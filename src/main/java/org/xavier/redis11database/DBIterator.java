package org.xavier.redis11database;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Data
public class DBIterator {
    private Jedis client;
    private String match;
    private Integer count;
    private String currentCursor;
    private Boolean iteratorIsOver;

    public List<String> next() {
        if(iteratorIsOver) return null;
        ScanParams params = new ScanParams();
        params.match(this.match);
        params.count(this.count);
        ScanResult<String> result = this.client.scan(String.valueOf(currentCursor), params);
        Optional.ofNullable(result.getCursor()).ifPresent(cursor->{
            if("0".equals(cursor)) {
                this.iteratorIsOver = true;
            } else {
                this.currentCursor = cursor;
            }
        });
        return result.getResult();
    }
}
