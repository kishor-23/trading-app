package com.chainsys.dao;

import java.util.List;

import com.chainsys.model.Portfolio;

public interface PortfolioDAO {
 
    Portfolio getPortfolioById(int portfolioId);
    List<Portfolio> getPortfoliosByUserId(int userId);
}

