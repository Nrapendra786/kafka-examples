package com.example.transactionalproducer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class Config {

    public static final String WORDS = "words";

    @Bean
    public NewTopic words() {
        return TopicBuilder.name(WORDS)
                .partitions(1)
                .replicas(1)
                .build();
    }

}
