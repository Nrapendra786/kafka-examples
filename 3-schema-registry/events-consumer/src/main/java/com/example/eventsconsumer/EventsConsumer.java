package com.example.eventsconsumer;

import org.example.avro.NewCustomerCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class EventsConsumer {

    private static final Logger logger = LoggerFactory.getLogger(EventsConsumerApplication.class);

    @KafkaListener(groupId = "my-events-group", topics = Config.TOPIC,
            clientIdPrefix = "${spring.application.name}", concurrency = "3")
    public void consume(NewCustomerCreatedEvent event) {
        logger.info("Consumed message -> {}", event);

    }
}
