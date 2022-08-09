package org.xavier.redis12expire;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Slf4j
public class LoginSessionTest {
    private LoginSession login;
    @Test
    public void create() {
        login = new LoginSession(new Jedis("127.0.0.1", 6379), "000001");
        String token = login.create();
        log.info(token);
    }

    @Test
    public void validate() {
        login = new LoginSession(new Jedis("127.0.0.1", 6379), "000001");
        String result = login.validate("dc82482b-5f05-49c6-916e-0534999638a5");
        log.info(result);
    }

    @Test
    public void destroy() {
        login = new LoginSession(new Jedis("127.0.0.1", 6379), "000001");
        login.destroy();
    }
}