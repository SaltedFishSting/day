package com.orange.day;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisListSetZSet {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void LSet() {
        List<Object> orangelist = new LinkedList<>();
        orangelist.add("1");
        orangelist.add("2");
        orangelist.add("3");
        orangelist.add("4");
        orangelist.add("5");
        Long l = redisTemplate.opsForList().rightPushAll("OrangeList", orangelist);
        System.out.println(l);
    }


    @Test
    public void LGet() {
        List<Object> orangelist = redisTemplate.opsForList().range("OrangeList", 0, 100);
        System.out.println(orangelist);
    }
}
