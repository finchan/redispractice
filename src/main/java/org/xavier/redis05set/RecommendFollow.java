package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class RecommendFollow {
    private Jedis redis;
    private String followingKey;
    private String recommendedKey;

    public RecommendFollow(Jedis redis, String user, String recommendedKey) {
        this.redis = redis;
        this.followingKey = generateFollowingKey(user);
        this.recommendedKey = recommendedKey;
    }

    private String generateFollowingKey(String user) {
        return user + "::following";
    }

    private String generateRecommendedKey(String recommended){
        return recommended + "::recommended";
    }

    public Set<String> calculate(Integer seedSize) {
        //1 从用户关注的人中随机选一些人作为种子用户
        List<String> seedUsers = redis.srandmember(followingKey, seedSize);
        //2 收集种子用户的正在关注的集合键名
        Set<String> recommendedUser = new HashSet<>();
        seedUsers.forEach(s -> {
            redis.smembers(s).forEach(r->{
                recommendedUser.add(s);
            });
        });
        //3 对所有种子用户正在关注的集合执行并集计算
        recommendedUser.forEach(r->{
            redis.sadd(recommendedKey, r);
        });
        return redis.smembers(recommendedKey);
    }
}
