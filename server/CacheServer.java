package com.cache.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class CacheServer {
    private final int port;
    private final String nodeId;

    public CacheServer(int port, String nodeId) {
        this.port = port;
        this.nodeId = nodeId;
    }

    public void start() throws Exception {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup workers = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(boss, workers)
             .channel(NioServerSocketChannel.class)
             .childHandler(new CacheServerInitializer(nodeId));

            ChannelFuture f = b.bind(port).sync();
            System.out.println("Cache Node started: " + nodeId + " on port " + port);
            f.channel().closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            workers.shutdownGracefully();
        }
    }
}