package com.orange.day;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisGeo {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    String cityGeoKey = "cityGeoKey";

    @Test
    public void testAdd() {

        Map<Object, Point> cityInfos = new HashMap<>();
        cityInfos.put("合肥", new Point(117.17, 31.52));
        cityInfos.put("安庆", new Point(117.02, 30.31));
        cityInfos.put("华北", new Point(116.47, 33.57));
        cityInfos.put("苏州", new Point(116.58, 33.38));
        cityInfos.put("阜阳", new Point(115.48, 32.54));
        cityInfos.put("蚌埠", new Point(117.21, 32.56));
        cityInfos.put("黄山", new Point(118.18, 29.43));
        cityInfos.put("北京", new Point(116.40, 39.90));
        cityInfos.put("上海", new Point(121.47, 31.23));

        GeoOperations<String, Object> geoOperations = redisTemplate.opsForGeo();
        Long addedNum = geoOperations.add(cityGeoKey, cityInfos);
        System.out.println(addedNum);
    }

    @Test
    public void testGeoGet() {
        List<Point> points = redisTemplate.opsForGeo().position(cityGeoKey, "北京", "上海", "深圳");
        System.out.println(points);
    }

    @Test
    public void testDist() {
        Distance distance = redisTemplate.opsForGeo()
                .distance(cityGeoKey, "北京", "上海", RedisGeoCommands.DistanceUnit.KILOMETERS);
        System.out.println(distance);
    }

    @Test
    public void testNearByXY() {
        //longitude,latitude
        Circle circle = new Circle(116.405285, 39.904989, Metrics.KILOMETERS.getMultiplier()*10000);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo()
                .radius(cityGeoKey, circle, args);
        System.out.println(results);
    }

    @Test
    public void testNearByPlace() {
        Distance distance = new Distance(5, Metrics.KILOMETERS);
        RedisGeoCommands.GeoRadiusCommandArgs args = RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs().includeDistance().includeCoordinates().sortAscending().limit(5);
        GeoResults<RedisGeoCommands.GeoLocation<Object>> results = redisTemplate.opsForGeo()
                .radius(cityGeoKey, "北京", distance, args);
        System.out.println(results);
    }

    @Test
    public void testGeoHash() {
        List<String> results = redisTemplate.opsForGeo()
                .hash(cityGeoKey, "北京", "上海", "深圳");
        System.out.println(results);
    }


    @Test
    public void redisgao() {


    }

}
