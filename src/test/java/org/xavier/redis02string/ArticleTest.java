package org.xavier.redis02string;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;
@Slf4j
public class ArticleTest {
    @Test
    public void testArticle() {
        Article article = new Article(new Jedis("127.0.0.1"), "2");
        article.create("TITLE", "CONTENT", "AUTHOR");
        List<String> results = article.getRedis().mget(article.getIdKey(), article.getTitleKey(),
                article.getAuthorKey(), article.getContentKey(),
                article.getCreateAtKey());
        results.forEach(str -> System.out.println(str));
        article.getById();
        Assert.assertTrue(article.update("T_", "C_CONTENT_UPDATED", "A_"));
        System.out.println(article.getContentLength());
        System.out.println(article.getContentPreview(3));
    }

    @Test
    public void testContentChineseGetRange(){
        Jedis redis = new Jedis("127.0.0.1", 6379);
        redis.set("AAA", "Redis中文处理");
        log.info(redis.getrange("AAA", 4, 4));
        //一个汉字3个字节
        log.info(redis.getrange("AAA", 5, 7));
        //通过将redis中的文字取出来，用开发语言处理（Java默认utf-8,按照字符处理索引）
        String aaa = redis.get("AAA");
        log.info(aaa.substring(5));
        redis.setrange("AAA", 100L,"BBB");
        log.info(redis.get("AAA"));
    }
}