<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.chainsys.model.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%@ page import="com.chainsys.impl.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stock Order</title>
</head>
<body>
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,200;0,300;0,500;0,700;0,800;1,400;1,600&display=swap");

        body {
            padding: 0;
            margin: 0;
        }
        .box {
            width: 100%;
            height: 90vh;
            display: flex;
            align-items: center;
            justify-content: center;
            font-family: 'Poppins', sans-serif;
            background-color: #1c38ec;
        }
        .orderContainer {
            display: flex;
            flex-direction: column;
            align-items: center;
            transition: 0.5s all ease-in-out;
        }
        .order {
            animation: bouncingCard 0.6s ease-out infinite alternate;
            background-color: white;
            color: darkslategray;
            border-radius: 12px;
            height: auto;
        }
        .orderShadow {
            animation: bouncingShadow 0.6s ease-out infinite alternate;
            margin-top: 4px;
            width: 95%;
            height: 12px;
            border-radius: 50%;
            background-color: rgba(0, 0, 0, 0.4);
            filter: blur(12px);
        }
        .orderTitle {
            font-size: 1.5rem;
            font-weight: 700;
            padding: 12px 16px 4px;
        }
        hr {
            width: 90%;
            border: 1px solid #efefef;
        }
        .orderDetail {
            font-size: 1.1rem;
            font-weight: 500;
            padding: 4px 16px 12px 16px;
        }
        .orderSubDetail {
            display: flex;
            justify-content: space-between;
            font-size: 1rem;
            padding: 12px 16px;
        }
        .orderSubDetail .code {
            margin-right: 24px;
        }
        .qr-code {
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .orderContainer:hover {
            transform: scale(1.2);
        }
        @keyframes bouncingCard {
            from { transform: translateY(0);}
            to {transform: translateY(-18px);}
        }
        @keyframes bouncingShadow {
            from { transform: translateY(0px);}
            to {transform: translateY(4px);}
        }
    </style>
    <%
        int userId = Integer.parseInt(request.getParameter("userid"));
        int stockId = Integer.parseInt(request.getParameter("stockId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        StockImpl stockoperations = new StockImpl();
        Stock stock = stockoperations.getStockDetailsById(stockId);

        // Get the current date and time
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, hh:mma");
        String currentDateTime = formatter.format(date);
    %>
    <div class="box">
        <a onclick="window.print()">
            <div class="orderContainer">
                <div class="order">
                    <div class="orderTitle"><%= stock.getCompanyName() %></div>
                    <hr>
                    <div class="orderDetail">
                        <div style="width: 300px; text-align: center;"> " You are now a shareholder of <%= stock.getCompanyName() %> "</div>
                        <div>Quantity: <%= quantity %></div>
                        <div class="code">Order Value: <%= price %></div>
                        <div>NSE Order: ID1400000000012294</div>
                    </div>
                    <div class="qr-code">
                        <!-- Add QR code or any other images here if needed -->
                    </div>
                    <p style="font-size: small; text-align: center;">Order placed on <%= currentDateTime %></p>
                    <div class="orderSubDetail">
                        <!-- Additional order details can be added here -->
                    </div>
                    <hr>
                    <div class="orderDetail">
                        <div style="text-align: right;">Total Amount: rs. <%= price * quantity %></div>
                    </div>
                </div>
                <div class="orderShadow"></div>
            </div>
        </a>
    </div>
    <div class="button-container" style="text-align: center; margin: 10px;">
        <p style="text-align: center; font-size:20px;">Congrats!! Your order has been confirmed, visit <a href="home.jsp">chain trade</a> for more details</p>
    </div>
</body>
</html>
