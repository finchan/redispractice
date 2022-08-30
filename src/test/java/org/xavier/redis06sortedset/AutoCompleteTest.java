package org.xavier.redis06sortedset;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
@Slf4j
public class AutoCompleteTest {
    private AutoComplete autoComplete;

    @Before
    public void setUp() throws Exception {
        autoComplete = new AutoComplete(new Jedis(
                "127.0.0.1",
                6379
        ));
    }

    @Test
    public void feed() {
        autoComplete.feed("Redis");
        autoComplete.feed("Rain");
        autoComplete.feed("River");
        autoComplete.feed("rally");
        autoComplete.feed("reciprocal");
    }

    @Test
    public void hint() {
        List<String> result = autoComplete.hint("R", 5);
        result.forEach(s->log.info(s));
    }
}