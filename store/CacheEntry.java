// package Redis-Like-Distributed-Cache.store;

// public class CacheEntry {
    
// }

package com.cache.store;

public class CacheEntry {
    public final String value;
    public volatile long expireAtMillis; // 0 => no expiry

    public CacheEntry(String value, long expireAtMillis) {
        this.value = value;
        this.expireAtMillis = expireAtMillis;
    }

    public boolean isExpired() {
        return expireAtMillis > 0 && System.currentTimeMillis() > expireAtMillis;
    }
}