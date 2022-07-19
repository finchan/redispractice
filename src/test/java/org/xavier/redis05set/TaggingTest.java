package org.xavier.redis05set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Data
@Slf4j
public class TaggingTest {
    private Tagging tag;

    @Before
    public void init() {
        tag = new Tagging(new Jedis("127.0.0.1", 6379), "music");
    }

    @Test
    public void add() {
        tag.add("声声慢", "七里香");
        tag.getClient().smembers(tag.getKey())
                .forEach(e-> log.info(e));
    }

    @Test
    public void remove() {
        tag.remove("七里香");
    }

    @Test
    public void isIncluded() {
        Assert.assertTrue(tag.isIncluded("声声慢"));
    }

    @Test
    public void getAllTags() {
        tag.getAllTags().forEach(s->log.info(s));
    }

    @Test
    public void count() {
        log.info(tag.count().toString());
    }
}