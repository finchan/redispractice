package org.xavier.redis05set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;
@Data
@Slf4j
public class LotteryTest {
    private Lottery lottery;

    @Before
    public void setUp() throws Exception {
        lottery = new Lottery(new Jedis("127.0.0.1", 6379), "LOTTERY");
    }

    @Test
    public void addPlayer() {
        lottery.addPlayer("A");
        lottery.addPlayer("B");
        lottery.addPlayer("C");
        lottery.addPlayer("D");
        lottery.addPlayer("E");
    }

    @Test
    public void getAllPlayers() {
        lottery.getAllPlayers().forEach(s->log.info(s));
    }

    @Test
    public void countPlayer() {
        log.info(lottery.countPlayer().toString());
    }

    @Test
    public void draw() {
        List<String> luckiers = lottery.draw(2);
        luckiers.forEach(s->log.info(s));
    }
}