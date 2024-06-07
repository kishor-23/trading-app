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
    public void addPortfolio(Portfolio portfolio) {
        String query = "INSERT INTO portfolio (user_id, stock_id, quantity, buyed_price) VALUES (?, ?, ?, ?)";
        try (
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, portfolio.getUserId());
            statement.setInt(2, portfolio.getStockId());
            statement.setInt(3, portfolio.getQuantity());
            statement.setBigDecimal(4, portfolio.getBuyedPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updatePortfolio(Portfolio portfolio) {
        String query = "UPDATE portfolio SET user_id = ?, stock_id = ?, quantity = ?, buyed_price = ? WHERE portfolio_id = ?";
        try (
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, portfolio.getUserId());
            statement.setInt(2, portfolio.getStockId());
            statement.setInt(3, portfolio.getQuantity());
            statement.setBigDecimal(4, portfolio.getBuyedPrice());
            statement.setInt(5, portfolio.getPortfolioId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePortfolio(int portfolioId) {
        String query = "DELETE FROM portfolio WHERE portfolio_id = ?";
        try (
             PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, portfolioId);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public Portfolio getPortfolioById(int portfolioId) {
        String query = "SELECT * FROM portfolio WHERE portfolio_id = ?";
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
        String query = "SELECT * FROM portfolio";
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


}

