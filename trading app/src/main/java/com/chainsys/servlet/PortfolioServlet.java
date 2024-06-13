package com.chainsys.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.dao.PortfolioDAO;
import com.chainsys.impl.PortfolioImpl;
import com.chainsys.model.Portfolio;

@WebServlet("/portfolio")
public class PortfolioServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
	private PortfolioDAO portfolioDAO;

    @Override
    public void init() throws ServletException {
        try {
			portfolioDAO = new PortfolioImpl();
		} catch (ClassNotFoundException | SQLException e) {
		
			e.printStackTrace();
		}
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "view":
                viewPortfolio(request, response);
                break;
            case "list":
            	
            default:
              
                break;
        }
    }


        private void viewPortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            Portfolio portfolio = portfolioDAO.getPortfolioById(portfolioId);
            request.setAttribute("portfolio", portfolio);
            request.getRequestDispatcher("/portfolio-view.jsp").forward(request, response);
        }

    
    }