package org.xavier.redis03hash;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.UUID;
@Data
public class LoginSession {
    //会话默认过期时间(毫秒)
    final static long DEFAULT_TIMEOUT = 1000*3600*24*30;
    //存储会话令牌以及会话过期时间戳的散列
    final static String SESSION_TOKEN_HASH = "SESSION::TOKEN";
    final static String SESSION_EXPIRE_TS_HASH = "SESSION:EXPIRE_TIMESTAMP";
    //会话状态
    final static String SESSION_NOT_LOGIN = "SESSION_NOT_LOGIN";    //用户尚未登陆
    final static String SESSION_EXPIRED = "SESSION_EXPIRED";        //会话过期
    final static String SESSION_TOKEN_CORRECT = "SESSION_TOKEN_CORRECT";    //用户已登录，给定的令牌与用户令牌一致
    final static String SESSION_TOKEN_INCORRECT = "SESSION_TOKEN_INCORRECT";    //用户已登录，令牌与用户令牌不一致6

    private Jedis client;
    private String userId;

    public LoginSession(Jedis client, String userId) {
        this.client = client;
        this.userId = userId;
    }

    public String generateToken() {
        return UUID.randomUUID().toString();
    }

    public String create(Long timeout){
        String userToken = generateToken();
        Long expiredTime = System.currentTimeMillis() + timeout*1000;
        client.hset(SESSION_TOKEN_HASH, userId, userToken);
        client.hset(SESSION_EXPIRE_TS_HASH, userId, expiredTime.toString());
        return userToken;
    }

    public String validate(String inputToken) {
        String userToken = client.hget(SESSION_TOKEN_HASH, userId);
        String eValue = client.hget(SESSION_EXPIRE_TS_HASH, userId);
        if(userToken == null || eValue == null) {
            return SESSION_NOT_LOGIN;
        }

        Long expireTimestamp = Long.parseLong(eValue);

        if(inputToken.equals(userToken)){
            if(System.currentTimeMillis() > expireTimestamp){
                return SESSION_EXPIRED;
            }
            return SESSION_TOKEN_CORRECT;
        } else {
            return SESSION_TOKEN_INCORRECT;
        }
    }

    public void destroy() {
        client.hdel(SESSION_TOKEN_HASH, userId);
        client.hdel(SESSION_EXPIRE_TS_HASH, userId);
    }
}
