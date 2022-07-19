package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Data
public class Like {
    private Jedis client;
    private String setKey;

    public Like(Jedis client, String setKey) {
        this.client = client;
        this.setKey = setKey;
    }

    public boolean cast(String user) {
        return client.sadd(setKey, user) == 1;
    }

    public boolean undo(String user) {
        return client.srem(setKey, user) == 1;
    }
    
    public boolean isLiked(String user) {
        return client.sismember(setKey, user);
    }
    
    public Set<String> getAllLikedUsers() {
        return client.smembers(setKey);
    }
    
    public Long count() {
        return client.scard(setKey);
    }
}
