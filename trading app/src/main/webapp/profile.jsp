<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.List" %>
<%@ page import="com.chainsys.model.*" %>
<%@ page import="com.chainsys.dao.*" %>
<%@ page import="com.chainsys.impl.*" %>
<%@ page import="javax.servlet.http.HttpSession" %>
<%
    if (session == null || session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setHeader("Expires", "0"); // Proxies

    User user = (User) session.getAttribute("user");
    TransactionDAO transOP=new TransactionImpl();
    
%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>User Profile</title>
  <link rel="icon" href="assets/favicon.svg" type="image/x-icon">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
  <style>
  /* Popup */
.popup {
  display: none;
  position: fixed;
  z-index: 9999;
  left: 0;
  top: 0;
  width: 100%;
  height: 100%;
  overflow: auto;
  background-color: rgba(0, 0, 0, 0.7);
}

/* Popup Content */
.popup-content {
  position: relative;
  margin: auto;
  width: 80%;
  padding-top:20px;
  max-width: 600px;
}

/* Close Button */
.close {
  position: absolute;
  top: 15px;
  right: 15px;
  color: white;
  font-size: 24px;
  font-weight: bold;
  cursor: pointer;
}
/* Custom scrollbar design */
::-webkit-scrollbar {
  width: 0;
}
  
    .form-popup-bg, .form-popup-bg-add-money, .modal {
      display: none;
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.5);
      z-index: 9999;
    }

    .form-container {
      background-color: white;
      border-radius: 5px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
      padding: 20px;
      width: 300px;
      position: absolute;
      top: 50%;
      left: 50%;
      transform: translate(-50%, -50%);
    }

    .close-button {
      position: absolute;
      top: 10px;
      right: 10px;
      cursor: pointer;
    }

    .form-container h1 {
      margin-top: 0;
    }

    .form-group {
      margin-bottom: 15px;
    }

    .form-group label {
      display: block;
      margin-bottom: 5px;
    }

    .form-control {
      width: 100%;
      padding: 8px;
      border: 1px solid #ccc;
      border-radius: 4px;
      box-sizing: border-box;
    }

    .form-control:focus {
      outline: none;
      border-color: #007bff;
    }

    button {
      background-color: #007bff;
      color: white;
      padding: 10px 20px;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    button:hover {
      background-color: #0056b3;
    }
  </style>
</head>
<body>
  <section style="background-color: #eee;">
    <div class="container py-5">
      <div class="row">
        <div class="col">
          <nav aria-label="breadcrumb" class="bg-body-tertiary rounded-3 p-3 mb-4">
            <ol class="breadcrumb mb-0 d-flex justify-content-between align-items-center w-100">
              <div class="breadcrumb-items d-flex align-items-center">
                <li class="breadcrumb-item"><a href="home.jsp">Home</a></li>
                <li class="breadcrumb-item active" aria-current="page">User Profile</li>
              </div>
              <div class="logo d-flex align-items-center">
                <img src="assets/favicon.svg" width="32" height="32" alt="Cryptex logo">
                <p class="mb-0 ms-2">ChainTrade</p>
              </div>
            </ol>
          </nav>
        </div>
      </div>

      <div class="row">
        <div class="col-lg-4">
          <div class="card mb-4">
            <div class="card-body text-center">
              <% if (user.getProfilePicture() != null) { %>
                <img id="profileImage" src="ProfilePictureServlet?userId=<%= user.getId() %>" onclick="openProfilePicturePopup()"  alt="avatar" class="rounded-circle img-fluid" style="width: 150px;">
              <% } else { %>
                <img src="assets/default-user-image.jpg"  onclick="openProfilePicturePopup()" alt="Default Avatar" class="rounded-circle img-fluid" style="width: 150px;">
              <% } %>
              <h5 class="my-3"><%= user.getName() %></h5>
             
              <div class="d-flex justify-content-center mb-2">
                <a class="btn btn-primary" href="LogoutServlet">Logout</a>
                <button id="btnUpdateProfilePicture" class="btn btn-outline-primary ms-1">Update Profile Picture</button>
              </div>
            </div>
          </div>
          <div class="card mb-4 mb-lg-0">
            <div class="card-body p-1">
              <p style="padding-left: 16px; padding-top:15px;">Wallet</p>
              <div class="d-flex justify-content-between align-items-center p-3 ">
                <p class="mb-0">Total Balance</p>
                <p class="mb-0">$ <%= user.getBalance() %></p>
                <button id="btnOpenAddMoneyForm">Add Money</button>
              </div>
              <!-- <div class="d-flex justify-content-between align-items-center p-3 border-bottom">
                <p class="mb-0">Invested</p>
                <p class="mb-0">$ 500</p>
                <button>Withdraw</button>
              </div>
 -->            </div>
          </div>
          
         <div style=" margin-top:5px">
         
         
  <div class="card mt-2 mb-5 mb-lg-0  " >
  <div class="card-body">
  <form action="transactions" method="post">
    <input type="hidden" name="userId" value="<%= user.getId() %>">
    <button type="submit" style="text-decoration:none; color: inherit; text-decoration: inherit; border: none; background: none; cursor: pointer;"><span class="text-primary font-italic me-1">Order</span> stock details</button>
    </form>

                  

<div class="card-body p-1" style="height: 150px; overflow-y: auto;">
    <%
        List<Transaction> transList = transOP.getLastFiveTransactionsByUserId(user.getId());
    %>
    <div class="d-flex justify-content-between align-items-center p-3">
        <p class="mb-0">Company</p>
        <p class="mb-0">Quantity</p>
        <p class="mb-0">Total</p>
        <p class="mb-0">Type</p>
    </div>
    <% for (Transaction transaction : transList) { %>
        <div class="d-flex justify-content-between align-items-center p-3">
            <p class="mb-0"><%= transaction.getCompanyName() %></p>
            <p class="mb-0"><%= transaction.getShares() %></p>
            <p class="mb-0">$<%= transaction.getPrice() %></p>
            <p class="mb-1 btn <%= "buy".equals(transaction.getTransactionType()) ? "bg-success text-white" : "bg-danger text-white" %> rounded">
                <%= transaction.getTransactionType() %>
            </p>
        </div>
    <% } %>
</div>

  </div>
  
  </div>
    <div class="card mt-2 mb-5 mb-lg-0  " >
  <div class="card-body">
  <form action="transactions" method="post">
    <input type="hidden" name="userId" value="<%= user.getId() %>">
    <button type="submit" style="text-decoration:none; color: inherit; text-decoration: inherit; border: none; background: none; cursor: pointer;"><span class="text-primary font-italic me-1">Order</span> stock details</button>
    </form>

                  

<div class="card-body p-1" style="height: 150px; overflow-y: auto;">
    <%
/*         List<Transaction> transList = transOP.getLastFiveTransactionsByUserId(user.getId());
 */    %>
    <div class="d-flex justify-content-between align-items-center p-3">
        <p class="mb-0">Company</p>
        <p class="mb-0">Quantity</p>
        <p class="mb-0">Total</p>
        <p class="mb-0">Type</p>
    </div>
    <% for (Transaction transaction : transList) { %>
        <div class="d-flex justify-content-between align-items-center p-3">
            <p class="mb-0"><%= transaction.getCompanyName() %></p>
            <p class="mb-0"><%= transaction.getShares() %></p>
            <p class="mb-0">$<%= transaction.getPrice() %></p>
            <p class="mb-1 btn <%= "buy".equals(transaction.getTransactionType()) ? "bg-success text-white" : "bg-danger text-white" %> rounded">
                <%= transaction.getTransactionType() %>
            </p>
        </div>
    <% } %>
</div>

  </div>
  
  </div>
</div>
          
          
        </div>
        
        <div class="col-lg-8">
          <div class="card mb-4">
            <div class="card-body">
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Full Name</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0"><%= user.getName() %></p>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Email</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0"><%= user.getEmail() %></p>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Date of Birth</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0"><%= user.getDob() %></p>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Phone</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0"><%= user.getPhone() %></p>
                </div>
              </div>
              <hr>
              <div class="row">
                <div class="col-sm-3">
                  <p class="mb-0">Pancard No</p>
                </div>
                <div class="col-sm-9">
                  <p class="text-muted mb-0"><%= user.getPancardno() %></p>
                </div>
              </div>
            </div>
          </div>

          <div class="row">
  <div class="col-md-6 mb-1">
    <div class="card mb-4 p-1">
      <div class="card-body">
        <div class="d-flex justify-content-between align-items-center mb-4">
          <p class="mb-0"><span class="text-primary font-italic me-1">Nominee</span> details</p>
          <p class="mb-0">
            <button id="btnOpenNomineeForm">Add Nominee</button>
          </p>
        </div>
        <div class="card-body p-1 mb-2" style="height: 300px; overflow-y: auto;">
        <% List<Nominee> nominees = (List<Nominee>) request.getAttribute("listNominees");
        if (nominees != null) {
          for (Nominee nominee : nominees) { %>
          
          <div class="row border   mt-2 md-2 mx-1 rounded  ">
            
              <div class="col-sm-4 mt-2 md-9">
                <p class="mb-0">Name</p>
              </div>
              <div class="col-sm-8 mt-2">
                <p class="text-muted mb-0"><%= nominee.getNomineeName() %></p>
              </div>
           
            
            
              <div class="col-sm-4">
                <p class="mb-0">Relation</p>
              </div>
              <div class="col-sm-8">
                <p class="text-muted mb-0"><%= nominee.getRelationship() %></p>
              </div>
            
            
            
              <div class="col-sm-4">
                <p class="mb-0">Phone</p>
              </div>
              <div class="col-sm-8 mb-0 p-1">
                <p class="text-muted mb-0"><%= nominee.getPhoneno() %></p>
              </div>
        
            
            <div class="d-flex justify-content-between">
            
            </div>
            <div class="col-sm-12 d-flex justify-content-end mb-2">
      <button class="btn btn-primary btnEditNominee"
              data-id="<%= nominee.getNomineeId() %>"
              data-name="<%= nominee.getNomineeName() %>"
              data-relationship="<%= nominee.getRelationship() %>"
              data-phone="<%= nominee.getPhoneno() %>">Edit</button>
              <button class="btn btn-danger  ms-1 btnDeleteNominee" data-id="<%= nominee.getNomineeId() %>">Delete</button>
                     
               </div>      
             </div>
          <% }
        } %>
        </div>
        
      </div>
      
    </div>
    
  </div>
    <div class="col-md-6">
              <div class="card mb-4 mb-md-0">
                 <div class="card-body">
                 
                   <a href="StockServlet"style="text-decoration:none;  color: inherit;
  text-decoration: inherit;" >  <p class="mb-4"><span class="text-primary font-italic me-1" href="">Stock</span> details
                  </p></a>
                        <p class="mb-1" style="font-size: .77rem;">Small cap</p>
                        <div class="progress rounded" style="height: 5px;">
                            <div class="progress-bar" role="progressbar" style="width: 80%" aria-valuenow="80" aria-valuemin="0" aria-valuemax="100" data-toggle="tooltip" title="80%"></div>
                        </div>
                        <p class="mt-4 mb-1" style="font-size: .77rem;">Medium cap</p>
                        <div class="progress rounded" style="height: 5px;">
                            <div class="progress-bar" role="progressbar" style="width: 72%" aria-valuenow="72" aria-valuemin="0" aria-valuemax="100" data-toggle="tooltip" title="72%"></div>
                        </div>
                        <p class="mt-4 mb-1" style="font-size: .77rem;">Large cap</p>
                        <div class="progress rounded" style="height: 5px;">
                            <div class="progress-bar" role="progressbar" style="width: 89%" aria-valuenow="89" aria-valuemin="0" aria-valuemax="100" data-toggle="tooltip" title="89%"></div>
                        </div>
                    </div>
              </div>
                       
  <div class="card mt-2 mb-5 mb-lg-0  " >
  <div class="card-body">
  <a href="Portfolio"style="text-decoration:none;  color: inherit;
  text-decoration: inherit;" >  <p class="mb-4"><span class="text-primary font-italic me-1" href="">Portfolio</span> details
                  </p></a>
                   <%
/*         List<Transaction> transList = transOP.getLastFiveTransactionsByUserId(user.getId());
 */    %>
    <div class="d-flex justify-content-between align-items-center p-3">
        <p class="mb-0">stockId</p>
        <p class="mb-0">Quantity</p>
        <p class="mb-0">Total</p>
        <p class="mb-0">Type</p>
    </div>
    <% for (Transaction transaction : transList) { %>
        <div class="d-flex justify-content-between align-items-center p-3">
            <p class="mb-0"><%= transaction.getCompanyName() %></p>
            <p class="mb-0"><%= transaction.getShares() %></p>
            <p class="mb-0">$<%= transaction.getPrice() %></p>
            <p class="mb-1 btn <%= "buy".equals(transaction.getTransactionType()) ? "bg-success text-white" : "bg-danger text-white" %> rounded">
                <%= transaction.getTransactionType() %>
            </p>
        </div>
    <% } %>
                      
  </div>
  </div>
              
            </div>
            
</div>



      <!-- Update Profile Picture Form -->
      <div class="form-popup-bg" id="updateProfilePicturePopup">
        <div class="form-container">
          <span class="close-button" id="btnCloseUpdateProfilePicture">&times;</span>
          <h1>Update Profile Picture</h1>
          <form action="ProfilePictureServlet" method="post" enctype="multipart/form-data">
            <input type="hidden" name="userId" value="<%= user.getId() %>">
            <div class="form-group">
              <label for="profilePicture">Select a new profile picture:</label>
              <input type="file" id="profilePicture" name="profilePicture" class="form-control" required>
            </div>
            <button type="submit">Update</button>
          </form>
        </div>
      </div>

     <!-- Add Nominee Form -->
<div class="form-popup-bg" id="addNomineePopup">
  <div class="form-container">
    <span class="close-button" id="btnCloseNomineeForm">&times;</span>
    <h1>Add Nominee</h1>
    <form action="NomineeServlet" method="post">
      <input type="hidden" name="userId" value="<%= user.getId() %>">
       <input type="hidden" name="action" value="add">
      <div class="form-group">
        <label for="nomineeName">Nominee Name:</label>
        <input type="text" id="nomineeName" name="name" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="relationship">Relationship:</label>
        <select id="relationship" name="relationship" class="form-control" required>
          <option value="">Select Relationship</option>
          <option value="Spouse">Spouse</option>
          <option value="Parent">Parent</option>
          <option value="Sibling">Sibling</option>
          <option value="Child">Child</option>
          <!-- Add more options as needed -->
        </select>
      </div>
      <div class="form-group">
        <label for="phone">Phone:</label>
        <input type="text" id="phone" name="phone" class="form-control" required>
      </div>
      <button type="submit">Add Nominee</button>
    </form>
  </div>
</div>


      <!-- Add Money Form -->
      <div class="form-popup-bg" id="addMoneyPopup">
        <div class="form-container">
          <span class="close-button" id="btnCloseAddMoneyForm">&times;</span>
          <h1>Add Money</h1>
          <form action="MoneyServlet" method="post">
            <input type="hidden" name="userId" value="<%= user.getId() %>">
            <div class="form-group">
              <label for="amount">Amount:</label>
              <input type="number" id="amount" name="amount" class="form-control" required>
            </div>
              <div class="form-group">
                    <label for="paymentMethod">Payment Method:</label>
                    <select id="paymentMethod" name="paymentMethod" class="form-control" required>
                        <option value="card">Card</option>
                        <option value="paypal">PayPal</option>
                        <option value="bank">Bank Transfer</option>
                    </select>
                </div>
            <button type="submit">Add Money</button>
          </form>
        </div>
      </div>

    </div>
  </section>

<!-- Edit Nominee Form -->
<div class="form-popup-bg" id="editNomineePopup" style="display: none;">
  <div class="form-container">
    <span class="close-button" id="btnCloseEditNomineeForm">&times;</span>
    <h1>Edit Nominee</h1>
    <form id="editNomineeForm" action="NomineeServlet" method="post">
      <input type="hidden" name="action" value="update">
      <input type="hidden" name="nomineeId" id="editNomineeId">
      <input type="hidden" name="userId" value="<%= user.getId() %>">
      <div class="form-group">
        <label for="editNomineeName">Nominee Name:</label>
        <input type="text" id="editNomineeName" name="nomineeName" class="form-control" required>
      </div>
      <div class="form-group">
        <label for="editRelationship">Relationship:</label>
        <select id="editRelationship" name="relationship" class="form-control" required>
          <option value="">Select Relationship</option>
          <option value="Spouse">Spouse</option>
          <option value="Parent">Parent</option>
          <option value="Sibling">Sibling</option>
          <option value="Child">Child</option>
          <!-- Add more options as needed -->
        </select>
      </div>
      <div class="form-group">
        <label for="editPhone">Phone:</label>
        <input type="text" id="editPhone" name="phone" class="form-control" required>
      </div>
      <button type="submit">Update Nominee</button>
    </form>
  </div>
</div>

<!-- Delete Nominee Form -->
<div class="form-popup-bg" id="deleteNomineePopup">
  <div class="form-container">
    <span class="close-button" id="btnCloseDeleteNomineeForm">&times;</span>
    <h1>Delete Nominee</h1>
    <form id="deleteNomineeForm" action="NomineeServlet" method="post">
      <input type="hidden" name="action" value="delete">
      <input type="hidden" name="nomineeId" id="deleteNomineeId">
      <input type="hidden" name="userId" value="<%= user.getId() %>">
      <p>Are you sure you want to delete this nominee?</p>
      <button type="submit" class="btn btn-danger">Delete</button>
      <button type="button" class="btn btn-secondary" id="btnCancelDeleteNominee">Cancel</button>
    </form>
  </div>
</div>
<!-- Profile Picture Popup -->
<div id="profilePicturePopup" class="popup">
  <div class="popup-content">
    <img id="profilePictureImg" src="ProfilePictureServlet?userId=<%= user.getId() %>" alt="Profile Picture" class="rounded-circle img-fluid">
    <span class="close" onclick="closeProfilePicturePopup()">&times;</span>
  </div>
</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
$(document).ready(function() {
  $('.btnEditNominee').on('click', function() {
    var nomineeId = $(this).data('id');
    var nomineeName = $(this).data('name');
    var relationship = $(this).data('relationship');
    var phone = $(this).data('phone');

    // Populate the form fields
    $('#editNomineeId').val(nomineeId);
    $('#editNomineeName').val(nomineeName);
    $('#editRelationship').val(relationship);
    $('#editPhone').val(phone);

    // Show the form
    $('#editNomineePopup').show();
  });

  $('#btnCloseEditNomineeForm').on('click', function() {
    $('#editNomineePopup').hide();
  });
});
</script>
<script>

  document.getElementById('btnOpenNomineeForm').addEventListener('click', function() {
    document.getElementById('addNomineePopup').style.display = 'block';
  });

  document.getElementById('btnCloseNomineeForm').addEventListener('click', function() {
    document.getElementById('addNomineePopup').style.display = 'none';
  });

  document.getElementById('btnOpenAddMoneyForm').addEventListener('click', function() {
    document.getElementById('addMoneyPopup').style.display = 'block';
  });

  document.getElementById('btnCloseAddMoneyForm').addEventListener('click', function() {
    document.getElementById('addMoneyPopup').style.display = 'none';
  });

 
  document.querySelectorAll('.btnEditNominee').forEach(button => {
    button.addEventListener('click', function() {
    	 const nomineeId = this.getAttribute('data-id');
    	    // Set the value of nomineeId input field in the edit form
    	    document.getElementById('nomineeId').value = nomineeId;
    	    
      document.getElementById('editNomineePopup').style.display = 'block';
     
     
    });
    });


  function populateEditForm(nominee) {
    document.getElementById('editNomineeId').value = nominee.nomineeId;
    document.getElementById('editNomineeName').value = nominee.nomineeName;
    document.getElementById('editRelation').value = nominee.relationship;
    document.getElementById('editPhone').value = nominee.phoneNo;
  }

  document.getElementById('btnCloseEditNomineeForm').addEventListener('click', function() {
    document.getElementById('editNomineePopup').style.display = 'none';
  });

  document.getElementById('btnCloseEditNomineeForm').addEventListener('click', function() {
    document.getElementById('editNomineePopup').style.display = 'none';
  });

  // Delete Nominee
  document.querySelectorAll('.btnDeleteNominee').forEach(button => {
    button.addEventListener('click', function() {
      const nomineeId = this.getAttribute('data-id');
      document.getElementById('deleteNomineeId').value = nomineeId;
      document.getElementById('deleteNomineePopup').style.display = 'block';
    });
  });

  document.getElementById('btnCloseDeleteNomineeForm').addEventListener('click', function() {
    document.getElementById('deleteNomineePopup').style.display = 'none';
  });

  document.getElementById('btnCancelDeleteNominee').addEventListener('click', function() {
    document.getElementById('deleteNomineePopup').style.display = 'none';
  });
</script>

  <script>
    document.getElementById('btnUpdateProfilePicture').addEventListener('click', function() {
      document.getElementById('updateProfilePicturePopup').style.display = 'block';
    });

    document.getElementById('btnCloseUpdateProfilePicture').addEventListener('click', function() {
      document.getElementById('updateProfilePicturePopup').style.display = 'none';
    });

    document.getElementById('btnOpenNomineeForm').addEventListener('click', function() {
      document.getElementById('addNomineePopup').style.display = 'block';
    });

    document.getElementById('btnCloseNomineeForm').addEventListener('click', function() {
      document.getElementById('addNomineePopup').style.display = 'none';
    });

    document.getElementById('btnOpenAddMoneyForm').addEventListener('click', function() {
      document.getElementById('addMoneyPopup').style.display = 'block';
    });

    document.getElementById('btnCloseAddMoneyForm').addEventListener('click', function() {
      document.getElementById('addMoneyPopup').style.display = 'none';
    });
    
 // Open Profile Picture Popup
    function openProfilePicturePopup() {
      var profilePicturePopup = document.getElementById("profilePicturePopup");
      var profilePictureImg = document.getElementById("profilePictureImg");
      profilePictureImg.src = "ProfilePictureServlet?userId=<%= user.getId() %>"; // Set the source of the profile picture
      profilePicturePopup.style.display = "block";
    }

    // Close Profile Picture Popup
    function closeProfilePicturePopup() {
      var profilePicturePopup = document.getElementById("profilePicturePopup");
      profilePicturePopup.style.display = "none";
    }
  </script>
</body>
</html>
