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

    @Override
    public void init() throws ServletException {
        try {
            stockDao = new StockImpl();
        } catch (Exception e) {
            throw new ServletException("Failed to initialize StockDao", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int page = getPageParameter(request);
            int itemsPerPage = getItemsPerPageParameter(request);
            String searchQuery = request.getParameter("searchQuery");
            String filterCategory = request.getParameter("filterCategory");
            String sortField = request.getParameter("sortField");
            boolean sortOrder = getSortOrderParameter(request);

            List<Stock> allStocks = stockDao.selectAllStocks();
            List<Stock> filteredStocks = filterStocks(allStocks, filterCategory);
            List<Stock> searchedStocks = searchStocks(filteredStocks, searchQuery);
            sortStocks(searchedStocks, sortField, sortOrder);

            int totalItems = searchedStocks.size();
            int totalPages = (int) Math.ceil((double) totalItems / itemsPerPage);
            int startItemIndex = (page - 1) * itemsPerPage;
            int endItemIndex = Math.min(startItemIndex + itemsPerPage, totalItems);

            List<Stock> paginatedStocks = searchedStocks.subList(startItemIndex, endItemIndex);

            setRequestAttributes(request, paginatedStocks, totalPages, page, itemsPerPage, filterCategory,
                    searchQuery, sortField, sortOrder);

            RequestDispatcher dispatcher = request.getRequestDispatcher("stock.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            log("Error in processing request", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private int getPageParameter(HttpServletRequest request) {
        String pageParam = request.getParameter("page");
        return pageParam != null ? Integer.parseInt(pageParam) : 1;
    }

    private int getItemsPerPageParameter(HttpServletRequest request) {
        String itemsPerPageParam = request.getParameter("itemsPerPage");
        return itemsPerPageParam != null ? Integer.parseInt(itemsPerPageParam) : 10;
    }

    private boolean getSortOrderParameter(HttpServletRequest request) {
        String sortOrderParam = request.getParameter("sortOrder");
        return sortOrderParam != null ? Boolean.parseBoolean(sortOrderParam) : true;
    }


    private List<Stock> filterStocks(List<Stock> stocks, String filterCategory) {
        if (filterCategory == null || "All".equals(filterCategory)) {
            return stocks;
        }
        List<Stock> filteredStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getCapCategory().equalsIgnoreCase(filterCategory)) {
                filteredStocks.add(stock);
            }
        }
        return filteredStocks;
    }

    private List<Stock> searchStocks(List<Stock> stocks, String searchQuery) {
        if (searchQuery == null || searchQuery.isEmpty()) {
            return stocks;
        }
        String upperCaseSearchQuery = searchQuery.toUpperCase();
        List<Stock> searchedStocks = new ArrayList<>();
        for (Stock stock : stocks) {
            if (stock.getSymbol().toUpperCase().contains(upperCaseSearchQuery)
                    || stock.getCompanyName().toUpperCase().contains(upperCaseSearchQuery)) {
                searchedStocks.add(stock);
            }
        }
        return searchedStocks;
    }

    private void sortStocks(List<Stock> stocks, String sortField, boolean sortOrder) {
        if (sortField == null) {
            return;
        }
        Comparator<Stock> comparator = null;
        switch (sortField) {
            case "symbol":
                comparator = Comparator.comparing(Stock::getSymbol);
                break;
            case "companyName":
                comparator = Comparator.comparing(Stock::getCompanyName);
                break;
            case "currentStockPrice":
                comparator = Comparator.comparing(Stock::getCurrentStockPrice);
                break;
            case "capCategory":
                comparator = Comparator.comparing(Stock::getCapCategory);
                break;
        }
        if (comparator != null) {
            if (!sortOrder) {
                comparator = comparator.reversed();
            }
            Collections.sort(stocks, comparator);
        }
    }

    private void setRequestAttributes(HttpServletRequest request, List<Stock> paginatedStocks, int totalPages,
            int currentPage, int itemsPerPage, String filterCategory, String searchQuery, String sortField,
            boolean sortOrder) {
        request.setAttribute("listStocks", paginatedStocks);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("itemsPerPage", itemsPerPage);
        request.setAttribute("filterCategory", filterCategory != null ? filterCategory : "All");
        request.setAttribute("searchQuery", searchQuery != null ? searchQuery : "");
        request.setAttribute("sortField", sortField != null ? sortField : "symbol");
        request.setAttribute("sortOrder", sortOrder);
    }
}
