package org.xavier.redis13transaction;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.Optional;

@Data
@Slf4j
public class Semaphore {
    private Jedis client;
    private String name;
    private String holderKey;
    private String sizeKey;
    public Semaphore(Jedis client, String name) {
        this.name = name;
        this.client = client;
        holderKey = "semaphore::" + name + "::holders";
        sizeKey = "semaphore::" + name + "::max_size";
    }

    public void setMaxSize(int size) {
        client.set(sizeKey, String.valueOf(size));
    }

    public int getMaxSize() {
        String size = client.get(sizeKey);
        return size == null? 0: Integer.parseInt(size);
    }

    public int getCurrentSize() {
        return (int)client.scard(holderKey);
    }

    public boolean acquire(String identity) {
        boolean ret = false;
        Pipeline pipe = client.pipelined();
        Transaction tran = new Transaction(client);
        try{
            tran.watch(sizeKey, holderKey);
            //获取当前已被获取的信号数量，以及最大可获取的信号数量
            Response<Long> currentSizeStr = pipe.scard(holderKey);
            //下面的语句是必须的，否则无法调用Response.get()
            pipe.sync();

            int currentSize = Optional.ofNullable(currentSizeStr.get()).orElse(0L).intValue();
            Response<String> maxSizeResponse = pipe.get(sizeKey);
            pipe.sync();
            String maxSizeStr = maxSizeResponse.get();

            int maxSize = 0;
            if(maxSizeStr == null) {
                throw new Exception("Semaphore max size is not set");
            } else {
                maxSize = Integer.parseInt(maxSizeStr);
            }

            if(currentSize < maxSize) {
                tran.multi();
                pipe.sadd(holderKey, identity);
                tran.exec();
                ret = true;
                log.info("Add successfully");
            }
        } catch(Exception e) {
            log.info(e.getMessage());
        } finally {
            tran.unwatch();
            pipe.close();
            return ret;
        }
    }

    public boolean release(String identity) {
        return client.srem(identity) == 1;
    }
}
