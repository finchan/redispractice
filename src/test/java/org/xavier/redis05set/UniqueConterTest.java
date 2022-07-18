package org.xavier.redis05set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Data
@Slf4j
public class UniqueConterTest {
    private UniqueConter counter;
    @Before
    public void setUp() throws Exception {
      counter = new UniqueConter(new Jedis("127.0.0.1", 6379), "UNIQUE-COUNTER");
    }

    @Test
    public void countIn() {
        boolean result = counter.countIn("127.0.0.1");
        Assert.assertTrue(result);
    }

    @Test
    public void getResult() {
        Long result = counter.getResult();
        Assert.assertEquals(1, result.longValue());
    }
}