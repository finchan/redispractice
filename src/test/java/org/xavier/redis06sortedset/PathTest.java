package org.xavier.redis06sortedset;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.resps.Tuple;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@Data
@Slf4j
public class PathTest {
    private Path path;

    @Before
    public void setUp() throws Exception {
        path = new Path(new Jedis("127.0.0.1", 6379));
    }

    @Test
    public void forwardTo() {
        path.forwardTo("Redis使用手册", "Redis设计与实现");
        path.forwardTo("Redis使用手册", "Redis设计与实现");
        path.forwardTo("Redis使用手册", "Redis设计与实现");
        path.forwardTo("Redis使用手册", "Git学习指南");
        path.forwardTo("Redis使用手册", "Electron: From Beginner to Pro");
        path.forwardTo("Pro Git", "Redis设计与实现");
        path.forwardTo("Electron - From Beginner to Pro", "Git学习指南");
    }

    @Test
    public void paggingRecord() {
        List<Tuple> records = path.paggingRecord("Pro", 1, 2);
        Optional.ofNullable(records).ifPresentOrElse(tupleSet -> {
            tupleSet.forEach(tuple -> {
                log.info(tuple.getElement() + "\t" + tuple.getScore());
            });
        }, () -> {
            log.info("No Records");
        });
    }
}