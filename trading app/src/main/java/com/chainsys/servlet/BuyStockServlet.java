package com.chainsys.servlet;
import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.impl.StockImpl;

@WebServlet("/BuyStockServlet")
public class BuyStockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private  StockImpl stockDAO;
@Override
    public void init() {
        try {
			stockDAO = new StockImpl();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userid"));
        int stockId = Integer.parseInt(request.getParameter("stockId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));

        // Log input values for debugging
        System.out.println("User ID: " + userId);
        System.out.println("Stock ID: " + stockId);
        System.out.println("Quantity: " + quantity);
        System.out.println("Price: " + price);

        // Call the DAO to execute the stored procedure
        int result = stockDAO.buyStock(userId, stockId, quantity, price);

        // Log the result for debugging
        System.out.println("Result: " + result);

        if (result == 1) {
            // Redirect to success.jsp with URL parameters
            response.sendRedirect("ordersuccess.jsp?userid=" + userId + "&stockId=" + stockId + "&quantity=" + quantity + "&price=" + price);
        } else {
            response.sendRedirect("insufficientBalance.jsp");
        }
    }


}

