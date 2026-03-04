package Redis-Like-Distributed-Cache.cluster;

public class CacheNode {

    private final String nodeId;
    private final String host;
    private final int port;

    public CacheNode(String nodeId, String host, int port) {
        this.nodeId = nodeId;
        this.host = host;
        this.port = port;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return nodeId + "(" + host + ":" + port + ")";
    }
}