package org.xavier.redis13transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: 这个例子值得深度挖掘 - sync, syncAll, exec - 搞清楚这之间的关联
 */
@Data
@AllArgsConstructor
public class PipelineAndTransaction {
    private Jedis client;

    public List<Object> mlpop(String listKey, int number) {
        Pipeline pipeline = this.client.pipelined();
        List<Object> results = new ArrayList<Object>();
        Transaction tran = new Transaction(client);
        try{
            //开启事务
            tran.multi();
            for(int i=0; i< number; i++) {
                pipeline.lpop(listKey);
                if(i == 5) throw new Exception ("Test DISCARD");
            }
            //执行事务
            tran.exec();
            pipeline.sync(); //no result
//            results = pipeline.syncAndReturnAll();  //with results
        } catch (Exception e) {
            //取消事务
            tran.discard();
        }
        return results;
    }
}
