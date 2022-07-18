package org.xavier.redis04list;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.util.List;

import static org.junit.Assert.*;

@Data
@Slf4j
public class TODOListTest {
    private TODOList todoList;
    @Before
    public void setUp() throws Exception {
        todoList = new TODOList(new Jedis("127.0.0.1", 6379), "USER001");
    }

    @Test
    public void add() {
        todoList.add("待办事项1");
        todoList.add("待办事项2");
        todoList.add("待办事项3");
        todoList.add("待办事项4");
        todoList.add("待办事项5");
    }

    @Test
    public void remove() {
        todoList.remove("待办事项3");
    }

    @Test
    public void done() {
        todoList.remove("待办事项2");
        todoList.done("待办事项2");
    }

    @Test
    public void showTODOList() {
        List<String> todo = todoList.showTODOList();
        todo.forEach(a->log.info(a));
    }

    @Test
    public void showDONEList() {
        List<String> todo = todoList.showDONEList();
        todo.forEach(a->log.info(a));
    }
}