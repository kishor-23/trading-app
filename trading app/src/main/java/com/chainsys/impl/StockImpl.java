package com.chainsys.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.model.Stock;
import com.chainsys.model.User;
import com.chainsys.util.DbConnection;

public class StockImpl {
	 private Connection con;

	    public StockImpl() throws ClassNotFoundException, SQLException {
	        this.con = DbConnection.getConnection();
	    }
	
	 public List<Stock> selectAllStocks() {
	        List<Stock> stocks = new ArrayList<>();
            String sql ="select stock_id,symbol, company_name, current_stock_price, cap_category from stocks ";

	        try (
	             PreparedStatement preparedStatement = con.prepareStatement(sql);) {
	            ResultSet rs = preparedStatement.executeQuery();
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
	        }
	        return stocks;
	    }
	 public Stock getStockDetailsById(int id) {
		  String sql = "select stock_id, symbol, company_name, current_stock_price, cap_category from stocks where stock_id=?";

		  try (PreparedStatement pstmt = con.prepareStatement(sql)) {
		    pstmt.setInt(1, id); // Use setInt for integer values

		    try (ResultSet rs = pstmt.executeQuery()) {
		      if (rs.next()) {
		        int stockId = rs.getInt("stock_id");
		        String symbol = rs.getString("symbol");
		        String companyName = rs.getString("company_name");
		        double currentStockPrice = rs.getDouble("current_stock_price");
		        String capCategory = rs.getString("cap_category");

		        // Create a Stock object using the retrieved data
		        Stock stock = new Stock(stockId, symbol, companyName, currentStockPrice, capCategory);
		        return stock;
		      }
		    }
		  } catch (SQLException e) {
		    // Handle SQL exceptions appropriately (e.g., logging, throwing a custom exception)
		    e.printStackTrace();
		  }

		  return null;
		}
}
