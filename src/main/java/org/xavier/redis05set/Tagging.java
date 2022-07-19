package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Data
public class Tagging {
    private Jedis client;
    private String key;

    public Tagging(Jedis client, String key) {
        this.client = client;
        this.key = makeTagKey(key);
    }

    private String makeTagKey(String item) {
        return item+"::tags";
    }

    public void add(String... tags) {
        client.sadd(key, tags);
    }

    public void remove(String... tags) {
        client.srem(key, tags);
    }

    public boolean isIncluded(String tag) {
        return client.sismember(key, tag);
    }

    public Set<String> getAllTags(){
        return client.smembers(key);
    }

    public Long count() {
        return client.scard(key);
    }
}
