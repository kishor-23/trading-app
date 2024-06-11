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
import com.chainsys.util.PasswordHashing; // Import PasswordHashing class


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
            User user = userOperations.getUserByEmail(email);

            if (user != null && PasswordHashing.checkPassword(password, user.getPassword())) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                NomineeDAO nomineeDAO = new NomineeImpl();
                List<Nominee> listNominees = null;
                try {
                    listNominees = nomineeDAO.getAllNomineesByUserId(user.getId());
                } catch (SQLException e) {
                    throw new ServletException(e);
                }

                response.sendRedirect("NomineeServlet?action=list");
            } else {
                response.sendRedirect("login.jsp?msg=Invalid email or password");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?msg=An error occurred. Please try again later.");
        }
    }

}
