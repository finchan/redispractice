package org.xavier.redis02string;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class LogTest {
    @Test
    public void testAppend() {
        Log log = new Log(new Jedis("127.0.0.1", 6379), "LOG");
        log.log("First Log");
        log.log("Seconde Log");
        log.log("Third Log");
    }
}