package org.xavier.redis16pubsub;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
@AllArgsConstructor
public class Broadcast {
    private Jedis client;

    public void subscribe(String channel) {
        BroadcastListener listener = new BroadcastListener();
        client.subscribe(listener, channel);
    }

    public void publish(String channel, String message) {
        Long num = client.publish(channel, message);
        System.out.println("Subscribed: " + String.valueOf(num));
    }


}
