package org.xavier.redis02string;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Base64;

import static org.junit.Assert.*;

public class CacheTest {
    Jedis redis = null;
    Cache cache = null;

    @Before
    public void init() {
        redis = new Jedis("127.0.0.1", 6379);
        cache = new Cache(redis);
    }

    @Test
    public void setAndGet() {
        cache.set("greeting-page", "<html><p>Hello World</p></html>");
        Assert.assertEquals("<html><p>Hello World</p></html>", cache.get("greeting-page"));
    }

    @Test
    public void update() {
        //Redis - Jedis调用set命令以及getset命令，不可以设置为null
        //Redis - 对于get命令及getset命令调用，无对应的key值，返回null
        cache.set("greeting-page", "<html><p>Hello World</p></html>");
        Assert.assertEquals("<html><p>Hello World</p></html>", cache.update("greeting-page", "<html><p>Good Morning</p></html>"));
        Assert.assertEquals("<html><p>Good Morning</p></html>", cache.get("greeting-page"));
    }

    @Test
    public void cacheBinary() throws IOException {
        File file = new File("E:\\Projects\\Redis\\src\\main\\resources\\redis02\\redis-logo.jpg");
        FileInputStream in = new FileInputStream(file);
        byte[] bytes = new byte[(int)file.length()];
        in.read(bytes);
        String byteS = Base64.getEncoder().encodeToString(bytes);
        redis.set("redis-log", byteS);
        in.close();

        File file2 = new File("E:\\Projects\\Redis\\src\\main\\resources\\redis02\\redis-logo-recover.jpg");
        FileOutputStream out = new FileOutputStream(file2);
        String byteS2 = redis.get("redis-log");
        byte[] bytes2 = Base64.getDecoder().decode(byteS2);
        out.write(bytes2);
        out.close();


    }
}