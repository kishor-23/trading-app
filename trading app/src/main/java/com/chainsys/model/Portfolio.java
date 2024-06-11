package com.chainsys.model;

import java.math.BigDecimal;

public class Portfolio {
    private int portfolioId;
    private int userId;
    private int stockId;
    private int quantity;
    private BigDecimal total;
    public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	private BigDecimal buyedPrice;

    // Getters and Setters
    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBuyedPrice() {
        return buyedPrice;
    }

    public void setBuyedPrice(BigDecimal buyedPrice) {
        this.buyedPrice = buyedPrice;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "portfolioId=" + portfolioId +
                ", userId=" + userId +
                ", stockId=" + stockId +
                ", quantity=" + quantity +
                ", buyedPrice=" + buyedPrice +
                '}';
    }
}

