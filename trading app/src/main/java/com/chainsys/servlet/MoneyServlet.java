package com.chainsys.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.impl.UserImpl;
import com.chainsys.model.User;


@WebServlet("/MoneyServlet")
public class MoneyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserImpl userOperations;

    public MoneyServlet() throws ClassNotFoundException, SQLException {
        super();
         userOperations = new UserImpl();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 int userId = Integer.parseInt(request.getParameter("userId"));
	        double amount = Double.parseDouble(request.getParameter("amount"));

	        try {
	        	userOperations.addMoneyToUser(userId, amount);
	        	 response.sendRedirect("NomineeServlet?action=list");
	        } catch (SQLException e) {
	            e.printStackTrace();
	            response.sendRedirect("error.jsp"); // Redirect to an error page
	        }
	}

}
