package com.chainsys.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

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
    private PortfolioDAO portfolioDAO;

    @Override
    public void init() throws ServletException {
        try {
			portfolioDAO = new PortfolioImpl();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
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
            case "delete":
                deletePortfolio(request, response);
                break;
            case "list":
            default:
                listPortfolios(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            action = "list";
        }

        switch (action) {
            case "add":
                addPortfolio(request, response);
                break;
            case "update":
                updatePortfolio(request, response);
                break;
            case "list":
            default:
                listPortfolios(request, response);
                break;
        }
    }
        private void addPortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int userId = Integer.parseInt(request.getParameter("userId"));
            int stockId = Integer.parseInt(request.getParameter("stockId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            BigDecimal buyedPrice = new BigDecimal(request.getParameter("buyedPrice"));

            Portfolio portfolio = new Portfolio();
            portfolio.setUserId(userId);
            portfolio.setStockId(stockId);
            portfolio.setQuantity(quantity);
            portfolio.setBuyedPrice(buyedPrice);

            portfolioDAO.addPortfolio(portfolio);
            response.sendRedirect("portfolio?action=list");
        }

        private void updatePortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            int userId = Integer.parseInt(request.getParameter("userId"));
            int stockId = Integer.parseInt(request.getParameter("stockId"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            BigDecimal buyedPrice = new BigDecimal(request.getParameter("buyedPrice"));

            Portfolio portfolio = new Portfolio();
            portfolio.setPortfolioId(portfolioId);
            portfolio.setUserId(userId);
            portfolio.setStockId(stockId);
            portfolio.setQuantity(quantity);
            portfolio.setBuyedPrice(buyedPrice);

            portfolioDAO.updatePortfolio(portfolio);
            response.sendRedirect("portfolio?action=list");
        }

        private void deletePortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            portfolioDAO.deletePortfolio(portfolioId);
            response.sendRedirect("portfolio?action=list");
        }

        private void viewPortfolio(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int portfolioId = Integer.parseInt(request.getParameter("portfolioId"));
            Portfolio portfolio = portfolioDAO.getPortfolioById(portfolioId);
            request.setAttribute("portfolio", portfolio);
            request.getRequestDispatcher("/portfolio-view.jsp").forward(request, response);
        }

        private void listPortfolios(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            List<Portfolio> portfolios = portfolioDAO.getAllPortfolios();
            request.setAttribute("portfolios", portfolios);
            request.getRequestDispatcher("/portfolio-list.jsp").forward(request, response);
        }
    }