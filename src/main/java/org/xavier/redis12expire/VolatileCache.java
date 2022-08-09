package org.xavier.redis12expire;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
@AllArgsConstructor
public class VolatileCache {
    private Jedis client;

    public void set(String key, String value, int timeout) {
        this.client.set(key, value);
        this.client.expire(key, timeout);
    }

    public String get(String key) {
        return this.client.get(key);
    }
}
