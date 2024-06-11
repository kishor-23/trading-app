package com.chainsys.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chainsys.impl.StockImpl;
import com.chainsys.model.Stock;
@WebServlet("/StockServlet")
public class ViewStockServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private StockImpl stockDao;

    public void init() {
        stockDao = new StockImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve parameters for pagination, filtering, searching, and sorting
        int page = Integer.parseInt(request.getParameter("page") != null ? request.getParameter("page") : "1");
        int itemsPerPage = Integer.parseInt(request.getParameter("itemsPerPage") != null ? request.getParameter("itemsPerPage") : "10");
        String searchQuery = request.getParameter("searchQuery") != null ? request.getParameter("searchQuery") : "";
        String filterCategory = request.getParameter("filterCategory") != null ? request.getParameter("filterCategory") : "All";
        String sortField = request.getParameter("sortField") != null ? request.getParameter("sortField") : "symbol";
        boolean sortOrder = Boolean.parseBoolean(request.getParameter("sortOrder") != null ? request.getParameter("sortOrder") : "true");

 
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

        // Sort stocks based on the specified field and order
        Collections.sort(searchedStocks, new Comparator<Stock>() {
            @Override
            public int compare(Stock s1, Stock s2) {
                int comparisonResult = 0;
                switch (sortField) {
                    case "symbol":
                        comparisonResult = s1.getSymbol().compareTo(s2.getSymbol());
                        break;
                    case "companyName":
                        comparisonResult = s1.getCompanyName().compareTo(s2.getCompanyName());
                        break;
                    case "currentStockPrice":
                        comparisonResult = Double.compare(s1.getCurrentStockPrice(), s2.getCurrentStockPrice());
                        break;
                    case "capCategory":
                        comparisonResult = s1.getCapCategory().compareTo(s2.getCapCategory());
                        break;
                }
                return sortOrder ? comparisonResult : -comparisonResult;
            }
        });

        // Pagination
        int totalItems = searchedStocks.size();
        int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
        int startItemIndex = (page - 1) * itemsPerPage;
        int endItemIndex = Math.min(startItemIndex + itemsPerPage, totalItems);

        List<Stock> paginatedStocks = searchedStocks.subList(startItemIndex, endItemIndex);

        // Log the sorted and paginated list for debugging
//        paginatedStocks.forEach(stock -> System.out.println(stock.getSymbol() + " - " + stock.getCurrentStockPrice()));

        // Set attributes for JSP
        request.setAttribute("listStocks", paginatedStocks);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", page);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("filterCategory", filterCategory);
        request.setAttribute("searchQuery", searchQuery);
        request.setAttribute("sortField", sortField);
        request.setAttribute("sortOrder", sortOrder);

        // Forward to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher("stock.jsp");
        dispatcher.forward(request, response);
    }
}
