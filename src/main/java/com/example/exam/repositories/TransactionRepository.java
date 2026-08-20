package com.example.exam.repositories;

import com.example.exam.configurations.DatabaseConnection;
import com.example.exam.models.Transaction;
import com.example.exam.models.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class TransactionRepository {

    private final DatabaseConnection databaseConnection;

    public List<Transaction> findByTransactionType(TransactionType transactionType) {
        String sql = "SELECT id, created_at, transaction_type, amount, account_id, reason FROM transaction WHERE transaction_type = ?::TRANSACTION_TYPE";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, transactionType.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public List<Transaction> findByAccountId(UUID accountId) {
        String sql = "SELECT id, created_at, transaction_type, amount, account_id, reason FROM transaction WHERE account_id = ?";
        List<Transaction> transactions = new ArrayList<>();
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setObject(1, accountId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    transactions.add(mapResultSetToTransaction(rs));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return transactions;
    }

    public Transaction save(Transaction transaction) {
        String sql = "INSERT INTO transaction (transaction_type, amount, account_id, reason) VALUES (?::TRANSACTION_TYPE, ?, ?, ?) RETURNING id, created_at";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, transaction.getTransactionType().name());
            ps.setBigDecimal(2, transaction.getAmount());
            ps.setObject(3, transaction.getAccountId());
            ps.setString(4, transaction.getReason());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    transaction.setId((UUID) rs.getObject("id"));
                    Timestamp timestamp = rs.getTimestamp("created_at");
                    if (timestamp != null) {
                        transaction.setCreatedAt(timestamp.toLocalDateTime());
                    }
                }
            }
            return transaction;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Transaction mapResultSetToTransaction(ResultSet rs) throws SQLException {
        Timestamp timestamp = rs.getTimestamp("created_at");
        LocalDateTime createdAt = (timestamp != null) ? timestamp.toLocalDateTime() : null;

        return Transaction.builder()
                .id((UUID) rs.getObject("id"))
                .createdAt(createdAt)
                .transactionType(TransactionType.valueOf(rs.getString("transaction_type")))
                .amount(rs.getBigDecimal("amount"))
                .accountId((UUID) rs.getObject("account_id"))
                .reason(rs.getString("reason"))
                .build();
    }
}