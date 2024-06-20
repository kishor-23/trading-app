package com.chainsys.impl;

import com.chainsys.dao.TransactionDAO;
import com.chainsys.model.Transaction;
import com.chainsys.util.DbConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransactionImpl implements TransactionDAO {

    private static final String GET_TRANSACTIONS_BY_USER_ID_QUERY =
            "SELECT t.transaction_id, t.user_id, t.stock_id,t.profit_loss, t.shares, t.price, t.transaction_type, t.timestamp, s.symbol, s.company_name " +
            "FROM transactions t JOIN stocks s ON t.stock_id = s.stock_id " +
            "WHERE t.user_id = ?";
    


    private static final String GET_LAST_FIVE_TRANSACTIONS_BY_USER_ID_QUERY =
            "SELECT t.transaction_id, t.user_id, t.stock_id, t.shares, t.price, t.transaction_type, t.timestamp, s.symbol, s.company_name " +
            "FROM transactions t JOIN stocks s ON t.stock_id = s.stock_id " +
            "WHERE t.user_id = ? " +
            "ORDER BY t.timestamp DESC LIMIT 5";
    private Connection con;

    public TransactionImpl() throws ClassNotFoundException, SQLException {
        this.con = DbConnection.getConnection();
    }
    @Override
    public List<Transaction> getTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try (
             PreparedStatement preparedStatement = con.prepareStatement(GET_TRANSACTIONS_BY_USER_ID_QUERY)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(resultSet.getInt("transaction_id"));
                    transaction.setUserId(resultSet.getInt("user_id"));
                    transaction.setStockId(resultSet.getInt("stock_id"));
                    transaction.setShares(resultSet.getInt("shares"));
                    transaction.setPrice(resultSet.getBigDecimal("price"));
                    transaction.setTransactionType(resultSet.getString("transaction_type"));
                    transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                    transaction.setStockSymbol(resultSet.getString("symbol"));
                    transaction.setCompanyName(resultSet.getString("company_name"));
                    transaction.setProfitOrLoss(resultSet.getDouble("profit_loss"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }


    
    public List<Transaction> getLastFiveTransactionsByUserId(int userId) {
        List<Transaction> transactions = new ArrayList<>();
        try (PreparedStatement preparedStatement = con.prepareStatement(GET_LAST_FIVE_TRANSACTIONS_BY_USER_ID_QUERY)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setTransactionId(resultSet.getInt("transaction_id"));
                    transaction.setUserId(resultSet.getInt("user_id"));
                    transaction.setStockId(resultSet.getInt("stock_id"));
                    transaction.setShares(resultSet.getInt("shares"));
                    transaction.setPrice(resultSet.getBigDecimal("price"));
                    transaction.setTransactionType(resultSet.getString("transaction_type"));
                    transaction.setTimestamp(resultSet.getTimestamp("timestamp"));
                    transaction.setStockSymbol(resultSet.getString("symbol"));
                    transaction.setCompanyName(resultSet.getString("company_name"));
                    transactions.add(transaction);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
