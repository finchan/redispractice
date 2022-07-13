package org.xavier.redis02string;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class ImperfectLockTest {
    @Test
    public void testLock() {
        ImperfectLock lock = new ImperfectLock(new Jedis("127.0.0.1", 6379), "lock");
        Assert.assertTrue(lock.acquire());
        Assert.assertFalse(lock.acquire());
        boolean result = lock.release();
        Assert.assertTrue(result);
        result = lock.release();
        Assert.assertFalse(result);
    }
}