package org.xavier.redis02string;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;

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
}