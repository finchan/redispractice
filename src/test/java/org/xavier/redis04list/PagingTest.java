package org.xavier.redis04list;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;
@Data
@Slf4j
public class PagingTest {
    private Paging paging;

    @Before
    public void init() {
        paging = new Paging(new Jedis("127.0.0.1", 6379), "paging::list");
    }

    @Test
    public void testGetPage() {
        paging.add("A");
        paging.add("B");
        paging.add("C");
        paging.add("D");
        Long len = paging.size();
        log.info("LEN: " + len.toString());

        List<String> items = paging.getPage(2, 3);
        items.forEach(item -> log.info(item));
    }
}