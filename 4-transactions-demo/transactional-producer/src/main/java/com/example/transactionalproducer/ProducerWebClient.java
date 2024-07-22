package com.example.transactionalproducer;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ProducerWebClient {

    private final Producer producer;

    public ProducerWebClient(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/messages1")
    public void send(@RequestBody String message) {
        producer.process1(message);
    }

    @PostMapping("/messages2")
    public void send2(@RequestBody String message) throws IOException {
        producer.process2(message);
    }


}
