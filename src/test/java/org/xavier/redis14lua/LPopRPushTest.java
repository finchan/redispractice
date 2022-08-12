package org.xavier.redis14lua;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class LPopRPushTest {

    @Test
    public void lpoprpush() {
        LPopRPush  lpoprpush = new LPopRPush(new Jedis("127.0.0.1", 6379));
        Object value = lpoprpush.lpoprpush("source", "target");
        log.info(value.toString());
    }
}