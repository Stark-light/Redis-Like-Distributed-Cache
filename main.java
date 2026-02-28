package com.cache;

import com.cache.server.CacheServer;

public class Main {
    public static void main(String[] args) throws Exception {
        int port = 8081;
        String nodeId = "node-1";

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--port") && i + 1 < args.length) port = Integer.parseInt(args[i + 1]);
            if (args[i].equals("--nodeId") && i + 1 < args.length) nodeId = args[i + 1];
        }

        CacheServer server = new CacheServer(port, nodeId);
        server.start();
    }
}