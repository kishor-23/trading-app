package com.chainsys.dao;

import java.util.List;

import com.chainsys.model.Portfolio;

public interface PortfolioDAO {
    void addPortfolio(Portfolio portfolio);
    void updatePortfolio(Portfolio portfolio);
    void deletePortfolio(int portfolioId);
    Portfolio getPortfolioById(int portfolioId);
    List<Portfolio> getAllPortfolios();
}

