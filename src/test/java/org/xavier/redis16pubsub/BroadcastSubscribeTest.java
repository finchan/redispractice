package org.xavier.redis16pubsub;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class BroadcastSubscribeTest {
    @Test
    public void testSubscribe1() {
        Jedis client = new Jedis("127.0.0.1", 6379);
        Broadcast subscribe1 = new Broadcast(client);
        subscribe1.subscribe("sport.it");
    }

    @Test
    public void testSubscribe2() {
        Jedis client = new Jedis("127.0.0.1", 6379);
        Broadcast subscribe2 = new Broadcast(client);
        subscribe2.subscribe("sport.it");
    }
}
