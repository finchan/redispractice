package org.xavier.redis07hyperloglog;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
@Slf4j
public class UniqueCounterMergerTest {
    private UniqueCounterMerger merger;
    @Before
    public void setUp() throws Exception {
        merger = new UniqueCounterMerger(new Jedis("127.0.0.1", 6379));
    }

    @Test
    public void merge() {
        List<String> sourcesKeys = new ArrayList<>();
        String source1 = "Source1";
        String source2 = "Source2";
        merger.getClient().pfadd(source1, "A");
        merger.getClient().pfadd(source2, "A");
        merger.getClient().pfadd(source2, "B");
        merger.getClient().pfadd(source2, "C");
        sourcesKeys.add(source1);
        sourcesKeys.add(source2);
        merger.merge("DESTINATION", sourcesKeys);
        Long size = merger.getClient().pfcount("DESTINATION");
        Assert.assertEquals(3, Optional.ofNullable(size).get().longValue());
    }
}