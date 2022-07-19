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
public class LikeTest {
    private Like like;
    @Before
    public void setUp() throws Exception {
        like = new Like(new Jedis("127.0.0.1", 6379), "LIKE::SET");
    }

    @Test
    public void cast() {
        like.cast("Xavier");
        like.cast("Javier");
        like.cast("Zavier");
    }

    @Test
    public void undo() {
        like.undo("Zavier");
    }

    @Test
    public void isLiked() {
        Assert.assertTrue(like.isLiked("Xavier"));
    }

    @Test
    public void getAllLikedUsers() {
        like.getAllLikedUsers().forEach(s->log.info(s));
    }

    @Test
    public void count() {
        log.info(like.count().toString());
    }
}