package org.xavier.redis07hyperloglog;

import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
public class UniqueCounter {
    private Jedis client;
    private String key;

    public UniqueCounter(Jedis client, String key) {
        this.client = client;
        this.key = key;
    }

    public boolean countIn(String item) {
        return client.pfadd(key, item) == 1 ? true: false;
    }

    public Long getResult() {
        return client.pfcount(key);
    }
}
