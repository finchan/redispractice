package org.xavier.redis07hyperloglog;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
public class DuplicateCheckerTest {
    private DuplicateChecker checker;
    @Before
    public void setUp() throws Exception {
        checker = new DuplicateChecker(new Jedis("127.0.0.1", 6379), "DUPLICATED-KEY");
    }

    @Test
    public void isDuplicated() {
        Assert.assertFalse(checker.isDuplicated("BOOK"));
        Assert.assertTrue(checker.isDuplicated("BOOK"));
    }

    @Test
    public void uniqueCount() {
        Long result = Optional.ofNullable(checker.uniqueCount()).orElse(0L);
        Assert.assertEquals(1, result.longValue());
    }
}