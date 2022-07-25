package org.xavier.redis08bitmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class ActionRecorderTest {
    ActionRecorder action;

    @Before
    public void setUp() throws Exception {
        action = new ActionRecorder(new Jedis("127.0.0.1", 6379), ActionRecorder.makeActionKey("visit_website"));
    }

    @Test
    public void performBy() {
        action.performBy(100L);
        action.performBy(101L);
    }

    @Test
    public void isPerformedBy() {
        Assert.assertTrue(action.isPerformedBy(100L));
    }

    @Test
    public void countPerformed() {
        Assert.assertEquals(2, action.countPerformed().longValue());
    }
}