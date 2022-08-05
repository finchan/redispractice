package org.xavier.redis11database;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.*;

@Data
@AllArgsConstructor
@Slf4j
public class DBSampler {
    private Jedis client;

    public void sample() {
        Map<String, Long> keyType = new HashMap<>();
        keyType.put("string", 0L);
        keyType.put("list", 0L);
        keyType.put("hash", 0L);
        keyType.put("set", 0L);
        keyType.put("zset", 0L);
        keyType.put("stream", 0L);

        List<String> keys = new ArrayList<String>();
        getAllKeys(keys, "0");
        keys.forEach(key->{
            String type = this.client.type(key);
            keyType.put(type, keyType.get(type).longValue()+1);
        });
        Long dbSize = this.client.dbSize();

        keyType.forEach((key, value)->{
            log.info(key + "\t" + Math.round((double)value/dbSize*100)+"%");
        });
    }

    private void getAllKeys(List<String> allKeys, String cursor) {
        ScanResult<String> result = this.client.scan(cursor);
        Optional.ofNullable(result.getResult())
                .ifPresent(records -> allKeys.addAll(records));
        cursor = result.getCursor();
        if(result.getCursor().equals("0")) {
            return;
        } else{
            getAllKeys(allKeys, cursor);
        }
    }
}
