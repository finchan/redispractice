package org.xavier.redis14lua;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

@Data
@AllArgsConstructor
@Slf4j
public class IdentityLock {
    private Jedis client;
    private String key;

    public boolean acquire(String identity, int timeout) {
        SetParams params = new SetParams();
        params.nx();
        params.ex(timeout);
        String result = client.set(key, identity, params);
        log.info("Execute result: " + result);
        return result != null;
    }

    public Object release(String inputIdentity) {
        String script = "\n" +
                "local key = KEYS[1]\n" +
                "local input_identity = ARGV[1]\n" +
                "local lock_identity = redis.call('GET', key)\n" +
                "if lock_identity == false then\n" +
                "   return true\n" +
                "elseif input_identity == lock_identity then\n" +
                "   redis.call('DEL', key)\n" +
                "   return true\n" +
                "else\n" +
                "   return false\n" +
                "end";
        Object result = client.eval(script, 1, key, inputIdentity);
        log.info("Execute result: " + result.toString());
        return result;
    }
}
