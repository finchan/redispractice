package org.xavier.redis02string;

import lombok.Data;
import redis.clients.jedis.Jedis;
@Data
public class Counter {
    private Jedis redis;
    private String key;

    public Long increasing(Long n) {
        return redis.incrBy(key, n);
    }

    public Long decreasing(Long n) {
        return redis.decrBy(key, n);
    }

    public Long get() {
        String value = redis.get(key);
        if(value == null) {
            return 0L;
        } else {
            return Long.parseLong(value);
        }
    }

    public Long reset() {
        String oldValue = redis.getSet(key, "0");
        if(oldValue  == null) {
            return 0L;
        } else {
            return Long.parseLong(oldValue);
        }
    }

    public Counter(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }
}
