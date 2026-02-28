// package Redis-Like-Distributed-Cache.protocol;

// public class Command {
    
// }

package com.cache.protocol;

public record Command(CommandType type, String key, String value, int ttlSeconds) {}