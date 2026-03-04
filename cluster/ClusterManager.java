package Redis-Like-Distributed-Cache.cluster;

import java.util.ArrayList;
import java.util.List;

public class ClusterManager {

    private final ConsistentHashRing ring;
    private final List<CacheNode> nodes;

    public ClusterManager() {

        this.ring = new ConsistentHashRing(100);
        this.nodes = new ArrayList<>();

        initNodes();
    }

    private void initNodes() {

        CacheNode n1 = new CacheNode("node-1", "localhost", 8081);
        CacheNode n2 = new CacheNode("node-2", "localhost", 8082);
        CacheNode n3 = new CacheNode("node-3", "localhost", 8083);

        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);

        nodes.forEach(ring::addNode);
    }

    public CacheNode getNodeForKey(String key) {
        return ring.getNode(key);
    }

    public List<CacheNode> getNodes() {
        return nodes;
    }
}