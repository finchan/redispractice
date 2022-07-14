package org.xavier.redis02string;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class CounterTest {
    private Counter counter;

    @Before
    public void init() {
        Jedis redis = new Jedis("127.0.0.1", 6379);
        String key = "counter";
        counter = new Counter(redis, key);
    }

    @Test
    public void increasing() {
        Long initValue = counter.get();
        System.out.println(initValue);
        Long value = counter.increasing(5L);
        System.out.println(value);
    }

    @Test
    public void decreasing() {
        Long initValue = counter.get();
        System.out.println(initValue);
        Long value = counter.decreasing(5L);
        System.out.println(value);
    }

    @Test
    public void reset() {
        Long initValue = counter.get();
        System.out.println(initValue);
        Long value = counter.reset();
        System.out.println(value);
    }
}