package com.chainsys.servlet;

import java.io.IOException;
import java.sql.Blob;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.InputStream;

import com.chainsys.dao.UserDAO;
import com.chainsys.impl.UserImpl;
import com.chainsys.model.User;
import com.chainsys.util.PasswordHashing;
import com.chainsys.util.EmailUtility;

@WebServlet("/RegisterServlet")
@MultipartConfig
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userOperations;
    private static final String REGISTER_JSP = "register.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    public RegisterServlet() throws ClassNotFoundException, SQLException {
        super();
            userOperations = new UserImpl();
      
    }
@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String pancardno = request.getParameter("pancardno");
        String phone = request.getParameter("phone");
        String dobString = request.getParameter("dob");
        Date dob = null;

        if (dobString != null && !dobString.isEmpty()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                dob = new Date(sdf.parse(dobString).getTime());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Blob profilePicture = null;
        Part filePart = request.getPart("profilePicture");
        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                profilePicture = new javax.sql.rowset.serial.SerialBlob(inputStream.readAllBytes());
            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }

        double balance = 100.00;

        // Hash the password before storing
        String hashedPassword = PasswordHashing.hashPassword(password);

        User user = new User(username, email, pancardno, phone, dob, hashedPassword, profilePicture, balance);

        try {
            boolean userExists = userOperations.checkUserAlreadyExists(email);
            if (!userExists) {
                userOperations.addUser(user);

                // Send email notification
                String body = "Dear " + username + ",\n\nYour account has been created successfully.\n\nBest Regards,\nTeam";
                EmailUtility.sendEmail(email, body,user.getName());

                response.sendRedirect("login.jsp?registered=true");
            } else {
                request.setAttribute(ERROR_MESSAGE, "Registration failed. User already exists. Please login.");
                request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute(ERROR_MESSAGE, "Registration failed. Please try again.");
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
        } catch (MessagingException e) {
            e.printStackTrace();
            request.setAttribute(ERROR_MESSAGE, "Registration successful, but failed to send confirmation email.");
            request.getRequestDispatcher(REGISTER_JSP).forward(request, response);
        }
    }
}
