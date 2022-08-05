package org.xavier.redis11database;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Data
public class ResetUserPassword {
    private Jedis originDB;
    private Jedis newDB;

    public ResetUserPassword() {
        originDB = new Jedis("127.0.0.1", 6379);
        newDB = new Jedis("127.0.0.1", 6379);
    }

    public void resetPasswordAndSwapDatabase() {
        originDB.keys("user*").forEach(key->{
            Map<String, String> userData = originDB.hgetAll(key);
            userData.put("password", "NEWID");
            //Jedis不支持构造函数连接指定的INDEX数据库
            newDB.select(1);
            newDB.hmset(key, userData);
        });
        originDB.swapDB(0, 1);
        newDB.flushDB();
    }
}
