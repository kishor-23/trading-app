package com.chainsys.servlet;

import com.chainsys.dao.TransactionDAO;
import com.chainsys.impl.TransactionImpl;
import com.chainsys.model.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	private  TransactionDAO transactionDAO;

    public TransactionServlet() throws ClassNotFoundException, SQLException {
        this.transactionDAO = new TransactionImpl();
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userIdParam = request.getParameter("userId");
        List<Transaction> transactions=null;

        if (userIdParam != null) {
            int userId = Integer.parseInt(userIdParam);
            transactions = transactionDAO.getTransactionsByUserId(userId);
        } 

        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("transactions.jsp").forward(request, response);
    }
}
