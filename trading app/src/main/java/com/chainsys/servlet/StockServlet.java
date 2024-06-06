package com.chainsys.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.impl.StockImpl;
import com.chainsys.model.Stock;

@WebServlet("/StockServlet")
public class StockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StockImpl stockDao;

    public void init() {
        try {
            stockDao = new StockImpl();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters for pagination, filtering, and searching
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage") != null ? request.getParameter("itemsPerPage") : "10");
        String searchQuery = request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "";
        String filterCategory = request.getParameter("filterCategory") != null ? request.getParameter("filterCategory") : "All";

        // Retrieve all stocks
        List<Stock> allStocks = stockDao.selectAllStocks();

        // Filter stocks based on category
        List<Stock> filteredStocks = new ArrayList<>();
        for (Stock stock : allStocks) {
            if (filterCategory.equals("All") || stock.getCapCategory().equalsIgnoreCase(filterCategory)) {
                filteredStocks.add(stock);
            }
        }

        // Search stocks based on search query
        List<Stock> searchedStocks = new ArrayList<>();
        for (Stock stock : filteredStocks) {
            if (stock.getSymbol().toUpperCase().contains(searchQuery.toUpperCase()) ||
                stock.getCompanyName().toUpperCase().contains(searchQuery.toUpperCase())) {
                searchedStocks.add(stock);
            }
        }

        // Pagination
        int totalItems = searchedStocks.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        int startItemIndex = (page - 1) * itemsPerPage;
        int endItemIndex = Math.min(startItemIndex + itemsPerPage, totalItems);

        List<Stock> paginatedStocks = searchedStocks.subList(startItemIndex, endItemIndex);

        // Set attributes for JSP
        request.setAttribute("listStocks", paginatedStocks);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("filterCategory", filterCategory);

        // Forward to JSP
        request.getRequestDispatcher("stock.jsp").forward(request, response);
    }
}
