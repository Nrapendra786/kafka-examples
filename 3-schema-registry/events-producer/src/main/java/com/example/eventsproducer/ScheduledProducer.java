package com.example.eventsproducer;

import org.example.avro.NewCustomerCreatedEvent;
import org.instancio.Instancio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledProducer {
    private static final Logger logger = LoggerFactory.getLogger(EventsProducerApplication.class);
    private KafkaTemplate<String, NewCustomerCreatedEvent> kafkaTemplate;

    public ScheduledProducer(KafkaTemplate<String, NewCustomerCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Scheduled(fixedRate = 2000)
    public void generatePayload() {
        NewCustomerCreatedEvent event = Instancio.create(NewCustomerCreatedEvent.class);
        logger.info("sending to topic '{}' the event '{}'", Config.TOPIC, event);
        kafkaTemplate.send(Config.TOPIC, event);
    }
}

