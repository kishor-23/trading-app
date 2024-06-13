package com.chainsys.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.dao.UserDAO;
import com.chainsys.impl.UserImpl;
import com.chainsys.model.User;
import com.chainsys.util.PasswordHashing;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDAO userOperations;

    public LoginServlet() throws ServletException {
        super();
        try {
            userOperations = new UserImpl();
        } catch (ClassNotFoundException | SQLException e) {
            log("Database connection failed", e);
            throw new ServletException("Database connection failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            User user = userOperations.getUserByEmail(email);
            if (user != null && PasswordHashing.checkPassword(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

             

                response.sendRedirect("NomineeServlet?action=list");
            } else {
                response.sendRedirect("login.jsp?msg=Invalid email or password");
            }
        } catch (SQLException | ClassNotFoundException e) {
            log("Error during login process", e);
            response.sendRedirect("login.jsp?msg=An error occurred. Please try again later.");
        }
    }
}
