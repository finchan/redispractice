package org.xavier.redis19sentinel;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
@Data
@Slf4j
public class Sentinel {
    private Jedis sentinel;
    public Sentinel(Jedis sentinel) {
        this.sentinel = sentinel;
    }

    public void discoveryMasters() {
        sentinel.sentinelMasters().forEach(map->{
            map.forEach((key, value) -> {
                log.info((key + "\t" + value + "\n"));
            });
        });
    }
}
