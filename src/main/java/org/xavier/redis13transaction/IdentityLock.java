package org.xavier.redis13transaction;

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
        String result = this.client.set(this.key, identity, params);
        return result != null;
    }

    public boolean release(String inputIdentity) {
        String lockedIdentity = this.client.get(this.key);
        //如果锁的值为空，说明锁已经释放了
        if(lockedIdentity == null) {
            log.info("锁已释放");
            return true;
        } else if(inputIdentity.equals(lockedIdentity)){
            //如果解锁人与锁持有者相同，可解锁（删除）
            log.info("解锁人与锁持有人相同，可解锁");
            return this.client.del(this.key) == 1;
        } else {
            //如果解锁人不同与当前锁持有者
            log.info("解锁人与锁持有人不同，无法解锁");
            return false;
        }
    }
}
