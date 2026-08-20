package com.example.exam.repositories;

import com.example.exam.configurations.DatabaseConnection;
import com.example.exam.models.Account;
import com.example.exam.models.AccountType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AccountRepository {

    private final DatabaseConnection databaseConnection;

    public Optional<Account> findById(UUID id) {
        String sql = "SELECT id, account_type FROM account WHERE id = ?";
        
        try (Connection conn = databaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setObject(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Account account = Account.builder()
                            .id((UUID) rs.getObject("id"))
                            .accountType(AccountType.valueOf(rs.getString("account_type")))
                            .build();
                    return Optional.of(account);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }
}