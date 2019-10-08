package com.orange.day;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Kafka {


    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Test
    public void testAdd() {

        for(int i=0;i<100;i++){
            kafkaTemplate.send("HelloWorld", "hello,kafka  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS")));

        }

    }


    @KafkaListener(topics = {"HelloWorld"})
    public void consumer(String message) {
        System.out.println(message);
    }


}
