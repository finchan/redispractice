package org.xavier.redis02string;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

@Slf4j
public class LimiterTest {
    private Limiter limiter;

    @Before
    public void setUp() throws Exception {
        limiter = new Limiter(new Jedis("127.0.0.1", 6379), "limit");
    }

    @Test
    public void stillValidToExecute() {
        limiter.setMaxExecuteTimes(10L);
        Assert.assertTrue(limiter.stillValidToExecute());
    }

    @Test
    public void remainingExecuteTimes() {
        if(limiter.getRedis().get(limiter.getKey())!= null) {
            Long remaining = limiter.remainingExecuteTimes();
            log.info(String.valueOf(remaining));
        }
    }

    @Test
    public void notValidToExecuteTest() {
        while(limiter.remainingExecuteTimes() >0 ) {
            log.info("Still valid and allow to use");
            limiter.stillValidToExecute();
        }
    }
}