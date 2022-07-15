package org.xavier.redis03hash;

import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
public class ShortURL {
    final static String ID_COUNTER = "SHORTURL::ID_COUNTER";
    final static String URL_HASH = "SHORTURL::URL_HASH";

    private Jedis redis;

    public ShortURL(Jedis redis) {
        this.redis = redis;
    }

    public String shorten(String targetURL) {
        Long newId = redis.incr(ID_COUNTER);
        String shortId = Long.toHexString(newId);
        redis.hset(URL_HASH, shortId, targetURL);
        return shortId;
    }

    public String getURLByField(String shortId) {
        return redis.hget(URL_HASH, shortId);
    }
}
