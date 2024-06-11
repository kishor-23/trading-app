package com.chainsys.impl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.dao.PortfolioDAO;
import com.chainsys.model.Portfolio;
import com.chainsys.util.DbConnection;

public class PortfolioImpl implements PortfolioDAO {
	private Connection con;

    public PortfolioImpl() throws ClassNotFoundException, SQLException {
        this.con = DbConnection.getConnection();
    }




    @Override
    public Portfolio getPortfolioById(int portfolioId) {
        String query = "SELECT  portfolio_id , user_id ,stock_id ,  quantity ,buyed_price  FROM portfolio WHERE portfolio_id = ?";
        try (
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, portfolioId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Portfolio portfolio = new Portfolio();
                portfolio.setPortfolioId(resultSet.getInt("portfolio_id"));
                portfolio.setUserId(resultSet.getInt("user_id"));
                portfolio.setStockId(resultSet.getInt("stock_id"));
                portfolio.setQuantity(resultSet.getInt("quantity"));
                portfolio.setBuyedPrice(resultSet.getBigDecimal("buyed_price"));
                return portfolio;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Portfolio> getAllPortfolios() {
        List<Portfolio> portfolios = new ArrayList<>();
        String query = "SELECT  portfolio_id ,user_id , stock_id ,quantity ,buyed_price  FROM portfolio";
        try (
             PreparedStatement statement = con.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Portfolio portfolio = new Portfolio();
                portfolio.setPortfolioId(resultSet.getInt("portfolio_id"));
                portfolio.setUserId(resultSet.getInt("user_id"));
                portfolio.setStockId(resultSet.getInt("stock_id"));
                portfolio.setQuantity(resultSet.getInt("quantity"));
                portfolio.setBuyedPrice(resultSet.getBigDecimal("buyed_price"));
                portfolios.add(portfolio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolios;
    }
    public List<Portfolio> getPortfoliosByUserId(int userId) {
        List<Portfolio> portfolios = new ArrayList<>();
        String query = "SELECT portfolio_id, user_id, stock_id, quantity,avg_cost,total_cost FROM portfolio WHERE user_id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Portfolio portfolio = new Portfolio();
                portfolio.setPortfolioId(resultSet.getInt("portfolio_id"));
                portfolio.setUserId(resultSet.getInt("user_id"));
                portfolio.setStockId(resultSet.getInt("stock_id"));
                portfolio.setQuantity(resultSet.getInt("quantity"));
                portfolio.setBuyedPrice(resultSet.getBigDecimal("avg_cost"));
                portfolio.setTotal(resultSet.getBigDecimal("total_cost"));
                portfolios.add(portfolio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolios;
    }


}

