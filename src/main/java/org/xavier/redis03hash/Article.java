package org.xavier.redis03hash;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

@Data
public class Article {
    private Jedis client;
    private String articleHash;

    public Article(Jedis client, String articleId) {
        this.client = client;
        this.articleHash = "article::" + articleId;
    }

    public boolean isExists() {
        return client.hexists(articleHash, "title");
    }

    public boolean create(String title, String content, String author) {
        if(isExists())
            return false;
        return setRedisHashData(title, content, author);
    }

    public Map<String, String> get() {
        Map<String, String> result = client.hgetAll(articleHash);
        return result;
    }

    public boolean update(String title, String content, String author){
        if(!isExists()){
            return false;
        }
        return setRedisHashData(title, content, author);
    }

    private boolean setRedisHashData(String title, String content, String author) {
        Map<String, String> map = new HashMap<>();
        map.put("title", title);
        map.put("content", content);
        map.put("author", author);
        String result = client.hmset(articleHash, map);
        if("OK".equals(result)){
            return true;
        } else {
            return false;
        }
    }
}
