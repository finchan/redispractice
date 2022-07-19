package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
public class UniqueConter {
    private Jedis client;
    private String key;

    public UniqueConter(Jedis client, String key) {
        this.client = client;
        this.key = key;
    }

    public boolean countIn(String item) {
        return client.sadd(key, item) == 1;
    }

    public Long getResult() {
        return client.scard(key);
    }
}
