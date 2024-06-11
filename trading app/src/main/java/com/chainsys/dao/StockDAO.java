package com.chainsys.dao;

import com.chainsys.model.Stock;
import java.sql.SQLException;
import java.util.List;

public interface StockDAO {
    List<Stock> selectAllStocks();
    Stock getStockDetailsById(int id);
    int buyStock(int userId, int stockId, int quantity, double price) throws SQLException;
    int sellStock(int userId, int stockId, int quantity, double price) throws SQLException;
}
