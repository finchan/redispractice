package org.xavier.redis12expire;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class TimingLockTest {
    private TimingLock lock;

    @Before
    public void setUp() throws Exception {
        lock = new TimingLock(new Jedis("127.0.0.1", 6379), "LOCK");
    }

    @Test
    public void acquire() {
        String result = lock.acquire(10);
        log.info((result != null)?"Successful": "Failed");
    }

    @Test
    public void release(){
        Boolean result = lock.release();
        log.info(String.valueOf(result));
    }
}