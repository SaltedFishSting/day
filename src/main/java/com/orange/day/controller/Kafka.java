package com.orange.day.controller;

import com.orange.day.model.ResultBean;
import com.orange.day.model.ResultCode;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/kafka")
public class Kafka extends BaseController {


    public class KafkaSendResultHandler implements ProducerListener {

        @Override
        public void onSuccess(ProducerRecord producerRecord, RecordMetadata recordMetadata) {
            System.out.println("orangeSUCCESS" + producerRecord.value());
        }

        @Override
        public void onError(ProducerRecord producerRecord, Exception exception) {
            System.out.println("orangeFAIL" + producerRecord.value());
        }
    }

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public Kafka(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaTemplate.setProducerListener(new KafkaSendResultHandler());
    }


    @GetMapping("/put/{key}")
    public ResultBean testAdd(@PathVariable String key) {
        try {
            for (int i = 0; i < 100; i++) {
                kafkaTemplate.send("topic.quick.orange", i + "", key + i).get();
            }
        } catch (Exception e) {

        }
        ResultBean resultBean = new ResultBean();
        resultBean.setCode(ResultCode.SUCCESS.code);
        resultBean.setDesc(ResultCode.SUCCESS.desc);
        return resultBean;
    }


    @KafkaListener(id = "orange1", topics = {"topic.quick.orange"})
//    @SendTo("HelloWord")
    public String consumer(ConsumerRecord record) {
        System.out.println("#######################");
        System.out.println(record.value());
        System.out.println(record.offset());
        return record.value().toString();
    }

    @KafkaListener(id = "HelloWord1", topics = {"HelloWord"})
    public void HelloWordConsumer(ConsumerRecord record) {
        System.out.println("***********************");
        System.out.println(record.value());
        System.out.println(record.offset());
    }



}
