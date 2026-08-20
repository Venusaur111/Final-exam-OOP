package com.example.exam.services;

import com.example.exam.models.Transaction;
import com.example.exam.repositories.AccountRepository;
import com.example.exam.repositories.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountServices {

    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;

    public List<Transaction> getTransactionsByAccountId(String accountId) {
        return transactionRepository.findByAccountId(accountId);
    }

    public BigDecimal getAccountBalance(String accountId) {
        List<Transaction> transactions = transactionRepository.findByAccountId(accountId);
        BigDecimal balance = BigDecimal.ZERO;

        for (Transaction tx : transactions) {
            if (tx.getTransactionType().name().equals("IN")) {
                balance = balance.add(tx.getAmount());
            } else if (tx.getTransactionType().name().equals("OUT")) {
                balance = balance.subtract(tx.getAmount());
            }
        }
        return balance;
    }
}