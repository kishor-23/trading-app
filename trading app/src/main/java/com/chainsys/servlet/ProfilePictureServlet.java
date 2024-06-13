package com.chainsys.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.chainsys.util.DbConnection;

@WebServlet("/ProfilePictureServlet")
@MultipartConfig
public class ProfilePictureServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT profilePicture FROM users WHERE id = ?")) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Blob blob = rs.getBlob("profilePicture");
                InputStream inputStream = blob.getBinaryStream();

                // Determine the size of the blob and initialize the byte array
                int fileLength = (int) blob.length();
                byte[] imageBytes = new byte[fileLength];
                int bytesRead = 0;
                int offset = 0;

                // Read the entire input stream
                while (offset < fileLength && (bytesRead = inputStream.read(imageBytes, offset, fileLength - offset)) != -1) {
                    offset += bytesRead;
                }

                // Ensure all bytes are read
                if (offset < fileLength) {
                    throw new IOException("Could not read the entire file: only read " + offset + " bytes; expected " + fileLength + " bytes");
                }

                response.setContentType("image/jpeg");
                try (ServletOutputStream outputStream = response.getOutputStream()) {
                    outputStream.write(imageBytes);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        Blob profilePicture = null;
        Part filePart = request.getPart("profilePicture"); 
        
        if (filePart != null) {
            InputStream inputStream = filePart.getInputStream();
            
            if (inputStream.available() > 0) {
                try {
                    profilePicture = new javax.sql.rowset.serial.SerialBlob(inputStream.readAllBytes());
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            // Continue processing with profilePicture
            
            try (Connection conn = DbConnection.getConnection()) {
                String sql = "UPDATE users SET profilePicture = ? WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setBlob(1, profilePicture);
                    stmt.setInt(2, userId);
                    int rowsUpdated = stmt.executeUpdate();
                    if (rowsUpdated > 0) {

                    	response.sendRedirect("NomineeServlet?action=list");
                        
                        
                    }
                }
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            } 
        } 
    }


}
