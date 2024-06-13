package com.chainsys.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {
    private int transactionId;
    private int userId;
    private int stockId;
    private int shares;
    private BigDecimal price;
    private String transactionType;
    private Timestamp timestamp;
    private String stockSymbol; 
    private String companyName; 

    // No-argument constructor
    public Transaction() {
    }

    // Parameterized constructor
    public Transaction(int transactionId, int userId, int stockId, int shares, BigDecimal price, String transactionType, Timestamp timestamp, String stockSymbol, String companyName) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.stockId = stockId;
        this.shares = shares;
        this.price = price;
        this.transactionType = transactionType;
        this.timestamp = timestamp;
        this.stockSymbol = stockSymbol;
        this.companyName = companyName;
    }

    // Getters and Setters for all fields
    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getStockId() {
        return stockId;
    }

    public void setStockId(int stockId) {
        this.stockId = stockId;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
