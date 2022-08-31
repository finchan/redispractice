package org.xavier.redis20cluster;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
@Slf4j
public class RedisClusterClient {
    public static void main(String[] args) {
        HostAndPort host = new HostAndPort("127.0.0.1", 30001);
        JedisCluster redisCluster = new JedisCluster(host);

        redisCluster.set("client", "JedisCluster");
        String client = redisCluster.get("client");

        redisCluster.getClusterNodes().forEach((key, pool) ->{
            log.info(key + "\t");
        });
        redisCluster.close();
    }
}
