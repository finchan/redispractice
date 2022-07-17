package org.xavier.redis03hash;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xavier.redis03hash.Graph;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Slf4j
public class GraphTest {
    private Graph graph;

    @Before
    public void init() {
        graph = new Graph(new Jedis("127.0.0.1", 6379), "GRAPH");
    }

    @Test
    public void makeEdgeNameFromVertexs() {
        Assert.assertEquals("a->b",Graph.makeEdgeNameFromVertexs("a","b"));
    }

    @Test
    public void decomposeFromEdgeName() {
        log.info(Graph.decomposeFromEdgeName("a->b")[1]);
    }

    @Test
    public void addEdge() {
        graph.addEdge("a","b", "30");
        graph.addEdge("c","b", "25");
        graph.addEdge("b","d", "70");
        graph.addEdge("d","e", "19");
    }

    @Test
    public void removeEdge() {
        log.info(graph.removeEdge("a","b").toString());
    }

    @Test
    public void getEdgeWeight() {
        Assert.assertEquals("30",graph.getEdgeWeight("a","b"));
    }

    @Test
    public void hasEdge() {
        Assert.assertTrue(graph.hasEdge("a","b"));
    }

    @Test
    public void addMultiEdges() {
        List<Map<String, String>> edges = new ArrayList<>();
        Map<String, String> edge = new HashMap<>();
        edge.put("a->b", "30");
        Map<String, String> edge2 = new HashMap<>();
        edge.put("c->b", "25");
        edges.add(edge);
        edges.add(edge2);
        graph.addMultiEdges(edges);
    }

    @Test
    public void getMultiEdgeWeights() {
        String[][] vertexs = new String[][]{{"a","b"},{"c","b"}};
        List<String> weights = graph.getMultiEdgeWeights(vertexs);
        weights.forEach(weight->log.info(weight));
    }

    @Test
    public void getAllEdges() {
        graph.getAllEdges().forEach(edge->log.info(edge));
    }

    @Test
    public void getAllEdgesWithWeight() {
        graph.getAllEdgesWithWeight().forEach((key,value)->{
            log.info(key+": " + value);
        });
    }
}