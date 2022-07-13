package org.xavier.redis01introduction;

import redis.clients.jedis.Jedis;

public class R01CheckConnecion {
    public static void main(String[] args) {
        Jedis redis = new Jedis("127.0.0.1", 6379);
        String retValue = redis.ping();
        System.out.println(retValue);
        retValue = redis.ping("Hello World");
        System.out.println(retValue);
    }
}
