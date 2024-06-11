package com.chainsys.dao;

import java.util.List;

import com.chainsys.model.Transaction;

public interface TransactionDAO {
 
    List<Transaction> getTransactionsByUserId(int userId);
    List<Transaction> getAllTransactions();
    List<Transaction> getLastFiveTransactionsByUserId(int userId);
}
