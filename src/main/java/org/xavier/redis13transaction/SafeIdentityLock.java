package org.xavier.redis13transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

@Data
@AllArgsConstructor
@Slf4j
public class SafeIdentityLock {
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
        Pipeline pipeline = this.client.pipelined();
        Transaction tran = new Transaction(client);
        boolean ret = false;
        try{
            tran.watch(this.key);
            String lockedIdentity = this.client.get(this.key);
//如果锁的值为空，说明锁已经释放了
            if(lockedIdentity == null) {
                log.info("锁已释放");
                ret = true;
            } else if(inputIdentity.equals(lockedIdentity)){
                //如果解锁人与锁持有者相同，可解锁（删除）
                log.info("解锁人与锁持有人相同，可解锁");
                tran.multi();
                Response<Long> result = pipeline.del(this.key);
                tran.exec();
                ret = result.get() == 1L;
            } else {
                //如果解锁人不同与当前锁持有者
                log.info("解锁人与锁持有人不同，无法解锁");
                ret = false;
            }
        } catch (Exception e) {
            log.error("Exception");
        } finally {
            tran.unwatch();
            pipeline.close();
            return ret;
        }
    }
}
