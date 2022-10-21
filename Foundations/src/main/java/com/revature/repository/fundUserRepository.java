package com.revature.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.model.fundUser;
import com.revature.util.ConnectionFactory;

public class fundUserRepository {
	
//---------------------------VALIDATES USER ID---------------------------------
	public boolean validateUserId(String name) {
		PreparedStatement stmt = null;
		ResultSet set = null;
		boolean validStatus = false;
		final String SQL = "SELECT * FROM fundUser WHERE fundUser_name = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, name);
			set = stmt.executeQuery();
			if(set.next()) {
				validStatus = true;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				set.close();
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return validStatus;
	}
	
//------------USER OBJECT VALIDTAION TRUE=VALID FALSE=INVALID------------------
	public boolean userIsValid (fundUser user) {
		boolean valid = false;
		
		if(
			user.getFundUser_name().length() > 0 &&
			user.getFundUser_password().length() > 0) {
			valid = true;
		}
		
		return valid;
	}
	
//------------USERNAME VALIDATION---TRUE=AVAILABLE---FALSE=TAKEN---------------
	public boolean availableName(String username) {
		boolean available = true;
		PreparedStatement stmt = null;
		ResultSet set = null;
		final String SQL = "SELECT * FROM fundUser WHERE fundUser_name = ?";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, username);
			set = stmt.executeQuery();
			
			if(set.next()) {
				available = false;
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return available;
	}
	
//---------------------------------ADD USER------------------------------------
	public void addUser(fundUser user) {
		PreparedStatement stmt = null;
		final String SQL = "INSERT INTO fundUser(fundUser_id, fundUser_name, "
				+ "fundUser_password, fundUser_role) VALUES(default, ?, ?, 'Employee')";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, user.getFundUser_name());
			stmt.setString(2, user.getFundUser_password());
			stmt.execute();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
//----------------------AUTHENTICATES VALID MANAGER USER OBJECT-----------------------
	public fundUser authenticateManager(fundUser login) {
		PreparedStatement stmt = null;
		fundUser authenticatedUser = null;
		ResultSet set = null;
		final String SQL = "SELECT * FROM fundUser WHERE fundUser_name = ? AND "
				+ "fundUser_password = ? AND fundUser_role = 'Manager'";
		
		try(Connection conn = ConnectionFactory.getConnection()) {
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1, login.getFundUser_name());
			stmt.setString(2, login.getFundUser_password());
			set = stmt.executeQuery();
			
			if(set.next()) {
				authenticatedUser = new fundUser(
						set.getInt(1),
						set.getString(2),
						set.getString(3),
						set.getString(4)
						);
			}else {
				authenticatedUser = new fundUser();
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				stmt.close();
				set.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		return authenticatedUser;
	}
	
	//----------------------AUTHENTICATES VALID EMPLOYEE USER OBJECT-----------------------
		public fundUser authenticateEmployee(fundUser login) {
			PreparedStatement stmt = null;
			fundUser authenticatedUser = null;
			ResultSet set = null;
			final String SQL = "SELECT * FROM fundUser WHERE fundUser_name = ? AND "
					+ "fundUser_password = ? AND fundUser_role = 'Employee'";
			
			try(Connection conn = ConnectionFactory.getConnection()) {
				stmt = conn.prepareStatement(SQL);
				stmt.setString(1, login.getFundUser_name());
				stmt.setString(2, login.getFundUser_password());
				set = stmt.executeQuery();
				
				if(set.next()) {
					authenticatedUser = new fundUser(
							set.getInt(1),
							set.getString(2),
							set.getString(3),
							set.getString(4)
							);
				}else {
					authenticatedUser = new fundUser();
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					stmt.close();
					set.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return authenticatedUser;
		}
		
//---------------------------------------------------------------------------------------------------
		
}
