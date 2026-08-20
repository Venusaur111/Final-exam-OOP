package com.example.exam.controllers;

import com.example.exam.models.Transaction;
import com.example.exam.services.AccountServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@NoArgsConstructor
public class AccountsController {

    private AccountServices accountServices;

    @GetMapping("/accounts/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByAccountId(@PathVariable String id) {
        List<Transaction> transactions = accountServices.getTransactionsByAccountId(id);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/account/{id}/balance")
    public ResponseEntity<BigDecimal> getAccountBalance(@PathVariable String id) {
        BigDecimal balance = accountServices.getAccountBalance(id);
        return ResponseEntity.ok(balance);
    }
}