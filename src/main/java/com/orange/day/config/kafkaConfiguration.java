package com.orange.day.config;


import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;

@Configuration
@EnableKafka
public class kafkaConfiguration {

    @Autowired
    private ConsumerFactory consumerFactory;
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    //创建TopicName为topic.quick.initial的Topic并设置分区数为8以及副本数为1
    @Bean
    public NewTopic initialTopic() {
        return new NewTopic("topic.quick.orange", 8, (short) 1);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setReplyTemplate(kafkaTemplate);
        return factory;
    }

//    @Bean
//    public KafkaMessageListenerContainer demoListenerContainer() {
//        ContainerProperties properties = new ContainerProperties("topic.quick.orange");
//        properties.setGroupId("bean");
//        properties.setMessageListener(new MessageListener<String, String>() {
//            @Override
//            public void onMessage(ConsumerRecord record) {
//                System.out.println("**************");
//                System.out.println(record.value());
//            }
//        });
//
//        return new KafkaMessageListenerContainer(consumerFactory, properties);
//    }

}
