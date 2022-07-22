package org.xavier.redis07hyperloglog;

import lombok.Data;
import redis.clients.jedis.Jedis;
@Data
public class DuplicateChecker {
    private Jedis redis;
    private String key;

    public DuplicateChecker(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public boolean isDuplicated(String item) {
        return redis.pfadd(key, item) == 0;
    }

    public Long uniqueCount() {
        return redis.pfcount(key);
    }
}
