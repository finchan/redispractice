package org.xavier.redis10stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntry;
import redis.clients.jedis.StreamEntryID;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class MessageQueue {
    private Jedis client;
    private String streamKey;

    public void addMessage(Map<String, String> element) {
        client.xadd(streamKey, StreamEntryID.NEW_ENTRY, element);
    }

    public StreamEntry getMessage(String messageId) {
        List<StreamEntry> result =  client.xrange(streamKey, new StreamEntryID(messageId), new StreamEntryID(messageId),1);
        if(result.size() == 0) return null;
        return result.get(0);
    }

    public void removeMessage(String messageId) {
        client.xdel(streamKey, new StreamEntryID(messageId));
    }

    public Long len() {
        return client.xlen(streamKey);
    }

    public List<StreamEntry> getByRange(String startId, String endId, int maxItem) {
        List<StreamEntry> results = client.xrange(streamKey, new StreamEntryID(startId), new StreamEntryID(endId), maxItem);
        return results;
    }

    public void iterate(String startId, int maxItem) {
        client.xread(1,maxItem,new HashMap<String, StreamEntryID>(streamKey, new StreamEntryID(startId)));
    }
}
