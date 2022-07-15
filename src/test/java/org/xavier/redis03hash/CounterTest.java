package org.xavier.redis03hash;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class CounterTest {
    @Test
    public void test() {
        Counter counter = new Counter(new Jedis("127.0.0.1", 6379), "HASH_COUNTER_KEY", "COUNTER_FIELD");
        counter.increase(10L);
        counter.increase(10L);
        Long value =counter.getCounter();
        Assert.assertEquals(20L, value.longValue());
        counter.decrease(1L);
        value =counter.getCounter();
        Assert.assertEquals(19L, value.longValue());
        counter.reset();
        Assert.assertEquals(0L, counter.getCounter().longValue());
    }
}