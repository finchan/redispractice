package org.xavier.redis16pubsub;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.JedisPubSub;
@Slf4j
public class BroadcastListener extends JedisPubSub {
    @Override
    public void onMessage(String channel, String message) {
        System.out.println("channel: " + channel + " message: " + message);
    }

    @Override
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("pattern: " + pattern + " channel: " + channel + " message: " + message);
    }

    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("channel: " + channel + " subscribedChannels: " + subscribedChannels);
    }

    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("channel: " + channel + " subscribedChannels: " + subscribedChannels);
    }

    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("pattern: " + pattern + " subscribedChannels: " + subscribedChannels);
    }

    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("pattern: " + pattern + " subscribedChannels: " + subscribedChannels);
    }

    @Override
    public void onPong(String pattern) {
        super.onPong(pattern);
    }
}
