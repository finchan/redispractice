package org.xavier.redis02string;

import redis.clients.jedis.Jedis;

public class Cache {
    private Jedis redis;

    public Cache(Jedis redis){
        this.redis = redis;
    }

    public void set(String key, String value) {
        this.redis.set(key, value);
    }

    public String get(String key) {
        return this.redis.get(key);
    }

    public String update(String key, String newValue) {
        return this.redis.getSet(key, newValue);
    }
}
