package org.xavier.redis02string;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
@Data
@Slf4j
public class Limiter {
    private Jedis redis;
    private String key;

    public Limiter(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public void setMaxExecuteTimes(Long maxExecuteTimes) {
        redis.set(key, String.valueOf(maxExecuteTimes));
    }

    public boolean stillValidToExecute() {
        Long num = redis.decr(key);
        return num>=0;
    }

    public Long remainingExecuteTimes() {
        Long num = Long.parseLong(redis.get(key));
        return num < 0L? 0L: num;
    }
}
