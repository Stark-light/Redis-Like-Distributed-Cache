package Redis-Like-Distributed-Cache.cluster;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashFunction {

    public static long hash(String key) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(key.getBytes(StandardCharsets.UTF_8));

            long hash = 0;
            for (int i = 0; i < 4; i++) {
                hash <<= 8;
                hash |= ((int) digest[i]) & 0xFF;
            }

            return hash & 0xffffffffL;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}