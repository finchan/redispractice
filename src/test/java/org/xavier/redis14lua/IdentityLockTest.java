package org.xavier.redis14lua;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class IdentityLockTest {
    private IdentityLock lock;
    @Before
    public void setUp() throws Exception {
        lock = new IdentityLock(new Jedis("127.0.0.1", 6379), "LOCK");
    }

    @Test
    public void acquire() {
        lock.acquire("Xavier", 60);
    }

    @Test
    public void release() {
        lock.release("Xavier");
    }
}