package com.Model;

public class Login {

	int LoginID;
	long CustomerID;
	String Email;
	String UserID;
	String Password;
	String UserType;
	String Status;

	public Login(int loginID, long customerID, String email, String userID, String password, String status) {
		super();
		LoginID = loginID;
		CustomerID = customerID;
		Email = email;
		UserID = userID;
		Password = password;
		Status = status;
	}

	// LoginDao.checkLoginDetails takes the following constructor
	public Login(String userID, String password) {
		super();
//		LoginID = null;
//    	CustomerID = null;
//    	Email = null;
		// logindao.checkLoginDetails returns the filled in values
		UserID = userID;
		Password = password;
		UserType = null;
		Status = null;

	}

	public Login(String userID, String password, String userType, String status) {
		super();

		UserID = userID;
		Password = password;
		UserType = userType;
		Status = status;
	}

	public Login() {
		super();
	}

	public Login(long employeeID, String email, String adminUserID, String hashedPassword, String userType,
			String status) {
		CustomerID = employeeID;
		Email = email;
		UserID = adminUserID;
		Password = hashedPassword;
		UserType = userType;
		Status = status;
	}

	public Login(int loginID, long customerID, String email, String userID, String password, String userType,
			String status) {
		super();
		LoginID = loginID;
		CustomerID = customerID;
		Email = email;
		UserID = userID;
		Password = password;
		UserType = userType;
		Status = status;
	}

	public int getLoginID() {
		return LoginID;
	}

	public void setLoginID(int loginID) {
		LoginID = loginID;
	}

	public long getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public String toString() {
		return "login [LoginID=" + LoginID + ", CustomerID=" + CustomerID + ", Email=" + Email + ", UserID=" + UserID
				+ ", Password=" + Password + ", UserType=" + UserType + ", Status=" + Status + "]";
	}

	public String toStringWithoutPassword() {
		return "login [LoginID=" + LoginID + ", CustomerID=" + CustomerID + ", Email=" + Email + ", UserID=" + UserID
				+ ", UserType=" + UserType + ", Status=" + Status + "]";
	}
}
