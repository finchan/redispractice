package org.xavier.redis10stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.StreamEntryID;
import redis.clients.jedis.params.XReadParams;
import redis.clients.jedis.resps.StreamEntry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@Slf4j
public class MessageQueue {
    private Jedis client;
    private String streamKey;

    public void addMessage(Map<String, String> element) {
        StreamEntryID id = client.xadd(streamKey, StreamEntryID.NEW_ENTRY, element);
        log.info(id.toString());
    }

    public List<StreamEntry> getMessage(String messageId) {
        List<StreamEntry> result =  client.xrange(streamKey, new StreamEntryID(messageId), new StreamEntryID(messageId),1);
        if(result == null || result.size() == 0) return null;
        return result;
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

    public List<Map.Entry<String, List<StreamEntry>>> iterate(String startId, int maxItem) {
        Map<String, StreamEntryID> entry = new HashMap<>();
        entry.put(streamKey, new StreamEntryID(startId));
        XReadParams params = new XReadParams();
        params.count(maxItem);
        params.block(0);
        List<Map.Entry<String, List<StreamEntry>>> result = client.xread(params, entry);
        return result;
    }
}
