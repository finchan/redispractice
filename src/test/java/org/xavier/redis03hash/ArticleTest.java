package org.xavier.redis03hash;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.Map;

import static org.junit.Assert.*;
@Data
@Slf4j
public class ArticleTest {
    private Article article;
    @Before
    public void setUp() throws Exception {
        article = new Article(new Jedis("127.0.0.1", 6379), "00001");
    }

    @Test
    public void isExists() {
        boolean result = article.isExists();
        log.info(String.valueOf(result));
    }

    @Test
    public void create() {
        boolean result = article.create("Title - Redis使用指南", "介绍Redis的使用指令", "黄建宏");
        log.info(String.valueOf(result));
    }

    @Test
    public void get() {
        Map<String, String> result = article.get();
        result.forEach((key, value)->{
            log.info("Key: " + article.getArticleHash() + "\t Field: " + key + "\tValue: " + value);
        });
    }

    @Test
    public void update() {
        boolean result = article.update("Title - Redis使用指南 - 更新", "介绍Redis的使用指令", "黄建宏");
        log.info(String.valueOf(result));
    }
}