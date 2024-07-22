package com.example.eventsproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventsProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventsProducerApplication.class, args);
    }

}
