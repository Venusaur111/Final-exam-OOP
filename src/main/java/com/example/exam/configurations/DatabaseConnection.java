package com.example.exam.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
public class DatabaseConnection {

    @Value("${DB_HOST:localhost}")
    private String host;

    @Value("${DB_PORT:5432}")
    private String port;

    @Value("${DB_NAME:wallet_tracker}")
    private String dbName;

    @Value("${DB_USER:postgres}")
    private String user;

    @Value("${DB_PASSWORD:}")
    private String password;

    public Connection getConnection() throws SQLException {
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName);
        return DriverManager.getConnection(url, user, password);
    }
}