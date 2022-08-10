package org.xavier.redis13transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.StreamEntryID;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Data
@AllArgsConstructor
public class CreateRandomTypeKeys {
    private Jedis client;

    public void createKeys(int number) {
        Pipeline pipeline = this.client.pipelined();
        for(int i=0; i< number; i++) {
            String key = "key:"+i;
            createRandomKey(pipeline, number, key);
        }
        pipeline.sync();
    }

    private void createRandomKey(Pipeline pipeline, int number, String key) {
        Random r = new Random(25);
        int rand = r.nextInt(6);
        switch (rand){
            case 0:
                pipeline.set(key,"");
                break;
            case 1:
                pipeline.hset(key, "", "");
                break;
            case 2:
                pipeline.rpush(key, "");
                break;
            case 3:
                pipeline.sadd(key, "");
                break;
            case 4:
                pipeline.zadd(key, 0, "");
                break;
            default:
                Map<String, String> info = new HashMap<>();
                info.put("userName", "Xavier");
                pipeline.xadd(key, StreamEntryID.NEW_ENTRY, info);
        }
    }
}
