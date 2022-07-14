package org.xavier.redis02string;

import redis.clients.jedis.Jedis;

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
}
