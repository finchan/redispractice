package org.xavier.redis02string;

import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Article {
    private String articleId;
    private String idKey;
    private Jedis redis;
    private String titleKey;
    private String contentKey;
    private String authorKey;
    private String createAtKey;

    public String getIdKey() {
        return idKey;
    }

    public void setIdKey(String idKey) {
        this.idKey = idKey;
    }

    public Jedis getRedis() {
        return redis;
    }

    public void setRedis(Jedis redis) {
        this.redis = redis;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public String getContentKey() {
        return contentKey;
    }

    public void setContentKey(String contentKey) {
        this.contentKey = contentKey;
    }

    public String getAuthorKey() {
        return authorKey;
    }

    public void setAuthorKey(String authorKey) {
        this.authorKey = authorKey;
    }

    public String getCreateAtKey() {
        return createAtKey;
    }

    public void setCreateAtKey(String createAtKey) {
        this.createAtKey = createAtKey;
    }

    public Article(Jedis redis, String article_id) {
        this.redis = redis;
        this.articleId = article_id;
        this.idKey = "article::" + article_id + "::id";
        this.titleKey = "article::" + this.articleId + "::title";
        this.contentKey = "article::" + this.articleId + "::content";
        this.authorKey = "article::" + this.articleId + "::author";
        this.createAtKey = "article::" + this.articleId + "::create_at";
    }

    public boolean create(String title, String content, String author) {
        long result = redis.msetnx(this.idKey, this.articleId, this.titleKey, title,
                this.contentKey, content, this.authorKey, author,
                this.createAtKey, new Date().toString());
        return result == 1;
    }

    public boolean update(String title, String content, String author) {
        String updateInfo = "";
        String result = "";
        if(title != null)
            updateInfo += this.getTitleKey()+","+title+",";
        if(content != null)
            updateInfo += this.getContentKey() + "," + content+",";
        if(author != null)
            updateInfo += this.getAuthorKey() + "," + author+",";
        if(updateInfo.length() != 0) {
            updateInfo = updateInfo.substring(0, updateInfo.lastIndexOf(","));
            result = this.getRedis().mset(updateInfo.split(","));
        }
        return "OK".equals(result);
    }

    public Map<String, String> getById() {
        List<String> result = redis.mget(this.getIdKey(), this.getTitleKey(),
                this.getAuthorKey(), this.getContentKey(),
                this.getCreateAtKey());
        Map<String, String> map = new HashMap<String, String>();
        final Integer[] index = {0};
        result.forEach(str -> {
           switch (index[0]){
               case 0:
                   map.put(this.getIdKey(), str);
                   break;
               case 1:
                   map.put(this.getTitleKey(), str);
                   break;
               case 2:
                   map.put(this.getContentKey(), str);
                   break;
               case 3:
                   map.put(this.getAuthorKey(), str);
                   break;
               case 4:
                   map.put(this.getCreateAtKey(), str);
                   break;
               default:
                   break;
           }
           index[0] = index[0]+1;
        });
        System.out.println(map);
        return map;
    }

    /**
     * 返回文章内容
     * @return
     */
    public long getContentLength(){
        return redis.strlen(contentKey);
    }

    /**
     * 返回指定文章预览内容
     */
    public String getContentPreview(int previewLength) {
        int start_index = 0;
        int end_index = previewLength - 1;
        return redis.getrange(contentKey, start_index, end_index);
    }

}
