package org.xavier.redis04list;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Data
@Slf4j
public class FIFOQueueTest {
    private FIFOQueue queue;
    @Before
    public void setUp() throws Exception {
        queue = new FIFOQueue(new Jedis("127.0.0.1", 6379), "list001");
    }

    @Test
    public void enqueue() {
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
    }

    @Test
    public void dequeue() {
        String pop = "";
        pop = queue.dequeue();
        log.info(pop);
        pop = queue.dequeue();
        log.info(pop);
        pop = queue.dequeue();
        log.info(pop);
    }
}