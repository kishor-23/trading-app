package com.chainsys.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.chainsys.dao.UserDAO;
import com.chainsys.model.User;
import com.chainsys.util.*;

public class UserImpl implements UserDAO {
	 private Connection con;

	    public UserImpl() throws ClassNotFoundException, SQLException {
	        this.con = DbConnection.getConnection();
	    }

	    @Override
	    public void addUser(User user) throws SQLException, ClassNotFoundException {
	        String selectQuery = "SELECT email FROM users WHERE email = ?";
	        PreparedStatement selectStatement = con.prepareStatement(selectQuery);
	        selectStatement.setString(1, user.getEmail());
	        ResultSet resultSet = selectStatement.executeQuery();

	        if (!resultSet.next()) {
	            String insertQuery = "INSERT INTO users (name, email, pancardno, phone, dob, password, profilePicture, balance) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	            PreparedStatement insertStatement = con.prepareStatement(insertQuery);
	            insertStatement.setString(1, user.getName());
	            System.out.println(user.toString());
	            
	            insertStatement.setString(2, user.getEmail());
	            insertStatement.setString(3, user.getPancardno());
	            insertStatement.setString(4, user.getPhone());
	            insertStatement.setDate(5, user.getDob());
	            insertStatement.setString(6, user.getPassword());
	            insertStatement.setBlob(7, user.getProfilePicture());
	            insertStatement.setDouble(8, user.getBalance());
	            insertStatement.executeUpdate();
	            System.out.println("User registered successfully.");
	        } else {
	            System.out.println("User already exists with this email ID. Please use another email ID to sign up or sign in.");
	        }
	    }


	    @Override
	    public User getUserByEmailAndPassword(String email, String password) throws SQLException, ClassNotFoundException {
	        String selectQuery = "SELECT id, name, email, pancardno, phone, dob, profilePicture, balance FROM users WHERE email = ? AND password = ?";
	        try (PreparedStatement pstmt = con.prepareStatement(selectQuery)) {
	            pstmt.setString(1, email);
	            pstmt.setString(2, password);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    User user = new User();
	                    user.setId(rs.getInt("id"));
	                    user.setName(rs.getString("name"));
	                    user.setEmail(rs.getString("email"));
	                    user.setPancardno(rs.getString("pancardno"));
	                    user.setPhone(rs.getString("phone"));
	                    user.setDob(rs.getDate("dob"));
	                    // Assuming profilePicture is a Blob
	                    user.setProfilePicture(rs.getBlob("profilePicture"));
	                    user.setBalance(rs.getDouble("balance"));
	                    return user;
	                }
	            }
	        }
	        return null;
	    }

	    
	    @Override
	    public boolean checkUserAleardyExists(String mailId) throws ClassNotFoundException, SQLException {
			String selectQuery = "SELECT email,password FROM users WHERE email = ? ";
			PreparedStatement selectStatement = con.prepareStatement(selectQuery);
			selectStatement.setString(1, mailId);
			ResultSet resultSet = selectStatement.executeQuery();

			if (resultSet.next()) {
          		System.out.println("User already exists with this email ID. use another email id to sign up or sign in ");
				return true;
			}
			else {
				return false;
			}

		}
	    public User  getUserByEmail(String email) throws ClassNotFoundException, SQLException{
	    	 String selectQuery = "SELECT id, name, email, pancardno, phone, dob, profilePicture, balance FROM users WHERE email = ? ";
		        try (PreparedStatement pstmt = con.prepareStatement(selectQuery)) {
		            pstmt.setString(1, email);
		            
		            try (ResultSet rs = pstmt.executeQuery()) {
		                if (rs.next()) {
		                    User user = new User();
		                    user.setId(rs.getInt("id"));
		                    user.setName(rs.getString("name"));
		                    user.setEmail(rs.getString("email"));
		                    user.setPancardno(rs.getString("pancardno"));
		                    user.setPhone(rs.getString("phone"));
		                    user.setDob(rs.getDate("dob"));
		                    // Assuming profilePicture is a Blob
		                    user.setProfilePicture(rs.getBlob("profilePicture"));
		                    user.setBalance(rs.getDouble("balance"));
		                    return user;
		                }
		            }
		        }
		        return null;
	    }
	    public void addMoneyToUser(int userId, double amount) throws SQLException {
	    	  String sql = "UPDATE users SET balance = balance + ? WHERE id = ?";
	        PreparedStatement ps = con.prepareStatement(sql);
	            // SQL to update the balance
	            ps = con.prepareStatement(sql);
	            ps.setDouble(1, amount);
	            ps.setInt(2, userId);
	            ps.executeUpdate();
	        } 
	    
	



}
