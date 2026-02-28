// package Redis-Like-Distributed-Cache.protocol;

// public class Response {
    
// }

package com.cache.protocol;

public class Response {
    public static String error(String msg) {
        return "-ERROR " + msg;
    }
}