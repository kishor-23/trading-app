package com.chainsys.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.impl.StockImpl;

@WebServlet("/StockTransactionServlet")
public class StockTransactionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String TRANSACTION_TYPE = "transactionType";
    private StockImpl stockDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        stockDAO = new StockImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String transactionType = request.getParameter(TRANSACTION_TYPE); // Get the transaction type
    
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
            throws  IOException {
        String transactionType = request.getParameter(TRANSACTION_TYPE);
     
        int userId = Integer.parseInt(request.getParameter("userid"));
        int stockId = Integer.parseInt(request.getParameter("stockId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));


        // Call the DAO to execute the buy transaction
        int result = stockDAO.buyStock(userId, stockId, quantity, price);

        if (result == 1) {
            // Redirect to success.jsp with URL parameters
            response.sendRedirect("ordersuccess.jsp?userid=" + userId + "&stockId=" + stockId + "&quantity=" + quantity + "&price=" + price+"&transactionType="+ transactionType);
        } else {
        	String error="insufficientBalance";
            response.sendRedirect("fail.jsp?error="+error);
        }
    }
    private void handleSell(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        String transactionType = request.getParameter(TRANSACTION_TYPE);
        int userId = Integer.parseInt(request.getParameter("userid"));
        int stockId = Integer.parseInt(request.getParameter("stockId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        // Call the DAO to execute the sell transaction
        int result = stockDAO.sellStock(userId, stockId, quantity, price);
        if (result == 1) {
            // Redirect to success.jsp with URL parameters
            response.sendRedirect("ordersuccess.jsp?userid=" + userId + "&stockId=" + stockId + "&quantity=" + quantity + "&price=" + price+"&transactionType="+ transactionType);
        } else {
            response.sendRedirect("fail.jsp");
        }
    }

}
