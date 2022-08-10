package org.xavier.redis13transaction;

import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;

public class PipelineAndTransactionTest {
    private PipelineAndTransaction pipelineAndTransaction;

    @Before
    public void setUp() throws Exception {
        pipelineAndTransaction = new PipelineAndTransaction(new Jedis("127.0.0.1", 6379));
    }

    @Test
    public void mlpop() {
        List<Object> result = pipelineAndTransaction.mlpop("pipeline_list", 4);
        System.out.println("test");
    }
}