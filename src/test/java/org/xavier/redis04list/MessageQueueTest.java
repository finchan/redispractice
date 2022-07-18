package org.xavier.redis04list;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Data
@Slf4j
public class MessageQueueTest {
    @Test
    public void testAddMessage() {
        MessageQueue mq = new MessageQueue(new Jedis("127.0.0.1", 6379), "EMAIL-QUEUE");
        mq.addMessage("a@outlook.com");
        mq.addMessage("b@outlook.com");
        mq.addMessage("c@outlook.com");
        mq.addMessage("d@outlook.com");
    }

    @Test
    public void testGetMessage() {
        for(int i = 0; i<10; i++){
            MessageQueue mq = new MessageQueue(new Jedis("127.0.0.1", 6379), "EMAIL-QUEUE");
            log.info(mq.len().toString());
            String msg = mq.getMessage();
            log.info(msg);
        }
    }
}