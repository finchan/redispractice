package org.xavier.redis11database;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;
@Slf4j
public class DBIteratorTest {
    private DBIterator it = new DBIterator(new Jedis("127.0.0.1", 6379),
                "*", 2, "0", false);

    private void next() {
        List<String> result = it.next();
        result.forEach(str ->  {
            log.info(str);
        });
    }

    @Test
    public void testNext(){
        next();
        next();
    }
}