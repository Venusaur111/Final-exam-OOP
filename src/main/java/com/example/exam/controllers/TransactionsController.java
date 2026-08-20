package com.example.exam.controllers;

import com.example.exam.models.Transaction;
import com.example.exam.models.TransactionType;
import com.example.exam.services.TransactionServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TransactionsController {

    private final TransactionServices transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByType(@RequestParam TransactionType type) {
        List<Transaction> transactions = transactionService.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }
}