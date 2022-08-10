package org.xavier.redis13transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PipelineAndTransaction {
    private Jedis client;

    public List<Object> mlpop(String listKey, int number) {
        Pipeline pipeline = this.client.pipelined();
        List<Object> results = new ArrayList<Object>();
        try{
            //开启事务
            pipeline.multi();
            for(int i=0; i< number; i++) {
                pipeline.lpop(listKey);
                if(i == 5) throw new Exception ("Test DISCARD");
            }
            //执行事务
            Response<List<Object>> execResult = pipeline.exec();
//            pipeline.sync(); //no result
            results = pipeline.syncAndReturnAll();  //with results
        } catch (Exception e) {
            //取消事务
            pipeline.discard();
        }
        return results;
    }
}
