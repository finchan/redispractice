package org.xavier.redis07hyperloglog;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
public class UniqueCounterTest {
    private UniqueCounter uniqueCounter;
    @Before
    public void setUp() throws Exception {
        uniqueCounter = new UniqueCounter(new Jedis("127.0.0.1", 6379), "HYPERLOGLOG-TEST");
    }

    @Test
    public void countIn() {
        uniqueCounter.countIn("AAA");
        uniqueCounter.countIn("BBB");
        uniqueCounter.countIn("AAA");
    }

    @Test
    public void getResult() {
        Long result = uniqueCounter.getResult();
        Optional.ofNullable(result).ifPresentOrElse(l-> log.info("Elements Amount: " + l.toString()), ()->{
            log.info("No Elements");
        });
    }
}