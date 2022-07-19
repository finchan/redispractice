package org.xavier.redis05set;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.Set;

@Data
public class Vote {
    private Jedis client;
    private String voteUpSet;
    private String voteDownSet;

    public Vote(Jedis client, String voteTarget) {
        this.client = client;
        this.voteUpSet = voteUpKey(voteTarget);
        this.voteDownSet = voteDownKey(voteTarget);
    }

    private String voteUpKey(String voteTarget) {
        return voteTarget+"::VOTEUP";
    }

    private String voteDownKey(String voteTarget) {
        return voteTarget+"::VOTEDOWN";
    }

    public boolean isVoted(String user) {
        return client.sismember(voteUpSet, user) || client.sismember(voteDownSet, user);
    }

    public boolean voteUp(String user) {
        if(isVoted(user))
            return false;
        return client.sadd(voteUpSet, user) == 1;
    }

    public boolean voteDown(String user) {
        if(isVoted(user))
            return false;
        return client.sadd(voteDownSet, user) == 1;
    }

    public boolean undo(String user) {
        return client.srem(voteUpSet, user) == 1 || client.srem(voteDownSet, user) == 1;
    }

    public Long voteUpCount() {
        return client.scard(voteUpSet);
    }

    public Long voteDownCount() {
        return client.scard(voteDownSet);
    }

    public Set<String> getAllVoteUpUsers(){
        return client.smembers(voteUpSet);
    }

    public Set<String> getAllVoteDownUsers(){
        return client.smembers(voteDownSet);
    }
}
