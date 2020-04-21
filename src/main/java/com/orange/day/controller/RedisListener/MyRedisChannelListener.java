package com.orange.day.controller.RedisListener;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;


@Component
public class MyRedisChannelListener implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] bytes) {
        byte[] channel = message.getChannel();
        byte[] body = message.getBody();
        try {
            String content = new String(body, "UTF-8");
            String address = new String(channel, "UTF-8");
            System.out.println("get " + content + " from " + address);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
