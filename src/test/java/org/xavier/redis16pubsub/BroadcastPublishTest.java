package org.xavier.redis16pubsub;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class BroadcastPublishTest {

    @Test
    public void testPublisher() {
        Jedis client = new Jedis("127.0.0.1", 6379);
        Broadcast publisher = new Broadcast(client);
        publisher.publish("sport.it", "Hello World");
    }

}
