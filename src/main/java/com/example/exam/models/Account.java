package com.example.exam.models;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Account {
    private UUID id;
    private AccountType accountType;
}