package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Data
public class Relationship {
    private Jedis redis;
    private String user;

    public Relationship(Jedis redis, String user) {
        this.redis = redis;
        this.user = user;
    }

    //following - 表示我的关注
    private String followingKey(String user) {
        return user += "::following";
    }

    //follower - 表示关注我的
    private String followerKey(String user) {
        return user+="::follower";
    }

    public boolean follow(String target) {
        String userFollowingKey = followingKey(user);
        String userFollowerKey = followerKey(target);
        redis.sadd(userFollowerKey, user);
        return redis.sadd(userFollowingKey, target) == 1;
    }

    public boolean unFollow(String target) {
        String userFollowingKey = followingKey(user);
        String targetFollowerKey = followerKey(target);
        redis.srem(targetFollowerKey, user);
        return redis.srem(userFollowingKey, target) == 1;
    }

    public boolean isFollowing(String target) {
        String userFollowingKey = followingKey(user);
        return redis.sismember(userFollowingKey, target);
    }

    public Set<String> getAllFollowing() {
        String userFollowingKey = followingKey(user);
        return redis.smembers(userFollowingKey);
    }

    public Set<String> getAllFollower() {
        String userFollowerKey = followerKey(user);
        return redis.smembers(userFollowerKey);
    }

    public Long countFollowing() {
        String userFollowingKey = followingKey(user);
        return redis.scard(userFollowingKey);
    }

    public Long countFollower() {
        String userFollowerKey = followerKey(user);
        return redis.scard(userFollowerKey);
    }
}
