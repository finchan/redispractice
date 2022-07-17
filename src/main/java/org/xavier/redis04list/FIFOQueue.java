package org.xavier.redis04list;

import lombok.Data;
import redis.clients.jedis.Jedis;
@Data
public class FIFOQueue {
    private Jedis client;
    private String key;

    public FIFOQueue(Jedis client, String key) {
        this.client = client;
        this.key = key;
    }

    public Long enqueue(String element){
        return client.rpush(key, element);
    }

    public String dequeue(){
        return client.lpop(key);
    }
}
