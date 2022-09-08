package org.xavier.redis19sentinel;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class SentinelTest {
    private Sentinel sentinel;

    @Before
    public void setUp() throws Exception {
        sentinel = new Sentinel(new Jedis("127.0.0.1", 26379));
    }

    @Test
    public void discoveryMasters() {
        sentinel.discoveryMasters();
    }
}