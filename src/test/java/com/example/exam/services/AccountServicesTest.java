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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class AccountServicesTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountServices accountServices;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void it_should_return_all_transactions_associated_with_a_given_account_id() {
        UUID accountId = UUID.randomUUID();
        Transaction transaction1 = Transaction.builder().id(UUID.randomUUID()).accountId(accountId).build();
        Transaction transaction2 = Transaction.builder().id(UUID.randomUUID()).accountId(accountId).build();
        List<Transaction> expectedTransactions = Arrays.asList(transaction1, transaction2);

        when(transactionRepository.findByAccountId(accountId)).thenReturn(expectedTransactions);

        List<Transaction> actualTransactions = accountServices.getTransactionsByAccountId(accountId);

        assertEquals(expectedTransactions, actualTransactions);
        verify(transactionRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    void it_should_calculate_the_correct_account_balance_when_there_are_incoming_and_outgoing_transactions() {
        UUID accountId = UUID.randomUUID();
        Transaction incomingTransaction = Transaction.builder()
                .transactionType(TransactionType.IN)
                .amount(new BigDecimal("500.00"))
                .accountId(accountId)
                .build();
        Transaction outgoingTransaction = Transaction.builder()
                .transactionType(TransactionType.OUT)
                .amount(new BigDecimal("150.00"))
                .accountId(accountId)
                .build();

        when(transactionRepository.findByAccountId(accountId))
                .thenReturn(Arrays.asList(incomingTransaction, outgoingTransaction));

        BigDecimal balance = accountServices.getAccountBalance(accountId);

        assertEquals(new BigDecimal("350.00"), balance);
        verify(transactionRepository, times(1)).findByAccountId(accountId);
    }

    @Test
    viod it_should_return_zero_balance_when_the_account_has_no_transactions() {
        UUID accountId = UUID.randomUUID();

        when(transactionRepository.findByAccountId(accountId)).thenReturn(Collections.emptyList());

        BigDecimal balance = accountServices.getAccountBalance(accountId);

        assertEquals(BigDecimal.ZERO, balance);
        verify(transactionRepository, times(1)).findByAccountId(accountId);
    }
}