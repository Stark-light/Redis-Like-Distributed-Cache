// package Redis-Like-Distributed-Cache.store;

// public class InMemoryCacheStore {
    
// }

package com.cache.store;

import com.cache.eviction.LRUEvictionPolicy;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class InMemoryCacheStore {
    private final ConcurrentHashMap<String, CacheEntry> map = new ConcurrentHashMap<>();
    private final LRUEvictionPolicy lru;
    private final int capacity;

    private final AtomicLong hits = new AtomicLong(0);
    private final AtomicLong misses = new AtomicLong(0);

    public InMemoryCacheStore(int capacity) {
        this.capacity = capacity;
        this.lru = new LRUEvictionPolicy(capacity);
    }

    public void set(String key, String value) {
        if (map.size() >= capacity && !map.containsKey(key)) {
            String evicted = lru.evictKey();
            if (evicted != null) map.remove(evicted);
        }
        map.put(key, new CacheEntry(value, 0));
        lru.onPut(key);
    }

    public String get(String key) {
        CacheEntry entry = map.get(key);
        if (entry == null) {
            misses.incrementAndGet();
            return null;
        }
        if (entry.isExpired()) {
            map.remove(key);
            lru.onRemove(key);
            misses.incrementAndGet();
            return null;
        }
        hits.incrementAndGet();
        lru.onGet(key);
        return entry.value;
    }

    public boolean delete(String key) {
        CacheEntry removed = map.remove(key);
        if (removed != null) {
            lru.onRemove(key);
            return true;
        }
        return false;
    }

    public boolean expire(String key, int ttlSeconds) {
        CacheEntry entry = map.get(key);
        if (entry == null) return false;
        entry.expireAtMillis = System.currentTimeMillis() + ttlSeconds * 1000L;
        return true;
    }

    public String stats(String nodeId) {
        long h = hits.get();
        long m = misses.get();
        long total = h + m;
        double hitRate = total == 0 ? 0 : (h * 100.0 / total);

        return """
                +STATS
                node=%s
                keys=%d
                hits=%d
                misses=%d
                hitRate=%.2f%%
                """.formatted(nodeId, map.size(), h, m, hitRate);
    }
}