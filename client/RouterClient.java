package Redis-Like-Distributed-Cache.client;

// package com.cache.client;

import com.cache.cluster.CacheNode;
import com.cache.cluster.ClusterManager;

import java.io.*;
import java.net.Socket;

public class RouterClient {

    private final ClusterManager cluster;

    public RouterClient() {
        this.cluster = new ClusterManager();
    }

    public String sendCommand(String key, String command) throws Exception {

        CacheNode node = cluster.getNodeForKey(key);

        try (Socket socket = new Socket(node.getHost(), node.getPort())) {

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            writer.write(command + "\n");
            writer.flush();

            return reader.readLine();
        }
    }
}

public void replicatedSet(String key, String value) throws Exception {

    List<CacheNode> replicas = replicationManager.getReplicaNodes(key);

    for (CacheNode node : replicas) {

        try(Socket socket = new Socket(node.getHost(), node.getPort())) {

            BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
            );

            writer.write("SET " + key + " " + value + "\n");
            writer.flush();

        } catch(Exception e) {

            System.out.println("Replica failed: " + node.getNodeId());
        }
    }
}