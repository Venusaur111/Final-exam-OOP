package com.example.exam.services;

import com.example.exam.models.Transaction;
import com.example.exam.models.TransactionType;
import com.example.exam.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TransactionServicesTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServices transactionServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void it_should_return_a_list_of_transactions_filtered_by_a_specific_transaction_type() {
        TransactionType type = TransactionType.IN;
        Transaction transaction1 = Transaction.builder().transactionType(type).build();
        Transaction transaction2 = Transaction.builder().transactionType(type).build();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findByTransactionType(type)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = transactionServices.getTransactionsByType(type);

        assertEquals(expectedTransactions, actualTransactions);
        verify(transactionRepository, times(1)).findByTransactionType(type);
    }

    @Test
    void it_should_successfully_save_and_return_a_newly_created_transaction() {
        Transaction newTransaction = Transaction.builder()
                .id(UUID.randomUUID())
                .amount(new BigDecimal("100.00"))
                .transactionType(TransactionType.IN)
                .build();

        when(transactionRepository.save(newTransaction)).thenReturn(newTransaction);

        Transaction createdTransaction = transactionServices.createTransaction(newTransaction);

        assertEquals(newTransaction, createdTransaction);
        verify(transactionRepository, times(1)).save(newTransaction);
    }
}