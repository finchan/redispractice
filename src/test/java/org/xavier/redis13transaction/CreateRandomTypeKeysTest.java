package org.xavier.redis13transaction;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class CreateRandomTypeKeysTest {
    private CreateRandomTypeKeys randomKeys =
            new CreateRandomTypeKeys(new Jedis("127.0.0.1", 6379));
    @Test
    public void createKeys() {
        randomKeys.createKeys(10);
    }
}