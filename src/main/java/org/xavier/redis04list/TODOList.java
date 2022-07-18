package org.xavier.redis04list;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;

@Data
public class TODOList {
    private Jedis jedis;
    private String userId;
    private String todoListKey;
    private String doneListKey;

    public TODOList(Jedis jedis, String userId) {
        this.jedis = jedis;
        this.userId = userId;
        this.todoListKey = makeTODOListKey(userId);
        this.doneListKey = makeDONEListKey(userId);
    }

    public void add(String event) {
        jedis.lpush(todoListKey, event);
    }

    public void remove(String event){
        jedis.lrem(todoListKey, 0, event);
    }

    public void done(String event){
        jedis.lpush(doneListKey, event);
    }

    public List<String> showTODOList(){
        return jedis.lrange(todoListKey, 0, -1);
    }

    public List<String> showDONEList(){
        return jedis.lrange(doneListKey, 0, -1);
    }

    public static String makeTODOListKey(String userId){
        return "TODOLIST::" + userId + "::TODO";
    }

    public static String makeDONEListKey(String userId) {
        return "TODOLIST::" + userId + "::DONE";
    }
}
