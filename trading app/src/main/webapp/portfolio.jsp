<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@ page import="java.util.List" %>
    <%@ page import="com.chainsys.model.*" %>
    <%@ page import="com.chainsys.impl.*" %>
    <%@ page import="com.chainsys.dao.*" %>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portfolio</title>
          <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
       <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

</head>
    <style>
        html,
body,
.intro {
  height: 100%;
  overflow: hidden;
}

.gradient-custom-1 {
  /* fallback for old browsers */
  background: #4758eb


}

table td,
table th {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
}
tbody td {
  font-weight: 500;
  color: #999999;
}
    </style>
<%
if (session == null || session.getAttribute("user") == null) {
    response.sendRedirect("login.jsp");
    return;
}
User user = (User) session.getAttribute("user");
PortfolioDAO portfolioOperations = new PortfolioImpl();
List<Portfolio> portfoliolist = portfolioOperations.getPortfoliosByUserId(user.getId());
%>
<body>
    <section class="intro">
        <div class="gradient-custom-1 h-100">
            <div class="mask d-flex align-items-center h-100">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                  <div class="table-responsive bg-white rounded-3" style="overflow-y: scroll; ">
                                <table class="table mb-0">
                                    <thead>
                                        <tr scope="row" style="color: #666666;">
                                            <th scope="col">Symbol</th>
                                            <th scope="col">Company</th>
                                            <th scope="col">Quantity</th>
                                            <th scope="col">Invested</th>
                                            <th scope="col">Total</th>
                                           
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <% for (Portfolio portfolio : portfoliolist) { %>
                                            <tr>
                                                <td><%= portfolio.getSymbol() %></td>
                                                <td><%= portfolio.getCompany() %></td>
                                                <td><%= portfolio.getQuantity() %></td>
                                                <td><%= portfolio.getBuyedPrice() %></td>
                                                <td><%= portfolio.getTotal() %></td>

                                            </tr>
                                        <% } %>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>

