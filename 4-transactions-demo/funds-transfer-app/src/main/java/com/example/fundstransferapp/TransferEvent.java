package com.example.fundstransferapp;

import java.math.BigDecimal;

record TransferEvent(String from, String to, BigDecimal amount) {
}
