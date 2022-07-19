package org.xavier.redis05set;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;
@Data
@Slf4j
public class VoteTest {
    private Vote vote;
    @Before
    public void setUp() throws Exception {
        vote = new Vote(new Jedis("127.0.0.1", 6379), "WEBSITE");
    }

    @Test
    public void isVoted() {
        Assert.assertTrue(vote.isVoted("Xavier"));
    }

    @Test
    public void voteUp() {
        vote.voteUp("Xavier");
        vote.voteUp("Javier");
        vote.voteUp("Zavier");
    }

    @Test
    public void voteDown() {
        vote.voteDown("Avier");
        vote.voteDown("Bavier");
        vote.voteDown("Cavier");
    }

    @Test
    public void undo() {
        vote.undo("Cavier");
    }

    @Test
    public void voteUpCount() {
        log.info(vote.voteUpCount().toString());
    }

    @Test
    public void voteDownCount() {
        log.info(vote.voteDownCount().toString());
    }

    @Test
    public void getAllVoteUpUsers() {
        vote.getAllVoteUpUsers().forEach(s->log.info(s));
    }

    @Test
    public void getAllVoteDownUsers() {
        vote.getAllVoteDownUsers().forEach(s->log.info(s));
    }
}