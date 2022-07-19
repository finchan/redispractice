package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@Data
public class CommonFollowing {
    private Jedis redis;
    private String userFollowingSetKey;
    private String targetFollowingSetKey;

    public CommonFollowing(Jedis redis, String user, String target) {
        this.redis = redis;
        this.userFollowingSetKey = generateSetKey(user);
        this.targetFollowingSetKey = generateSetKey(target);
    }

    private String generateSetKey(String user) {
        return user+"::following";
    }

    public Set<String> calculate() {
        return redis.sinter(userFollowingSetKey, targetFollowingSetKey);
    }

    public Set<String> calculateAndStore(String store) {
        String storeKey = generateSetKey(store);
        redis.sinterstore(storeKey, userFollowingSetKey, targetFollowingSetKey);
        return redis.smembers(storeKey);
    }
}
