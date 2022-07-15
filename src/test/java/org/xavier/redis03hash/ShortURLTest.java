package org.xavier.redis03hash;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

@Slf4j
public class ShortURLTest {

    @Test
    public void getURLByField() {
        ShortURL url = new ShortURL(new Jedis("127.0.0.1", 6379));
        String shorten1 = url.shorten("www.google.com");
        log.info(shorten1);
        String shorten2 = url.shorten("www.yahoo.com");
        log.info(shorten2);
        String longUrl1 =  url.getURLByField("1");
        log.info(longUrl1);
        String longUrl2 =  url.getURLByField("2");
        log.info(longUrl2);
    }
}