package org.xavier.redis07hyperloglog;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;

@Data
public class UniqueCounterMerger {
    private Jedis client;

    public UniqueCounterMerger(Jedis client) {
        this.client = client;
    }

    public void merge(String destination, List<String> hyperloglogs){
        String[] logs = new String[]{};
        client.pfmerge(destination, hyperloglogs.toArray(logs));
    }
}
