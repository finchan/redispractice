package org.xavier.redis03hash;

import lombok.Data;
import redis.clients.jedis.Jedis;

import java.util.*;

@Data
public class Graph {
    private Jedis client;
    private String hashKey;

    public Graph(Jedis client, String hashKey) {
        this.client = client;
        this.hashKey = hashKey;
    }

    public static String makeEdgeNameFromVertexs(String start, String end){
        return start+"->"+end;
    }

    public static String[] decomposeFromEdgeName(String edgeName){
        return edgeName.split("->");
    }

    public Long addEdge(String start, String end, String weight) {
        String edge = makeEdgeNameFromVertexs(start, end);
        return client.hset(hashKey, edge, weight);
    }

    public Long removeEdge(String start, String end) {
        String edge = makeEdgeNameFromVertexs(start, end);
        return client.hdel(hashKey, edge);
    }

    public String getEdgeWeight(String start, String end) {
        String edge = makeEdgeNameFromVertexs(start, end);
        return client.hget(hashKey, edge);
    }

    public Boolean hasEdge(String start, String end) {
        String edge = makeEdgeNameFromVertexs(start, end);
        return client.hexists(hashKey, edge);
    }

    public String addMultiEdges(List<Map<String, String>> edges){
        Map<String, String> multiEdges = new HashMap<String, String>();
        edges.forEach(edge->{
            edge.forEach((key, value)->{
                multiEdges.put(key, value);
            });
        });
        return client.hmset(hashKey, multiEdges);
    }

    public List<String> getMultiEdgeWeights(String[][] edges){
        String[] edgeArray = new String[edges.length];
        final int[] index = new int[]{0};
        Arrays.asList(edges).forEach(edge->{
            String edgeName = makeEdgeNameFromVertexs(edge[0], edge[1]);
            edgeArray[index[0]] = edgeName;
            index[0]++;
        });
        return client.hmget(hashKey, (String[])edgeArray);
    }

    public Set<String> getAllEdges(){
        Set<String> keys = client.hkeys(hashKey);
        return keys;
    }

    public Map<String, String> getAllEdgesWithWeight() {
        return client.hgetAll(hashKey);
    }
}
