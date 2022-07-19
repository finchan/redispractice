package org.xavier.redis06sortedset;

import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

@Data
public class RankingList {
    private Jedis client;
    private String key;

    public RankingList(Jedis client, String key) {
        this.client = client;
        this.key = key;
    }

    public boolean setScore(String member, double score){
        return client.zadd(key, score, member) == 1;
    }

    public double getScore(String member) {
        return client.zscore(key, member);
    }

    public boolean remove(String member) {
        return client.zrem(key, member) == 1;
    }

    public double increaseScore(String member, double increment) {
        return client.zincrby(key, increment, member);
    }

    public double decreaseScore(String member, double decrement) {
        return client.zincrby(key, 0-decrement, member);
    }

    public Long getRank(String member) {
        Long rank = client.zrevrank(key, member);
        if(rank == null){
            rank = 0L;
        } else {
            rank = rank+1;
        }
        return rank;
    }

    public Set<Tuple> top(Long n){
        return client.zrevrangeWithScores(key, 0, n-1);
    }
}
