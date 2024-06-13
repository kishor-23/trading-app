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
        String query = "SELECT portfolio_id, user_id, stock_id, quantity, buyed_price FROM portfolio WHERE portfolio_id = ?";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, portfolioId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return mapResultSetToPortfolio(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Portfolio> getPortfoliosByUserId(int userId) {
        List<Portfolio> portfolios = new ArrayList<>();
        String query = "SELECT p.portfolio_id, p.user_id, p.stock_id, s.company_name, s.symbol, p.quantity, p.avg_cost, p.total_cost FROM portfolio p JOIN stocks s ON p.stock_id = s.stock_id WHERE p.user_id = ?;";
        try (PreparedStatement statement = con.prepareStatement(query)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Portfolio portfolio = mapResultSetToPortfolio(resultSet);
                portfolios.add(portfolio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolios;
    }

    private Portfolio mapResultSetToPortfolio(ResultSet resultSet) throws SQLException {
        Portfolio portfolio = new Portfolio();
        portfolio.setPortfolioId(resultSet.getInt("portfolio_id"));
        portfolio.setUserId(resultSet.getInt("user_id"));
        portfolio.setStockId(resultSet.getInt("stock_id"));
        portfolio.setQuantity(resultSet.getInt("quantity"));
        portfolio.setBuyedPrice(resultSet.getDouble("avg_cost"));
        portfolio.setTotal(resultSet.getDouble("total_cost"));
        return portfolio;
    }
}
