package com.example.fundstransferapp;

import java.math.BigDecimal;

record MovementEvent(MovementType type, BigDecimal amount, String account) {
}
