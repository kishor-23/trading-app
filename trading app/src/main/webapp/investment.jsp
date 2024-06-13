<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
       <!-- favicon -->
       <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
       <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

       <link rel="icon" href="assets/favicon.svg" type="image/x-icon">
       <link rel="stylesheet" href="style/navbar.css">
       <script src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
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
    <section class="intro">
        <div class="gradient-custom-1 h-100">
          <div class="mask d-flex align-items-center h-100">
            <div class="container">
              <div class="row justify-content-center">
                <div class="col-12">
                  <div class="table-responsive bg-white rounded-3" style="overflow-y: scroll; ">
                    <table class="table mb-0  ">
                      <thead>
                        <tr>
                          <th scope="col">Symbol</th>
                          <th scope="col">Company</th>
                          <th scope="col">Quantity</th>
                          <th scope="col">invested</th>
                          <th scope="col">Total</th>
                          <th scope="col">Current price</th>
                        </tr>
                      </thead>
                      <tbody>
                        
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