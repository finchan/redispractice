package org.xavier.redis13transaction;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class IdentityLockTest {
    private IdentityLock lock;

    @Test
    public void acquire() {
        lock = new IdentityLock(new Jedis("127.0.0.1", 6379), "lock");
        lock.acquire("Xavier", 30);
    }

    @Test
    public void release() {
        lock = new IdentityLock(new Jedis("127.0.0.1", 6379), "lock");
        lock.release("Javier");
        lock.release("Xavier");
    }
}