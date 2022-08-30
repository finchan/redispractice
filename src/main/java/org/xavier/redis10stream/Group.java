package org.xavier.redis10stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.*;
import redis.clients.jedis.params.XReadGroupParams;
import redis.clients.jedis.resps.StreamConsumersInfo;
import redis.clients.jedis.resps.StreamEntry;
import redis.clients.jedis.resps.StreamGroupInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Group {
    private Jedis client;
    private String streamKey;
    private String groupId;

    //创建消费者组
    public void createGroup(String startId){
        this.client.xgroupCreate(streamKey, groupId, new StreamEntryID(startId), false);
    }

    //删除消费者组
    public void destroyGroup(){
        this.client.xgroupDestroy(streamKey, groupId);
    }

    //从消费者组中读取信息
    public List<Map.Entry<String, List<StreamEntry>>> readMessage(String consumerId, int count) {
        Map<String, StreamEntryID> entry = new HashMap<>();
        entry.put(streamKey, StreamEntryID.UNRECEIVED_ENTRY);
        XReadGroupParams params = new XReadGroupParams();
        params.count(count);
        params.block(0);
        return this.client.xreadGroup(groupId, consumerId, params, entry);
    }

    //确认已处理完毕的信息
    public Long ackMessage(String messageId){
        return this.client.xack(streamKey, groupId, new StreamEntryID(messageId));
    }

    //返回消费组信息
    @Deprecated
    public List<StreamGroupInfo> info() {
        return this.client.xinfoGroup(streamKey);
    }

    //返回消费者信息
    public List<StreamConsumersInfo> consumerInfo() {
        return this.client.xinfoConsumers(streamKey, groupId);
    }

    //删除指定的消费者
    public void deleteConsumer(String consumerId) {
        this.client.xgroupDelConsumer(streamKey, groupId, consumerId);
    }
}
