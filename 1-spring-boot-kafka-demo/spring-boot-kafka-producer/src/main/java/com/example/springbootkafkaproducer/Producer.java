package com.example.springbootkafkaproducer;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class Producer {

    public static final String TOPIC = "my-topic-java";

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    private KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void send(String key, String value) {
        CompletableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(TOPIC, key, value);

        future.whenComplete((result, throwable) -> {
            if (result != null) {
                RecordMetadata recordMetadata = result.getRecordMetadata();
                logger.info("success, topic: {}, partition: {}, offset: {}",
                        recordMetadata.topic(),
                        recordMetadata.partition(),
                        recordMetadata.offset());
            } else {
                logger.info("error occurred:" + throwable);
            }
        });
    }

    public void send(Integer partition, String key, String value) throws ExecutionException, InterruptedException {
        SendResult<String, String> result = kafkaTemplate
                .send(TOPIC, partition, null, "Hello World").get();

        RecordMetadata recordMetadata = result.getRecordMetadata();

        logger.info("success, topic: {}, partition: {}, offset: {}",
                recordMetadata.topic(),
                recordMetadata.partition(),
                recordMetadata.offset());
    }


}
