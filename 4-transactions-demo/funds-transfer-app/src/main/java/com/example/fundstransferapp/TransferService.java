package com.example.fundstransferapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import static com.example.fundstransferapp.MovementType.CREDIT;
import static com.example.fundstransferapp.MovementType.DEBIT;

@Component
public class TransferService {

    private static final Logger logger = LoggerFactory.getLogger(FundsTransferAppApplication.class);

    private KafkaTemplate<String, MovementEvent> kafkaTemplate;

    public TransferService(KafkaTemplate<String, MovementEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "transfers", groupId = "transfers-group")
    public void handle(TransferEvent transferEvent) {
        logger.info("processing event: {}", transferEvent);
//        kafkaTemplate.executeInTransaction(kafkaTemplate -> {
        kafkaTemplate.send("balances", new MovementEvent
                (CREDIT, transferEvent.amount(), transferEvent.to()));
            if (true) throw new RuntimeException("ops");
        kafkaTemplate.send("balances", new MovementEvent
                (DEBIT, transferEvent.amount(), transferEvent.from()));
//            return null;
//        });
    }

}

