package com.orange.day;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HyperLogLogOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.LinkedList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisHyperLogLog {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void set() {
        HyperLogLogOperations<String, Object> hyperLogLogOperations = redisTemplate.opsForHyperLogLog();

        Long l = hyperLogLogOperations.add("book", "a", "b", "c");
        hyperLogLogOperations.add("bag", "a", "e", "d");
        hyperLogLogOperations.add("del", "f", "g", "h");
        System.out.println(l.toString());


    }

    @Test
    public void get() {
        HyperLogLogOperations<String, Object> hyperLogLogOperations = redisTemplate.opsForHyperLogLog();
        System.out.println(hyperLogLogOperations.size("book"));
        System.out.println(hyperLogLogOperations.size("bag"));
        System.out.println(hyperLogLogOperations.size("del"));
        System.out.println(hyperLogLogOperations.size("book", "bag", "del"));
        hyperLogLogOperations.delete("del");
        System.out.println(hyperLogLogOperations.size("book", "bag", "del"));
        hyperLogLogOperations.union("total", "book", "bag", "del");
        System.out.println(hyperLogLogOperations.size("total"));
    }

}
