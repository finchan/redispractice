package org.xavier.redis08bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;

@Data
@AllArgsConstructor
public class ActionRecorder {
    private Jedis client;
    private String key;

    public static String makeActionKey(String action){
        return "action_recorder::" + action;
    }

    public void performBy(Long userId){
        //userID is offset
        client.setbit(key, userId, true);
    }

    public boolean isPerformedBy(Long userId) {
        return client.getbit(key, userId);
    }

    public Long countPerformed() {
        return client.bitcount(key);
    }
}
