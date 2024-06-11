package com.chainsys.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.impl.StockImpl;

@WebServlet("/StockTransactionServlet")
public class StockTransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StockImpl stockDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        stockDAO = new StockImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionType = request.getParameter("transactionType"); // Get the transaction type
        System.out.println(transactionType);
        if ("buy".equalsIgnoreCase(transactionType)) {
            handleBuy(request, response);
        } else if ("sell".equalsIgnoreCase(transactionType)) {
            handleSell(request, response);
        } else {
            // Invalid transaction type
            response.sendRedirect("invalidTransaction.jsp");
        }
    }

    private void handleBuy(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionType = request.getParameter("transactionType");
        System.out.println(transactionType);
        int userId = Integer.parseInt(request.getParameter("userid"));
        int stockId = Integer.parseInt(request.getParameter("stockId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));

        // Log input values for debugging
        System.out.println("User ID: " + userId);
        System.out.println("Stock ID: " + stockId);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);

        // Call the DAO to execute the buy transaction
        int result = stockDAO.buyStock(userId, stockId, quantity, price);

        // Log the result for debugging
        System.out.println("Result: " + result);

        if (result == 1) {
            // Redirect to success.jsp with URL parameters
            response.sendRedirect("ordersuccess.jsp?userid=" + userId + "&stockId=" + stockId + "&quantity=" + quantity + "&price=" + price+"&transactionType="+ transactionType);
        } else {
        	String error="insufficientBalance";
            response.sendRedirect("fail.jsp?error="+error);
        }
    }

    private void handleSell(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionType = request.getParameter("transactionType");
        int userId = Integer.parseInt(request.getParameter("userid"));
        int stockId = Integer.parseInt(request.getParameter("stockId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));

        // Log input values for debugging
        System.out.println("User ID: " + userId);
        System.out.println("Stock ID: " + stockId);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);

        // Call the DAO to execute the sell transaction
        int result = stockDAO.sellStock(userId, stockId, quantity, price);

        // Log the result for debugging
        System.out.println("Result: " + result);

        if (result == 1) {
            // Redirect to success.jsp with URL parameters
            response.sendRedirect("ordersuccess.jsp?userid=" + userId + "&stockId=" + stockId + "&quantity=" + quantity + "&price=" + price+"&transactionType="+ transactionType);
        } else {
            response.sendRedirect("fail.jsp");
        }
    }

}
