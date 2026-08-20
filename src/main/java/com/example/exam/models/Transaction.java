package com.example.exam.models;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class Transaction {
    private UUID id;
    private LocalDateTime createdAt;
    private TransactionType transactionType;
    private BigDecimal amount;
    private UUID accountId;
    private String reason;
}