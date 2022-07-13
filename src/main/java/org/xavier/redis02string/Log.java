package org.xavier.redis02string;

import redis.clients.jedis.Jedis;

public class Log {
    private Jedis redis;
    private String key;

    public Log(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public void log(String logInfo) {
        this.redis.append(key, "\n"+logInfo);
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
