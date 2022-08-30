package org.xavier.redis12expire;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
public class AutoComplete {
    private Jedis client;

    public void feed(String content, int weight, int timeout) {
        for(int i=0; i< content.length(); i++){
            String key = "auto_complete::" + content.substring(0, i);
            this.client.zincrby(key, weight, content);
            this.client.expire(key, timeout);
        }
    }

    public List<String> hint(String prefix, int count) {
        String key = "auto_complete::" + prefix;
        return this.client.zrevrange(key, 0, count-1);
    }
}
