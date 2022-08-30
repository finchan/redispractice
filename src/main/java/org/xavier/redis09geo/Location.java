package org.xavier.redis09geo;

import lombok.AllArgsConstructor;
import lombok.Data;
import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

@Data
@AllArgsConstructor
public class Location {
    private Jedis client;
    private String key;

    public void pin(String user, double longitude, double latitude) {
        client.geoadd(key, longitude, latitude, user);
    }

    public List<GeoCoordinate> get(String user) {
        return client.geopos(key, user);
    }

    public double calculateDistance(String userA, String userB) {
        return client.geodist(key, userA, userB);
    }

    public List<GeoRadiusResponse> findNearby(String user, Long radius) {
        List<GeoRadiusResponse> result = client.georadiusByMember(key, user, 1, GeoUnit.KM);
        result.remove(user);
        return result;
    }
}
