<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.chainsys.model.*" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies

    User user = (User) session.getAttribute("user");
%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Stock Table with Pagination</title>
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
<style>
@import url('https://fonts.googleapis.com/css?family=Poppins:400,500,600,700&display=swap');

.pagination {
    justify-content: center;
    margin-top: 15px;
}

.filter-buttons, .search-bar {
    margin-bottom: 15px;
}

.sortable:hover {
    cursor: pointer;
    text-decoration: underline;
}

.sort-icon {
    margin-left: 5px;
}
</style>
</head>
<body>
<div class="row">
    <div class="col">
        <nav aria-label="breadcrumb" class="bg-body-tertiary rounded-3 p-3 mb-4">
            <ol class="breadcrumb mb-0 d-flex justify-content-between align-items-center w-100">
                <div class="breadcrumb-items d-flex align-items-center">
                    <li class="breadcrumb-item"><a href="home.jsp">Home</a></li>
                    <li class="breadcrumb-item active" aria-current="page">Stock details</li>
                </div>
                <div class="logo d-flex align-items-center">
                    <img src="assets/favicon.svg" width="32" height="32" alt="Chaintrade logo">
                    <p class="mb-0 ms-2">ChainTrade</p>
                </div>
            </ol>
        </nav>
    </div>
    <div class="container">
        <div class="row mb-3">
            <div class="col-md-8 filter-buttons text-left">
                <form method="get" action="StockServlet" id="filterForm">
                    <button type="submit" class="btn btn-primary" name="filterCategory" value="Small">Small Cap</button>
                    <button type="submit" class="btn btn-warning" name="filterCategory" value="Medium">Medium Cap</button>
                    <button type="submit" class="btn btn-success" name="filterCategory" value="Large">Large Cap</button>
                    <button type="submit" class="btn btn-info" name="filterCategory" value="All">All</button>
                </form>
            </div>
            <div class="col-md-4 search-bar">
                <form method="get" action="StockServlet" id="searchForm">
                    <input type="hidden" name="filterCategory" value="<%= request.getAttribute("filterCategory") %>">
                    <input type="hidden" name="page" value="1">
                    <input type="hidden" name="itemsPerPage" value="<%= request.getAttribute("itemsPerPage") %>">
                    <input type="text" class="form-control" id="searchInput" name="searchQuery" value="<%= request.getAttribute("searchQuery") %>" placeholder="Search for stocks...">
                </form>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover">
                <thead>
                    <tr>
                        <th>Symbol</th>
                        <th>Company Name</th>
                        <th id="priceHeader" class="sortable">Current Stock Price
                            <span id="priceSortIcon" class="sort-icon"></span>
                        </th>
                        <th>Cap Category</th>
                        <th colspan="2">Action</th>
                    </tr>
                </thead>
                <tbody id="stockTable">
                    <%
                        List<Stock> listStocks = (List<Stock>) request.getAttribute("listStocks");
                        if (listStocks != null) {
                            for (Stock stock : listStocks) {
                    %>
                    <tr>
                        <td><%= stock.getSymbol() %></td>
                        <td><%= stock.getCompanyName() %></td>
                        <td><%= stock.getCurrentStockPrice() %></td>
                        <td><%= stock.getCapCategory() %></td>
                        <td>
                            <form action="StockDetailServlet" method="post">
                                <input type="hidden" value="<%= stock.getSymbol() %>" name="symbol">
                                <button type="submit" class="btn btn-primary">View</button>
                            </form>
                        </td>
                        <td>
                            <button class="btn btn-success" data-toggle="modal" data-target="#buyModal" 
                                    data-symbol="<%= stock.getSymbol() %>" 
                                    data-price="<%= stock.getCurrentStockPrice() %>"
                                    data-stock-id="<%= stock.getStockId() %>">
                                Buy
                            </button>
                        </td>
                    </tr>
                    <%
                            }
                        }
                    %>
                </tbody>
            </table>
            <nav aria-label="Page navigation example">
                <ul class="pagination">
                    <%
                        int totalPages = (Integer) request.getAttribute("totalPages");
                        int currentPage = (Integer) request.getAttribute("currentPage");
                        for (int i = 1; i <= totalPages; i++) {
                    %>
                    <li class="page-item <%= i == currentPage ? "active" : "" %>">
                        <a class="page-link" href="?page=<%= i %>&itemsPerPage=<%= request.getAttribute("itemsPerPage") %>&filterCategory=<%= request.getAttribute("filterCategory") %>&searchQuery=<%= request.getAttribute("searchQuery") %>"><%= i %></a>
                    </li>
                    <%
                        }
                    %>
                </ul>
            </nav>
        </div>
    </div>
</div>
<!-- Buy Modal -->
<div class="modal fade" id="buyModal" tabindex="-1" role="dialog" aria-labelledby="buyModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <form action="BuyStockServlet" method="post">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title" id="buyModalLabel">Buy Stock</h5>
                    <button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group mb-3">
                        <label for="stockSymbol" class="font-weight-bold">Stock Symbol</label>
                        <input type="text" class="form-control" id="stockSymbol" name="symbol" readonly>
                    </div>
                    <div class="form-group mb-3">
                      <!--   <label for="stockId" class="font-weight-bold">Stock ID</label> -->
                        <input type="hidden" class="form-control" id="stockId" name="stockId" readonly>
                    </div>
                     <div class="form-group mb-3">
                        <label for="userid" class="font-weight-bold">userid</label>
                        <input type="number" class="form-control" id="userid" value=<%=user.getId() %> name="userid" readonly>
                    </div>
                    <div class="form-group mb-3">
                        <label for="stockPrice" class="font-weight-bold">Current Price</label>
                        <input type="text" class="form-control" id="stockPrice" name="price" readonly>
                    </div>
                    <div class="form-group mb-3">
                        <label for="quantity" class="font-weight-bold">Quantity</label>
                        <input type="number" class="form-control" id="quantity" name="quantity" min="1" max="10" value="1" required>
                    </div>
                    <div class="form-group mb-3">
                        <label for="totalPrice" class="font-weight-bold">Total Price</label>
                        <input type="text" class="form-control" id="totalPrice" name="totalPrice" readonly>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="submit" class="btn btn-primary">Buy</button>
                </div>
            </form>
        </div>
    </div>
</div>



<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.3/dist/umd/popper.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script>
let debounceTimer;
document.getElementById('searchInput').addEventListener('keyup', function() {
    clearTimeout(debounceTimer);
    debounceTimer = setTimeout(function() {
        document.getElementById('searchForm').submit();
    }, 1000);
});

let sortOrder = true;

document.getElementById('priceHeader').addEventListener('click', function() {
    let table = document.getElementById('stockTable');
    let rows = Array.from(table.getElementsByTagName('tr'));
    rows.sort((a, b) => {
        let priceA = parseFloat(a.cells[2].innerText) || 0;
        let priceB = parseFloat(b.cells[2].innerText) || 0;
        return sortOrder ? priceA - priceB : priceB - priceA;
    });
    sortOrder = !sortOrder;
    rows.forEach(row => table.appendChild(row));

    // Update sort icon
    let sortIcon = document.getElementById('priceSortIcon');
    sortIcon.innerHTML = sortOrder ? '▲' : '▼';
});
//Handle Buy Button Click
$('#buyModal').on('show.bs.modal', function (event) {
    let button = $(event.relatedTarget);
    let symbol = button.data('symbol');
    let price = button.data('price');
    let stockId = button.data('stock-id'); // Corrected variable name
    let modal = $(this);
    modal.find('.modal-body #stockSymbol').val(symbol);
    modal.find('.modal-body #stockPrice').val(price);
    modal.find('.modal-body #quantity').val(1);
    modal.find('.modal-body #totalPrice').val(price);
    modal.find('.modal-body #stockId').val(stockId); // Corrected the selector and variable name
});

// Update Total Price when Quantity Changes
document.getElementById('quantity').addEventListener('input', function() {
    let price = parseFloat(document.getElementById('stockPrice').value) || 0;
    let quantity = parseInt(this.value) || 1;
    document.getElementById('totalPrice').value = (price * quantity).toFixed(2);
});

</script>
</body>
</html>
