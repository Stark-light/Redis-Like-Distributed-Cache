// package Redis-Like-Distributed-Cache.server;

// public class CacheServerInitializer {
    
// }

package com.cache.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

public class CacheServerInitializer extends ChannelInitializer<SocketChannel> {
    private final String nodeId;

    public CacheServerInitializer(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ch.pipeline().addLast(new LineBasedFrameDecoder(4096));
        ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));
        ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));
        ch.pipeline().addLast(new CacheCommandHandler(nodeId));
    }
}