package org.xavier.redis05set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Data
@Slf4j
public class CommonFollowingTest {
    private CommonFollowing common;
    @Before
    public void setUp() throws Exception {
        common = new CommonFollowing(new Jedis("127.0.0.1", 6379), "JOHN", "SMITH");
        common.getRedis().sadd("JOHN::following", "MON", "TUE", "WED", "FRI");
        common.getRedis().sadd("SMITH::following", "MON", "WED", "FRI");
    }

    @Test
    public void calculate() {
        common.calculate().forEach(s->log.info(s));
    }

    @Test
    public void calculateAndStore() {
        common.calculateAndStore("COMMON").forEach(s->log.info(s));
    }
}