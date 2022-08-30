package org.xavier.redis06sortedset;

import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

public class AutoComplete {
    private Jedis redis;

    public AutoComplete(Jedis redis) {
        this.redis = redis;
    }

    public void feed(String content) {
        for(int i=0; i< content.length();i++){
            String key = "auto_complete::" + content.substring(0, i+1);
            redis.zincrby(key, 1, content);
        }
    }

    /**
     * 自动不全函数
     * @param prefix 字符前缀
     * @param count 自动补全候选结果个数
     * @return Set<String>
     */
    public List<String> hint(String prefix, int count){
       String key = "auto_complete::" + prefix;
       return redis.zrevrange(key, 0, count-1);
    }
}
