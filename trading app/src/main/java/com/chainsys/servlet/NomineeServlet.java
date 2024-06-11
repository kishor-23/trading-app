package com.chainsys.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.chainsys.dao.UserDAO;
import com.chainsys.impl.NomineeImpl;
import com.chainsys.impl.UserImpl;
import com.chainsys.model.Nominee;
import com.chainsys.model.User;

@WebServlet("/NomineeServlet")
public class NomineeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private NomineeImpl nomineeImpl;
    private UserDAO userOperations;
    public void init() {
        try {
        	 userOperations = new UserImpl();
            nomineeImpl = new NomineeImpl();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "add":
                    addNominee(request, response);
                    break;
                case "update":
                    updateNominee(request, response);
                    break;
                case "delete":
                    deleteNominee(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        try {
            switch (action) {
                case "list":
                    listNominees(request, response);
                    break;
                case "get": // New case to get nominee details by ID
//                    getNomineeById(request, response);
                    break;
                default:
                    break;
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }

    private void listNominees(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException {

    	 HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if(user==null) {
        	  response.sendRedirect("login.jsp");
              return;
        }
     	 User updatedUser = userOperations.getUserByEmail(user.getEmail());
      	
        List<Nominee> listNominees = nomineeImpl.getAllNomineesByUserId(user.getId());
        request.getSession().setAttribute("user", updatedUser);
        request.setAttribute("listNominees", listNominees);
        RequestDispatcher dispatcher = request.getRequestDispatcher("profile.jsp");
        dispatcher.forward(request, response);
    }



    private void addNominee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException {
        String nomineeName = request.getParameter("name");
        String relationship = request.getParameter("relationship");
        int userId = Integer.parseInt(request.getParameter("userId"));
        String phone = request.getParameter("phone");

        Nominee nominee = new Nominee();
        nominee.setNomineeName(nomineeName);
        nominee.setRelationship(relationship);
        nominee.setUserId(userId);
        nominee.setPhoneno(phone);

        nomineeImpl.addNominee(nominee);
        response.sendRedirect("NomineeServlet?action=list");
    }

    private void updateNominee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException {
        int nomineeId = Integer.parseInt(request.getParameter("nomineeId"));
        String nomineeName = request.getParameter("nomineeName");
        String relationship = request.getParameter("relationship");
        String phone = request.getParameter("phone");
        int userId = Integer.parseInt(request.getParameter("userId"));
        Nominee nominee = new Nominee();
        nominee.setNomineeId(nomineeId);
        nominee.setNomineeName(nomineeName);
        nominee.setRelationship(relationship);
        nominee.setPhoneno(phone);
        nominee.setUserId(userId);
        nomineeImpl.updateNominee(nominee);
      
        response.sendRedirect("NomineeServlet?action=list");
    }

    private void deleteNominee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException, ClassNotFoundException {
        int nomineeId = Integer.parseInt(request.getParameter("nomineeId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        nomineeImpl.deleteNominee(nomineeId);
        response.sendRedirect("NomineeServlet?action=list");
    }
}
