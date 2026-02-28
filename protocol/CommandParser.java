// package Redis-Like-Distributed-Cache.protocol;

// public class CommandParser {
    
// }

package com.cache.protocol;

public class CommandParser {
    public static Command parse(String input) {
        if (input == null) return null;
        input = input.trim();
        if (input.isEmpty()) return null;

        String[] parts = input.split("\\s+", 3);
        String cmd = parts[0].toUpperCase();

        return switch (cmd) {
            case "PING" -> new Command(CommandType.PING, null, null, 0);
            case "STATS" -> new Command(CommandType.STATS, null, null, 0);

            case "SET" -> {
                if (parts.length < 3) yield null;
                yield new Command(CommandType.SET, parts[1], parts[2], 0);
            }
            case "GET" -> {
                if (parts.length < 2) yield null;
                yield new Command(CommandType.GET, parts[1], null, 0);
            }
            case "DEL" -> {
                if (parts.length < 2) yield null;
                yield new Command(CommandType.DEL, parts[1], null, 0);
            }
            case "EXPIRE" -> {
                if (parts.length < 3) yield null;
                int ttl = Integer.parseInt(parts[2]);
                yield new Command(CommandType.EXPIRE, parts[1], null, ttl);
            }
            default -> null;
        };
    }
}
