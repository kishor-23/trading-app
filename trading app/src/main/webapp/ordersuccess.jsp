<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stock Order </title>
</head>
<body>
    <style>
        @import url("https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,200;0,300;0,500;0,700;0,800;1,400;1,600&display=swap");

        body{
   
            padding: 0;
    margin: 0;
}
.box{
   
    width: 100%;
    height: 90vh;
    display: flex;
    align-items: center;
    justify-content: center;
    font-family: sans-serif;
    font-size: 12px;
    font-family: 'Poppins', sans-serif;
    background-color: #1c38ec;
}

/* Main Ticket Style */
.orderContainer{
    display: flex;
    flex-direction: column;
    align-items: center;
    transition: 0.5s all ease-in-out;
}
.order{
    animation: bouncingCard 0.6s ease-out infinite alternate;
    background-color: white;
    color: darkslategray;
    border-radius: 12px;
    height: auto;
}
.orderShadow{
    animation: bouncingShadow 0.6s ease-out infinite alternate;
    margin-top: 4px;
    width: 95%;
    height: 12px;
    border-radius: 50%;
    background-color: rgba(0, 0, 0, 0.4);
    filter: blur(12px);
}

/* Ticket Content */
.orderTitle{
    font-size: 1.5rem;
    font-weight: 700;
    padding: 12px 16px 4px;
}
hr{
    width: 90%;
    border: 1px solid #efefef;
}
.orderDetail{
    font-size: 1.1rem;
    font-weight: 500;
    padding: 4px 16px 12px 16px;
}
.orderSubDetail{
    display: flex;
    justify-content: space-between;
    font-size: 1rem;
    padding: 12px 16px;
}
.orderSubDetail .code{
    margin-right: 24px;
}



.qr-code {
    display: flex;
    justify-content: center;
    align-items: center;
}
.orderContainer:hover{
    transform: scale(1.2);
}
        


/* Ticket Bouncing Animation  */
 /* @keyframes bouncingCard {
    from { transform: translateY(0);}
    to {transform: translateY(-18px);}
}
@keyframes bouncingShadow {
    from { transform: translateY(0px);}
    to {transform: translateY(4px);}
} */

    </style>
    <% 
    int userId = (Integer) request.getAttribute("userid");
    int stockId = (Integer) request.getAttribute("stockId");
    int quantity = (Integer) request.getAttribute("quantity");
    double price = (Double) request.getAttribute("price");
%>

<p>User ID: <%= userId %></p>
<p>Stock ID: <%= stockId %></p>
<p>Quantity: <%= quantity %></p>
<p>Price: <%= price %></p>
    <div class="box">
 <a onclick="window.print()">
    <div class="orderContainer" >
        <div class="order">
          <div class="orderTitle">Apple Inc..</div>
          <hr>
          <div class="orderDetail">
            <div style="width: 300px; text-align: center;">You are now a shareholder of Apple  </div>
        <!-- <p style="font-size: small; width: 300px;"> You have bought 5 shares at the rate of 200 per share.</p>     -->
            <div>Quantity: <%= quantity %></div>
            <!-- <div>Order Time:   19:20</div> -->
            <div class="code">Order Value: <%= price %></div>
             <div>NSE Order : ID1400000000012294</div>
             
          </div>
         
          
          <div class="qr-code">
            <!-- <img src="https://example.com/buy-stamp.png" width="70" height="60"> -->
            <!-- <img src="https://svgshare.com/i/t8x.svg" width="70" height="60"> -->
        </div>
        <p style="font-size: small; text-align: center;"> Order placed on 31 May 2024, 09:44PM</p>

            <div class="orderSubDetail">
                  <!-- <div class="code">Order Type: Market</div> -->
            </div>
          <div class="orderDetail">
            <div style="text-align: right;">Total Amount :  rs. 1000</div>
          </div>
        </div>
        <div class="orderShadow"></div>
      </div>
    </div>
</a>
    <!-- <div style="display: flex; justify-content: center; align-items: center;"></div> -->
    <!-- <p style="text-align: center; font-size: 20px;">Booking confirm Thank you, <a href="index.html">click here</a></p> -->
    <div class="button-container" style="text-align: center; margin: 10px;">
        <!-- <button onclick="window.print()"> Print the ticket</button> -->
   
      <p style="text-align: center; font-size:20px ;">Congrats !!  your order has been confirmed,visit <a href="index.html">chain trade</a> for more details</p>
    </div>
    
    </body>
</html>