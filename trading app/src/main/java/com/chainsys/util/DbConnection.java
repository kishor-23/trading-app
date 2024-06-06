package com.chainsys.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/trading_db_1", "root", "root");

		return connection;
	}

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Connection con = getConnection();
		System.out.println(con);

	}


}