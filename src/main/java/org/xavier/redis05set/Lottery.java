package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@Data
public class Lottery {
    private Jedis redis;
    private String key;

    public Lottery(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public boolean addPlayer(String user) {
        return redis.sadd(key, user) == 1;
    }

    public Set<String> getAllPlayers() {
        return redis.smembers(key);
    }

    public Long countPlayer() {
        return redis.scard(key);
    }

    public List<String> draw(Integer number) {
        return redis.srandmember(key, number);
    }
}
