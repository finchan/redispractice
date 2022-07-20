package org.xavier.redis06sortedset;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Date;

import static org.junit.Assert.*;
@Data
@Slf4j
public class TimelineTest {
    private Timeline timeline;
    @Before
    public void setUp() throws Exception {
        timeline = new Timeline(new Jedis("127.0.0.1", 6379), "ZSET::TIMELINE");
    }

    @Test
    public void add() {
        timeline.add("A", new Date().getTime());
        timeline.add("B", new Date().getTime());
        timeline.add("C", new Date().getTime());
        timeline.add("D", new Date().getTime());
        timeline.add("E", new Date().getTime());
        timeline.add("F", new Date().getTime());
        timeline.add("G", new Date().getTime());
        timeline.add("H", new Date().getTime());
        timeline.add("I", new Date().getTime());
        timeline.add("J", new Date().getTime());
        timeline.add("K", new Date().getTime());
        timeline.add("L", new Date().getTime());
    }

    @Test
    public void remove() {
        timeline.remove("B");
    }

    @Test
    public void count() {
        log.info(String.valueOf(timeline.count()));
    }

    @Test
    public void paging() {
        timeline.paging(1,5).forEach(zs->{
            log.info(zs.getElement()+": " + zs.getScore());
        });
    }

    @Test
    public void fetchByTimeRange() {
        timeline.fetchByTimeRange(1658307515863d, 1658307515910d, 1, 3).forEach(zs->{
            log.info(zs.getElement()+": " + zs.getScore());
        });
    }
}