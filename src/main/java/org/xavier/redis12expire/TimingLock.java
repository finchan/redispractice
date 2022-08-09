package org.xavier.redis12expire;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

@Data
@AllArgsConstructor
public class TimingLock {
    private Jedis client;
    private String key;

    public String acquire(int timeout) {
        SetParams params = new SetParams();
        params.ex(timeout);
        params.nx();
        return this.client.set(this.key, "locking", params);
    }

    public boolean release() {
        return this.client.del(this.key) == 1;
    }
}
