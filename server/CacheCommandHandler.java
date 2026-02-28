// package Redis-Like-Distributed-Cache.server;

// public class CacheCommandHandler {
    
// }

package com.cache.server;

import com.cache.protocol.Command;
import com.cache.protocol.CommandParser;
import com.cache.protocol.Response;
import com.cache.store.InMemoryCacheStore;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class CacheCommandHandler extends SimpleChannelInboundHandler<String> {
    private final String nodeId;
    private final InMemoryCacheStore store = new InMemoryCacheStore(1000); // max keys

    public CacheCommandHandler(String nodeId) {
        this.nodeId = nodeId;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Command cmd = CommandParser.parse(msg);

        if (cmd == null) {
            ctx.writeAndFlush(Response.error("Invalid command\n"));
            return;
        }

        switch (cmd.type()) {
            case PING -> ctx.writeAndFlush("+PONG\n");
            case SET -> {
                store.set(cmd.key(), cmd.value());
                ctx.writeAndFlush("+OK\n");
            }
            case GET -> {
                String val = store.get(cmd.key());
                if (val == null) ctx.writeAndFlush("$NULL\n");
                else ctx.writeAndFlush("$" + val + "\n");
            }
            case DEL -> {
                boolean removed = store.delete(cmd.key());
                ctx.writeAndFlush(removed ? "+OK\n" : "$NULL\n");
            }
            case EXPIRE -> {
                boolean ok = store.expire(cmd.key(), cmd.ttlSeconds());
                ctx.writeAndFlush(ok ? "+OK\n" : "$NULL\n");
            }
            case STATS -> ctx.writeAndFlush(store.stats(nodeId) + "\n");
            default -> ctx.writeAndFlush(Response.error("Unsupported command\n"));
        }
    }
}