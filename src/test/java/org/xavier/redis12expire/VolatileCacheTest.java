package org.xavier.redis12expire;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
public class VolatileCacheTest {
    private VolatileCache cache;

    @Before
    public void init() {
        cache = new VolatileCache(new Jedis("127.0.0.1", 6379));
    }

    @Test
    public void set() {
        cache.set("msg", "Expired in 5 sec", 5);
    }

    @Test
    public void get() {
        //after 10 msg and execute it
        if(null == cache.get("msg"))
            log.info("After 10 sec, NULL");
    }
}