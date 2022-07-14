package org.xavier.redis02string;

import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;
@Data
public class IdGenerator {
    private Jedis redis;
    private String key;

    public IdGenerator(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public Long produce() {
        return redis.incr(key);
    }

    public boolean reserve(String n) {
        return "OK".equals(redis.set(key, n, new SetParams().nx()));
    }
}
