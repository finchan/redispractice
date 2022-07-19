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
public class RelationshipTest {
    private Relationship john;
    private Relationship tom;

    @Before
    public void setUp() throws Exception {
        john = new Relationship(new Jedis("127.0.0.1", 6379), "JOHN");
        john.getRedis().flushDB();
        john.follow("MARY");
        john.follow("TOM");
    }

    @Test
    public void follow() {
        john.follow("JACK");
    }

    @Test
    public void unFollow() {
        john.unFollow("MARY");
    }

    @Test
    public void isFollowing() {
        Assert.assertTrue(john.isFollowing("MARY"));
    }

    @Test
    public void getAllFollowing() {
        john.getAllFollowing().forEach(s->log.info(s));
    }

    @Test
    public void getAllFollower() {
        Relationship mary = new Relationship(new Jedis("127.0.0.1", 6379), "MARY");
        mary.follow("JOHN");
        john.getAllFollower().forEach(s->log.info(s));
    }

    @Test
    public void countFollowing() {
        log.info(john.countFollowing().toString());
    }

    @Test
    public void countFollower() {
        log.info(john.countFollower().toString());
    }
}