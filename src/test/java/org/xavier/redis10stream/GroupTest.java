package org.xavier.redis10stream;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.resps.StreamConsumersInfo;
import redis.clients.jedis.resps.StreamEntry;
import redis.clients.jedis.resps.StreamGroupInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
public class GroupTest {
    private Group group;

    @Before
    public void setUp() throws Exception {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        //FlushDB
        jedis.flushDB();
        //Create Stream
        Map<String, String> member1 = new HashMap<>();
        member1.put("k1", "v1");
        Map<String, String> member2 = new HashMap<>();
        member2.put("k2", "v2");
        Map<String, String> member3 = new HashMap<>();
        member3.put("k3", "v3");
        Map<String, String> member4 = new HashMap<>();
        member4.put("k4", "v4");
        jedis.xadd("s1", StreamEntryID.NEW_ENTRY, member1);
        jedis.xadd("s1", StreamEntryID.NEW_ENTRY, member2);
        jedis.xadd("s1", StreamEntryID.NEW_ENTRY, member3);
        jedis.xadd("s1", StreamEntryID.NEW_ENTRY, member4);
        group = new Group(jedis, "s1", "group1");
    }

    @Test
    public void createGroup() {
        group.createGroup("0-0");
    }

    @Test
    public void destroyGroup() {
        group.destroyGroup();
    }

    @Test
    public void readMessage() {
        group.createGroup("0-0");
        List<Map.Entry<String, List<StreamEntry>>> result = group.readMessage("c1", 2);
        Optional.ofNullable(result).ifPresent(list -> {
            log.info(String.valueOf(list.size()));
            if(list.size() == 1) {
                list.get(0).getValue().forEach(se->{
                    log.info(se.getID()+"\t"+se.getFields().toString());
                });
            }
        });
    }

    @Test
    public void ackMessage() {
        group.createGroup("0-0");
        List<Map.Entry<String, List<StreamEntry>>> result = group.readMessage("c1", 1);
        Optional.ofNullable(result).ifPresent(list -> {
            log.info(String.valueOf(list.size()));
            if(list.size() == 1) {
                list.get(0).getValue().forEach(se->{
                    String messageId = se.getID().toString();
                    //Ack Message
                    Long ackNumber = group.ackMessage(messageId);
                    Optional.ofNullable(ackNumber).ifPresent(ack->{
                        log.info("Acknowledge Message");
                    });
                });
            }
        });

    }

    @Test
    public void info() {
        group.createGroup("0-0");
        List<StreamGroupInfo> groupInfo = group.info();
        Optional.ofNullable(groupInfo).ifPresent(gi ->  {
            log.info(gi.toString());
        });
    }

    @Test
    public void consumerInfo() {
        group.createGroup("0-0");
        List<Map.Entry<String, List<StreamEntry>>> result = group.readMessage("c1", 1);
        List<StreamConsumersInfo> consumerInfo = group.consumerInfo();
        Optional.ofNullable(consumerInfo).ifPresent(ci ->  {
            log.info(ci.toString());
        });
    }

    @Test
    public void deleteConsumer() {
        group.createGroup("0-0");
        List<Map.Entry<String, List<StreamEntry>>> result = group.readMessage("c1", 1);
        group.deleteConsumer("c1");
    }
}