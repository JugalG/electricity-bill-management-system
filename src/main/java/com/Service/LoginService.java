package com.Service;

import java.util.List;

import com.Dao.LoginDao;
import com.Model.Login;

public class LoginService {

	public String getNextAdminUserId() {
		// Query the DAO to find the userID of the admin with the highest Employee ID
		String highestAdminUserID = LoginDao.getHighestAdminUserID();

		if (highestAdminUserID == null) {
			// If no admin exists, return the first admin ID
			return "admin001";
		}
		try {
			String prefix = "admin";
			int number = Integer.parseInt(highestAdminUserID.substring(prefix.length()));
			number++; 
			// Return the next admin ID in the sequence
			return String.format("%s%03d", prefix, number);
		} catch (NumberFormatException e) {
			throw new RuntimeException("Failed to parse admin ID: " + highestAdminUserID, e);
		}
	}

	public long getUniqueEmployeeID() {
		// Query the DAO to find the highest Employee ID with user Type as admin
		Long highestEmployeeID = LoginDao.getHighestCustomerIDByUserType("admin");

		if (highestEmployeeID == null || highestEmployeeID.equals(0L)) {
			// If no Employee ID exists, return the default starting ID
			return 1000000000000L;
		}
		// Return the next Employee ID in the sequence
		return highestEmployeeID + 1;
	}

	public boolean registerAdmin(Login admin) {
		boolean success = false;
		// 1. Check if email exists
		if (LoginDao.checkIfEmailExists(admin.getEmail())) {
			System.out.println("Email ID is already registered!");
			return success;
		}
		// 2. Register the admin
		try {
			success = LoginDao.registerLoginDetails(admin);
		} catch (Exception e) {
			System.err.println("Failed to register admin: " + e.getMessage());
		}
		return success;
	}

	public boolean checkIfEmailExists(String email) {
		return LoginDao.checkIfEmailExists(email);
	}

	public List<Login> getAllAdmins() {
		return LoginDao.getAllAdmins();
	}

	public Login getAdminById(String adminId) {
		return LoginDao.getAdminById(adminId);
	}

	public Login checkLoginDetailsAndReturnLogin(Login login) {
		return LoginDao.checkLoginDetailsAndReturnLogin(login);
	}

}
