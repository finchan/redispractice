package org.xavier.redis04list;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;

@Data
public class MessageQueue {
    private Jedis client;
    private String queueName;

    public MessageQueue(Jedis client, String queueName) {
        this.client = client;
        this.queueName = queueName;
    }

    public void addMessage(String message) {
        client.rpush(queueName, message);
    }

    public String getMessage() {
        //blpop - timeout = 0永不过期，timeout以秒为单位
        List<String> results =  client.blpop(0,queueName);
        String qName = null, poppedItem = null;
        if(results != null){
            qName = results.get(0);
            poppedItem = results.get(1);
        }
        return poppedItem;
    }

    public Long len() {
        return client.llen(queueName);
    }
}
