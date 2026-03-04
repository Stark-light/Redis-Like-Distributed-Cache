// package Redis-Like-Distributed-Cache.store;

// public class LRUEvictionPolicy {
    
// }

package com.cache.eviction;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUEvictionPolicy {
    private final int capacity;
    private final LinkedHashMap<String, Boolean> lruMap;

    public LRUEvictionPolicy(int capacity) {
        this.capacity = capacity;
        this.lruMap = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    public synchronized void onPut(String key) {
        lruMap.put(key, Boolean.TRUE);
    }

    public synchronized void onGet(String key) {
        lruMap.get(key); // access-order updates automatically
    }

    public synchronized void onRemove(String key) {
        lruMap.remove(key);
    }

    public synchronized String evictKey() {
        if (lruMap.isEmpty()) return null;
        Map.Entry<String, Boolean> eldest = lruMap.entrySet().iterator().next();
        String key = eldest.getKey();
        lruMap.remove(key);
        return key;
    }
}
