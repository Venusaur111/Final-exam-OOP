package com.example.exam.controllers;

import com.example.exam.models.Transaction;
import com.example.exam.models.TransactionType;
import com.example.exam.services.TransactionServices;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionsController {

    private TransactionServices transactionServices;

    @GetMapping("/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsByType(@RequestParam TransactionType type) {
        List<Transaction> transactions = transactionServices.getTransactionsByType(type);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transaction")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        Transaction createdTransaction = transactionServices.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTransaction);
    }
}