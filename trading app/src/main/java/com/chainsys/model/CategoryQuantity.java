package com.chainsys.model;

public class CategoryQuantity {
	 private String capCategory;
	    private int totalQuantity;
	    private int userTotalQuantity;

	    // Getters and setters
	    public String getCapCategory() {
	        return capCategory;
	    }

	    public void setCapCategory(String capCategory) {
	        this.capCategory = capCategory;
	    }

	    public int getTotalQuantity() {
	        return totalQuantity;
	    }

	    public void setTotalQuantity(int totalQuantity) {
	        this.totalQuantity = totalQuantity;
	    }

	    public int getUserTotalQuantity() {
	        return userTotalQuantity;
	    }

	    public void setUserTotalQuantity(int userTotalQuantity) {
	        this.userTotalQuantity = userTotalQuantity;
	    }
}
