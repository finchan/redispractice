package org.xavier.redis13transaction;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

@Slf4j
public class SemaphoreTest {
    private Semaphore semaphore;

    @Before
    public void setUp() throws Exception {
        semaphore = new Semaphore(new Jedis("127.0.0.1", 6379), "Xavier");
    }

    @Test
    public void setMaxSize() {
        semaphore.setMaxSize(5);
    }

    @Test
    public void getMaxSize() {
        int size = semaphore.getMaxSize();
        log.info(String.valueOf(size));
    }

    @Test
    public void getCurrentSize() {
        int size = semaphore.getCurrentSize();
        log.info(String.valueOf(size));
    }

    @Test
    public void acquire() {
        semaphore.acquire("A");
        semaphore.acquire("B");
        semaphore.acquire("C");
        semaphore.acquire("D");
        semaphore.acquire("E");
        semaphore.acquire("F");
    }

    @Test
    public void release() {
    }
}