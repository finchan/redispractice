package org.xavier.redis06sortedset;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

import static org.junit.Assert.*;
@Slf4j
public class RankingListTest {
    private RankingList rank;
    @Before
    public void setUp() throws Exception {
        rank = new RankingList(new Jedis("127.0.0.1", 6379), "MUSIC::RANK");
    }

    @Test
    public void setSocre() {
        rank.setScore("My love", 100);
        rank.setScore("Seasons in the sun", 90);
        rank.setScore("You raise me up", 80);
        rank.setScore("Beautiful in white", 70);
        rank.setScore("Yesterday once more", 60);
    }

    @Test
    public void getScore() {
        log.info(String.valueOf(rank.getScore("My love")));
    }

    @Test
    public void remove() {
        rank.remove("Beautiful in white");
    }

    @Test
    public void increaseScore() {
        rank.increaseScore("Yesterday once more",10);
    }

    @Test
    public void decreaseScore() {
        rank.decreaseScore("Yesterday once more", 10);
    }

    @Test
    public void getRank() {
        Assert.assertEquals(1, rank.getRank("My love").longValue());
    }

    @Test
    public void top() {
        Set<Tuple> top3 = rank.top(3L);
        top3.forEach(ss ->  {
            log.info(ss.getElement() + ": " + ss.getScore());
        });
    }
}