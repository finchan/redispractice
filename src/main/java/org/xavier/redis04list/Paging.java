package org.xavier.redis04list;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.List;

@Data
public class Paging {
    private Jedis client;
    private String key;

    public Paging(Jedis client, String key) {
        this.client = client;
        this.key = key;
    }

    public void add(String content){
        client.lpush(key, content);
    }

    public List<String> getPage(Integer pageNumber, Integer itemPerPage) {
        Integer startIndex = (pageNumber-1)*itemPerPage;
        Integer endIndex = pageNumber*itemPerPage-1;
        return client.lrange(key, startIndex,endIndex);
    }

    public Long size() {
        return client.llen(key);
    }

}
