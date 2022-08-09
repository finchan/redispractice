package org.xavier.redis12expire;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Data
public class LoginSession {
    public final static String SESSION_NOT_LOGIN_OR_EXPIRED = "SESSION_NOT_LOGIN_OR_EXPIRED";
    public final static String SESSION_TOKEN_CORRECT = "SESSION_TOKEN_CORRECT";
    public final static String SESSION_TOKEN_INCORRECT = "SESSION_TOKEN_INCORRECT";
    public final int DEFAULT_TIMEOUT = 3600*24*30;

    private Jedis client;
    private String userID;
    private String key;

    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    public LoginSession(Jedis client, String userID) {
        this.client = client;
        this.userID = userID;
        this.key = "user::"+userID + "::token";
    }

    public String create() {
        final String token = generateToken();
        String result = this.client.setex(key, DEFAULT_TIMEOUT, token);
        if("OK".equals(result)) {
            return token;
        }else {
            return null;
        }
    }

    public String validate(String inputToken) {
        String userToken = this.client.get(this.key);
        if(userToken == null)
            return SESSION_NOT_LOGIN_OR_EXPIRED;
        if(inputToken.equals(userToken)) {
            return SESSION_TOKEN_CORRECT;
        } else {
            return SESSION_TOKEN_INCORRECT;
        }
    }

    public void destroy() {
        this.client.del(key);
    }
}
