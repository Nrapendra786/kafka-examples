package com.example.springbootkafkaconsumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final String TOPIC = "my-topic-java";
    private static final String TOPIC_DLT = "my-topic-java.DLT";
    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @KafkaListener(groupId = "my-group-java", topics = TOPIC, clientIdPrefix = "${spring.application.name}",
            concurrency = "3"
//            containerFactory = "dltContainerFactory2"
    )
    public void consume(String message) {
        if (message.startsWith("poison-pill")) {
            throw new RuntimeException("failed processing message:" + message);
        }
        logger.info("Consumed message -> {}", message);
    }

    @KafkaListener(groupId = "my-group-dlt", topics = TOPIC_DLT, clientIdPrefix = "${spring.application.name}")
    public void dltConsume(String message) {
        logger.info("processing message from dead letter topic {} : {}", TOPIC_DLT, message);
    }


}
