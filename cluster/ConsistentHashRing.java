package Redis-Like-Distributed-Cache.cluster;

import java.util.*;

public class ConsistentHashRing {

    private final SortedMap<Long, CacheNode> ring = new TreeMap<>();
    private final int virtualNodes;

    public ConsistentHashRing(int virtualNodes) {
        this.virtualNodes = virtualNodes;
    }

    public void addNode(CacheNode node) {

        for (int i = 0; i < virtualNodes; i++) {

            String vnodeKey = node.getNodeId() + "-vnode-" + i;
            long hash = HashFunction.hash(vnodeKey);

            ring.put(hash, node);
        }
    }

    public void removeNode(CacheNode node) {

        for (int i = 0; i < virtualNodes; i++) {

            String vnodeKey = node.getNodeId() + "-vnode-" + i;
            long hash = HashFunction.hash(vnodeKey);

            ring.remove(hash);
        }
    }

    public CacheNode getNode(String key) {

        if (ring.isEmpty()) {
            return null;
        }

        long hash = HashFunction.hash(key);

        if (!ring.containsKey(hash)) {

            SortedMap<Long, CacheNode> tailMap = ring.tailMap(hash);

            hash = tailMap.isEmpty() ? ring.firstKey() : tailMap.firstKey();
        }

        return ring.get(hash);
    }

    public void printRing() {
        ring.forEach((k, v) ->
                System.out.println(k + " -> " + v.getNodeId())
        );
    }
}