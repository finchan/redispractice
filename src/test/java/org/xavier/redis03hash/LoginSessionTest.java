package org.xavier.redis03hash;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.xavier.redis03hash.LoginSession;
import redis.clients.jedis.Jedis;

@Slf4j
@Data
public class LoginSessionTest {
    private LoginSession loginSession;

    @Before
    public void setUp() throws Exception {
        loginSession = new LoginSession(new Jedis("127.0.0.1", 6379), "Xavier");
    }

    @Test
    public void generateToken() {
        String token = loginSession.generateToken();
        log.info(token);
    }

    @Test
    public void create() {
        loginSession.create(20L);
        String token = loginSession.getClient().hget(LoginSession.SESSION_TOKEN_HASH, loginSession.getUserId());
        log.info("TOKEN - "+token);
    }

    @Test
    public void validate() {
        String status = loginSession.validate("b63f9102-a85c-4974-a5f3-6b994e4191f8");
        log.info("STATUS: " + status);
    }

    @Test
    public void destroy() {
        loginSession.destroy();
    }
}