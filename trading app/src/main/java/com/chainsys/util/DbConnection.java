package com.chainsys.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    
    // Private constructor to prevent instantiation
    private DbConnection() {
        throw new UnsupportedOperationException("Utility class");
    }
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/trading_db_1", "root", System.getenv("MYSQL_PASSWORD"));
    }
}
