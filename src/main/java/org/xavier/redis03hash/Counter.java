package org.xavier.redis03hash;

import lombok.Data;
import redis.clients.jedis.Jedis;
@Data
public class Counter {
    private Jedis redis;
    private String hashKey;
    private String counterName;

    public Counter(Jedis redis, String hashKey, String counterName) {
        this.redis = redis;
        this.hashKey = hashKey;
        this.counterName = counterName;
    }

    public Long increase(Long number) {
        return redis.hincrBy(hashKey, counterName, number);
    }

    public Long decrease(Long number) {
        return redis.hincrBy(hashKey, counterName, -number);
    }

    public Long getCounter() {
        String value = redis.hget(hashKey, counterName);
        return value == null ? 0L: Long.parseLong(value);
    }

    public void reset() {
        redis.hset(hashKey, counterName, "0");
    }
}
