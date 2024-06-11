package com.chainsys.impl;

import com.chainsys.dao.StockDAO;
import com.chainsys.model.Stock;
import com.chainsys.util.DbConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockImpl implements StockDAO {
    private static final String CALL_BUY_STOCK_PROCEDURE = "{CALL buyStockProcedure(?, ?, ?, ?, ?)}";
    private static final String CALL_SELL_STOCK_PROCEDURE = "{CALL sellStockProcedure(?, ?, ?, ?, ?, ?)}";
    private Connection con;

    public StockImpl() {
        try {
            this.con = DbConnection.getConnection();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
    }

    @Override
    public List<Stock> selectAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT stock_id, symbol, company_name, current_stock_price, cap_category FROM stocks";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql);
             ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int stockId = rs.getInt("stock_id");
                String symbol = rs.getString("symbol");
                String companyName = rs.getString("company_name");
                double currentStockPrice = rs.getDouble("current_stock_price");
                String capCategory = rs.getString("cap_category");
                stocks.add(new Stock(stockId, symbol, companyName, currentStockPrice, capCategory));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
        return stocks;
    }

    @Override
    public Stock getStockDetailsById(int id) {
        String sql = "SELECT stock_id, symbol, company_name, current_stock_price, cap_category FROM stocks WHERE stock_id = ?";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int stockId = rs.getInt("stock_id");
                    String symbol = rs.getString("symbol");
                    String companyName = rs.getString("company_name");
                    double currentStockPrice = rs.getDouble("current_stock_price");
                    String capCategory = rs.getString("cap_category");
                    return new Stock(stockId, symbol, companyName, currentStockPrice, capCategory);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }
        return null;
    }

    @Override
    public int buyStock(int userId, int stockId, int quantity, double price) {
        int result = 0;

        try (CallableStatement callableStatement = con.prepareCall(CALL_BUY_STOCK_PROCEDURE)) {
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, stockId);
            callableStatement.setInt(3, quantity);
            callableStatement.setDouble(4, price);
            callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);

            callableStatement.execute();
            result = callableStatement.getInt(5);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

        return result;
    }

    @Override
    public int sellStock(int userId, int stockId, int quantity, double price) {
        int result = 0;

        try (CallableStatement callableStatement = con.prepareCall(CALL_SELL_STOCK_PROCEDURE)) {
            callableStatement.setInt(1, userId);
            callableStatement.setInt(2, stockId);
            callableStatement.setInt(3, quantity);
            callableStatement.setDouble(4, price);
            callableStatement.registerOutParameter(5, java.sql.Types.INTEGER);
            callableStatement.registerOutParameter(6, java.sql.Types.DECIMAL); // To get the profit/loss

            callableStatement.execute();
            result = callableStatement.getInt(5);
            double profitLoss = callableStatement.getDouble(6);
            // Handle profitLoss if needed
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception appropriately
        }

        return result;
    }
}
