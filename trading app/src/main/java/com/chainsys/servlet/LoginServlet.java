package com.chainsys.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.dao.NomineeDAO;
import com.chainsys.dao.UserDAO;
import com.chainsys.impl.NomineeImpl;
import com.chainsys.impl.UserImpl;
import com.chainsys.model.Nominee;
import com.chainsys.model.User;


@WebServlet("/LoginServlet")
//@MultipartConfig
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private UserDAO userOperations;

	    public LoginServlet() {
	        super();
	        try {
	            userOperations = new UserImpl();
	        } catch (ClassNotFoundException | SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Database connection failed");
	        }
	    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String email = request.getParameter("email");
	        String password = request.getParameter("password");

	        try {
	            User user = userOperations.getUserByEmailAndPassword(email, password);
	            
	            if (user != null) {
	                HttpSession session = request.getSession();
	                System.out.println(user.getName());
	                session.setAttribute("user", user);
	                System.out.println(user.toString());
	                NomineeDAO nomineeDAO = new NomineeImpl();
	                List<Nominee> listNominees = null;
	                try {
	                    listNominees = nomineeDAO.getAllNomineesByUserId(user.getId());
	                } catch (SQLException e) {
	                    throw new ServletException(e);
	                }
	                
//	                request.setAttribute("listNominees", listNominees);
	                response.sendRedirect("NomineeServlet?action=list");
	            } else {
	            	if(userOperations.checkUserAleardyExists(email)) {
	            		response.sendRedirect("login.jsp?msg=1");
	            	}
	            	else {
	            		response.sendRedirect("login.jsp?msg=2");

	            	}
	                
	            }
	        } catch (SQLException | ClassNotFoundException e) {
	            e.printStackTrace();
	            response.sendRedirect("login.jsp?msg=An error occurred. Please try again later.");
	        }
	}

}
