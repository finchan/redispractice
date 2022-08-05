package org.xavier.redis11database;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class DBSamplerTest {
    private DBSampler sample;

    @Before
    public void setUp() throws Exception {
        sample = new DBSampler(new Jedis("127.0.0.1", 6379));
    }

    @Test
    public void sample() {
        sample.sample();
    }
}