package com.example.exam.controllers;

import com.example.exam.models.Transaction;
import com.example.exam.services.AccountServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping
public class AccountsController {

    private final AccountServices accountService;

    public AccountsController(AccountServices accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable String id) {
        List<Transaction> transactions = accountService.getTransactionsByAccountId(id);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String id) {
        BigDecimal balance = accountService.getAccountBalance(id);
        return ResponseEntity.ok(balance);
    }
}