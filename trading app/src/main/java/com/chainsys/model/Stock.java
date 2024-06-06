package com.chainsys.model;



public class Stock {
 private int stockId;
 private String symbol;
 private String companyName;
 private double currentStockPrice;
 private String capCategory;
public int getStockId() {
	return stockId;
}
public void setStockId(int stockId) {
	this.stockId = stockId;
}
public String getSymbol() {
	return symbol;
}
public void setSymbol(String symbol) {
	this.symbol = symbol;
}
public String getCompanyName() {
	return companyName;
}
public void setCompanyName(String companyName) {
	this.companyName = companyName;
}
public double getCurrentStockPrice() {
	return currentStockPrice;
}
public void setCurrentStockPrice(double currentStockPrice) {
	this.currentStockPrice = currentStockPrice;
}
public String getCapCategory() {
	return capCategory;
}
public void setCapCategory(String capCategory) {
	this.capCategory = capCategory;
}
public Stock(int stockId, String symbol, String companyName, double currentStockPrice, String capCategory) {

	this.stockId = stockId;
	this.symbol = symbol;
	this.companyName = companyName;
	this.currentStockPrice = currentStockPrice;
	this.capCategory = capCategory;
}


 // Getters and Setters
 
}

