package org.xavier.redis02string;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class IdGenerator {
    private Jedis redis;
    private String key;

    public Jedis getRedis() {
        return redis;
    }

    public void setRedis(Jedis redis) {
        this.redis = redis;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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
