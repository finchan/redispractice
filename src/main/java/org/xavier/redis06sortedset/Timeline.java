package org.xavier.redis06sortedset;

import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

@Data
public class Timeline {
    private Jedis redis;
    private String key;

    public Timeline(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public boolean add(String member, long time){
        return redis.zadd(key, time,member) == 1;
    }

    public boolean remove(String member){
        return redis.zrem(key, member) == 1;
    }

    public long count(){
        return  redis.zcard(key);
    }

    public Set< Tuple > paging(int pageNumber, int pageSize){
        int startIndex = (pageNumber-1)*pageSize;
        int endIndex = pageNumber*pageSize-1;
        return redis.zrevrangeWithScores(key, startIndex, endIndex);
    }

    public Set<Tuple> fetchByTimeRange(double minTime, double maxTime, int pageNumber, int pageSize){
        int startIndex = (pageNumber-1)*pageSize;
        return redis.zrevrangeByScoreWithScores(key, maxTime, minTime, startIndex,pageSize);
    }
}
