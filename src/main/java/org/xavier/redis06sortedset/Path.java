package org.xavier.redis06sortedset;

import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

@Data
public class Path {
    private Jedis redis;

    public Path(Jedis redis) {
        this.redis = redis;
    }

    private String makeRecordKey(String origin) {
        return "FORWARD_TO_RECORD::" + origin;
    }

    public void forwardTo(String origin, String destination) {
        String key = makeRecordKey(origin);
        redis.zincrby(key, 1, destination);
    }

    public Set<Tuple> paggingRecord(String origin, int pageNumber, int pageSize) {
        String key = makeRecordKey(origin);
        int startIndex = (pageNumber-1)*pageSize;
        int endIndex = pageNumber*pageSize-1;
        return redis.zrevrangeWithScores(key, startIndex, endIndex);
    }
}
