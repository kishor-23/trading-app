package com.chainsys.dao;

import java.sql.SQLException;


import com.chainsys.model.User;

public interface UserDAO {
	void addUser(User user) throws SQLException, ClassNotFoundException;

	User getUserByEmailAndPassword(String email, String password) throws SQLException, ClassNotFoundException;

	User  getUserByEmail(String email) throws ClassNotFoundException, SQLException;
	 boolean checkUserAlreadyExists(String mailId) throws ClassNotFoundException, SQLException;
}
