package org.xavier.redis02string;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

public class ImperfectLock {
    private Jedis redis;
    private String key;

    public Jedis getRedis() {
        return redis;
    }

    public void setRedis(Jedis redis) {
        this.redis = redis;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ImperfectLock(Jedis redis, String key) {
        this.redis = redis;
        this.key = key;
    }

    public boolean acquire() {
        Boolean result = false;
        SetParams param = new SetParams().nx();
        //redis.set成功，返回的内容同命令本身 - OK
        String rs = redis.set(key, "locking", param);
        if("OK".equals(rs))
            result = true;
        return result;
    }

    public boolean release() {
        //DEL成功返回1，失败返回0
        return redis.del(key) == 1;
    }
}
