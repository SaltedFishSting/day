package com.orange.day.controller;

import com.orange.day.model.ResultBean;
import com.orange.day.model.ResultCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/redis")
public class RedisController extends BaseController {


    private final StringRedisTemplate stringRedisTemplate;


    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisController(StringRedisTemplate stringRedisTemplate, RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @PostMapping("/set")
    public ResultBean set(@RequestBody Map map) {
        String key;
        String value;
        key = map.get("key").toString();
        value = map.get("value").toString();
        ResultBean resultBean =new ResultBean();
        if (Strings.isEmpty(value) || Strings.isEmpty(key)) {
            resultBean.setCode(ResultCode.PARAMENT_ILLEGAL.code);
            resultBean.setDesc(ResultCode.PARAMENT_ILLEGAL.desc);
        }
        resultBean.setCode(ResultCode.SUCCESS.code);
        resultBean.setDesc(ResultCode.SUCCESS.desc);

        redisTemplate.opsForValue().set(key, value);

        return resultBean;
    }


    @GetMapping("/get/{key}")
    public ResultBean get(@PathVariable String key) {
        ResultBean resultBean =new ResultBean();
        resultBean.setCode(ResultCode.SUCCESS.code);
        resultBean.setDesc(ResultCode.SUCCESS.desc);
        resultBean.setData(key, redisTemplate.opsForValue().get(key));
        return resultBean;
    }


    @GetMapping("/publish/{message}")
    public ResultBean publish(@PathVariable String message) {
        redisTemplate.convertAndSend("orange",message);
        ResultBean resultBean =new ResultBean();
        Map map=new HashMap();
        resultBean.setCode(ResultCode.SUCCESS.code);
        resultBean.setDesc(ResultCode.SUCCESS.desc);
        resultBean.setData("publish", message);
        return resultBean;
    }

}
