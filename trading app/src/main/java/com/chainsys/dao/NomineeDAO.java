package com.chainsys.dao;

import java.sql.SQLException;
import java.util.List;

import com.chainsys.model.Nominee;

public interface NomineeDAO {
	void addNominee(Nominee nominee) throws SQLException, ClassNotFoundException;
    Nominee getNomineeById(int nomineeId) throws SQLException , ClassNotFoundException;
    List<Nominee> getAllNomineesByUserId(int userId) throws SQLException, ClassNotFoundException;
    void updateNominee(Nominee nominee) throws SQLException , ClassNotFoundException;
    void deleteNominee(int nomineeId) throws SQLException , ClassNotFoundException;
}
