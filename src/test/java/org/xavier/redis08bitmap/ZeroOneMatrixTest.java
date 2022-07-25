package org.xavier.redis08bitmap;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class ZeroOneMatrixTest {
    private ZeroOneMatrix matrix;
    @Before
    public void setUp() throws Exception {
        matrix = new ZeroOneMatrix(new Jedis("127.0.0.1", 6379), "0-1", 3, 3);
    }

    @Test
    public void set() {
        matrix.set(0,0,0);
        matrix.set(0,1,1);
        matrix.set(0,2,0);
        matrix.set(0,3,0);
        matrix.set(1,0,0);
        matrix.set(1,1,0);
        matrix.set(1,2,1);
        matrix.set(1,3,0);
        matrix.set(2,0,1);
        matrix.set(2,1,1);
        matrix.set(2,2,1);
        matrix.set(2,3,0);
        matrix.set(3,0,0);
        matrix.set(3,1,1);
        matrix.set(3,2,1);
        matrix.set(3,3,1);
    }

    @Test
    public void get() {
        Assert.assertEquals(1, matrix.get(3,3));
    }

    @Test
    public void show() {
        matrix.show();
    }
}