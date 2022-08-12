package org.xavier.redis14lua;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

@Slf4j
@AllArgsConstructor
public class LPopRPush {
    private Jedis client;

    public Object lpoprpush(String source, String target) {
        String script = "\n" +
                "local source = KEYS[1]\n" +
                "local target = KEYS[2]\n" +
                "local item = redis.call('LPOP', source)\n" +
                "if item ~= false then" +
                "   redis.call('RPUSH', target, item)\n" +
                "   return item\n" +
                "end";
        return client.eval(script, 2, source, target);
    }
}
