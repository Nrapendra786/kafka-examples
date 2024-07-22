package com.example.springbootkafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;

@RestController
public class ProducerWebClient {

    private static final Logger logger = LoggerFactory.getLogger(ProducerWebClient.class);

    private final Producer producer;

    public ProducerWebClient(Producer producer) {
        this.producer = producer;
    }

    @PostMapping("/messages")
    public void sendMessage(@RequestBody String message) {
        String payload = message.trim();
        logger.info("Sending payload {}", payload);
        producer.send(null, payload);
    }

    @PostMapping("/many-messages")
    public void multipleMessages(@RequestBody Integer nr) {
        logger.info("Sending {} nr. of messages", nr);
        IntStream.range(0, nr).forEach(value -> producer.send("key_" + value, "value_" + value));
    }

    @PostMapping("/partition")
    public void sendToPartition(@RequestBody int partition) throws ExecutionException, InterruptedException {
        logger.info("Sending hello message to partition {}", partition);
        producer.send(partition, null, "Hello!");
    }


}
