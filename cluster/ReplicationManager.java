// package Redis-Like-Distributed-Cache.cluster;

// public class ReplicationManager {
    
// }

package com.cache.cluster.replication;

import Redis-Like-Distributed-Cache.cluster.CacheNode;
import Redis-Like-Distributed-Cache.cluster.ClusterManager;

import java.util.List;

public class ReplicationManager {

    private final ClusterManager cluster;
    private final int replicationFactor;

    public ReplicationManager(ClusterManager cluster, int replicationFactor) {
        this.cluster = cluster;
        this.replicationFactor = replicationFactor;
    }

    public List<CacheNode> getReplicaNodes(String key) {

        CacheNode primary = cluster.getNodeForKey(key);

        List<CacheNode> nodes = cluster.getNodes();

        int start = nodes.indexOf(primary);

        return nodes.subList(start, Math.min(start + replicationFactor, nodes.size()));
    }
}