package org.xavier.redis10stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
public class MessageQueueTest {
    private MessageQueue mq;
    @Before
    public void init() {
        mq = new MessageQueue(new Jedis("127.0.0.1", 6379), "MQ-KEY");
    }

    @Test
    public void addMessage() {
        Map<String, String> info = new HashMap<>();
        info.put("userName", "Xavier");
        info.put("location", "Dalian");
        Map<String, String> info2 = new HashMap<>();
        info2.put("userName", "Tom");
        info2.put("location", "Beijing");
        Map<String, String> info3 = new HashMap<>();
        info3.put("userName", "Jack");
        info3.put("location", "Shanghai");
        Map<String, String> info4 = new HashMap<>();
        info4.put("userName", "Luis");
        info4.put("location", "Paris");
        mq.addMessage(info);
        mq.addMessage(info2);
        mq.addMessage(info3);
        mq.addMessage(info4);
    }

    @Test
    public void getMessage() {
        Optional.ofNullable(mq.getMessage("1658843349199-0"))
                .ifPresent(msg ->
                        msg.forEach(se -> {
                            se.getFields().forEach((key, value) -> {
                                log.info(key + "\t" + value);
                            });
                        }));
    }

    @Test
    public void removeMessage() {
        mq.removeMessage("1658843349199-1");
    }

    @Test
    public void len() {
        log.info(mq.len().toString());
    }

    @Test
    public void getByRange() {
        List<StreamEntry> result = mq.getByRange("1658843349197-0", "1658843400345-1", 10);
        Optional.ofNullable(result).ifPresent(list ->{
            if(list.size() != 0) {
                list.forEach(se -> {
                    se.getFields().forEach((k,v)->{
                        log.info(k+"\t" + v);
                    });
                });
            }
        });
    }

    @Test
    public void iterate() {
        List<Map.Entry<String, List<StreamEntry>>> result = mq.iterate("1658843349197-0", 10);
        result.forEach(map->{
            map.getValue().forEach(se->{
                se.getFields().forEach((key,value)->{
                    log.info(key+"\t"+value);
                });
            });
        });
        System.out.println(result.size());
    }
}