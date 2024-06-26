package com.chainsys.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/StockDetailServlet")
public class StockDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public StockDetailServlet() {
        super();
    }

@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	
	}

@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		  String symbol = request.getParameter("symbol");
		  
		  request.setAttribute("symbol", symbol);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("viewstocks.jsp");
	        dispatcher.forward(request, response);
	}

}
