package com.chainsys.model;



public class Portfolio {
    private int portfolioId;
    private int userId;
    private int stockId;
    private int quantity;
    private double total;
    private String symbol;
    public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	private String company;
    public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	private double buyedPrice;

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

    public double getBuyedPrice() {
        return buyedPrice;
    }

    public void setBuyedPrice(double buyedPrice) {
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

