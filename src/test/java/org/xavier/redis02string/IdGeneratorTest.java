package org.xavier.redis02string;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class IdGeneratorTest {
    @Test
    public void idGeneratorTest() {
        IdGenerator generator = new IdGenerator(new Jedis("127.0.0.1", 6379), "id");

        generator.reserve("10000");
        System.out.println(generator.getRedis().mget(("id")));

        Long id = generator.produce();
        System.out.println(id);
    }
}