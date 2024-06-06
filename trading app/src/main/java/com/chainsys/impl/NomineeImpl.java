package com.chainsys.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.dao.NomineeDAO;
import com.chainsys.model.Nominee;
import com.chainsys.util.DbConnection;

public class NomineeImpl implements NomineeDAO {
	 private Connection con;

	    public NomineeImpl() throws ClassNotFoundException, SQLException {
	        this.con = DbConnection.getConnection();
	    }

	@Override
	public void addNominee(Nominee nominee) throws SQLException, ClassNotFoundException {
		 String sql = "INSERT INTO Nominee (nominee_name, relationship, user_id,phone_no) VALUES (?, ?, ?,?)";
	        try ( PreparedStatement pstmt = con.prepareStatement(sql)) {
	            pstmt.setString(1, nominee.getNomineeName());
	            pstmt.setString(2, nominee.getRelationship());
	            pstmt.setInt(3, nominee.getUserId());
	            pstmt.setString(4,nominee.getPhoneno());
	            pstmt.executeUpdate();
	        }
		
	}

	@Override
	public Nominee getNomineeById(int nomineeId) throws SQLException ,ClassNotFoundException {
		Nominee nominee = null;
        String sql = "SELECT nominee_id, nominee_name, relationship, user_id,phone_no FROM Nominee WHERE nominee_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nomineeId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    nominee = new Nominee();
                    nominee.setNomineeId(rs.getInt("nominee_id"));
                    nominee.setNomineeName(rs.getString("nominee_name"));
                    nominee.setRelationship(rs.getString("relationship"));
                    nominee.setUserId(rs.getInt("user_id"));
                    nominee.setPhoneno(rs.getString("phone_no"));
                }
            }
        }
        return nominee;
	}

	@Override
	public List<Nominee> getAllNomineesByUserId(int userId) throws SQLException ,ClassNotFoundException{

		 List<Nominee> nominees = new ArrayList<>();
	        String sql = "SELECT nominee_id, nominee_name, relationship,phone_no FROM Nominee WHERE user_id = ?";
	        try (Connection conn = DbConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, userId);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    Nominee nominee = new Nominee();
	                    nominee.setNomineeId(rs.getInt("nominee_id"));
	                    nominee.setNomineeName(rs.getString("nominee_name"));
	                    nominee.setRelationship(rs.getString("relationship"));
	                    nominee.setUserId(userId);
	                    nominee.setPhoneno(rs.getString("phone_no"));
	                    nominees.add(nominee);
	                }
	            }
	        }
	        return nominees;
	}

	@Override
	public void updateNominee(Nominee nominee) throws SQLException, ClassNotFoundException {
		 String sql = "UPDATE Nominee SET nominee_name = ?, relationship = ? WHERE nominee_id = ?";
	        try (Connection conn = DbConnection.getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, nominee.getNomineeName());
	            pstmt.setString(2, nominee.getRelationship());
	            pstmt.setInt(3, nominee.getNomineeId());
	            pstmt.executeUpdate();
	        }
		
	}

	@Override
	public void deleteNominee(int nomineeId) throws SQLException, ClassNotFoundException {
		String sql = "DELETE FROM Nominee WHERE nominee_id = ?";
        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, nomineeId);
            pstmt.executeUpdate();
        }
    
		
	}

}
