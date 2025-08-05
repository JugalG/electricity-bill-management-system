package com.Model;

public class Customer {

	private long CustomerID;
	private String Title;
	private String CustomerName;
	private String Email;
	private String MobileNumber;
	private String UserId;
	private String Pwd;
	// Customer Validation Script returns this
	private String ConfirmPwd;
	public Customer(long customerID, String title, String customerName, String email, String mobileNumber,
			String userId, String pwd) {
		super();
		CustomerID = customerID;
		Title = title;
		CustomerName = customerName;
		Email = email;
		MobileNumber = mobileNumber;
		UserId = userId;
		Pwd = pwd;
	}

	public Customer() {
		super();
	}

	public Customer(long customerID, String title, String customerName, String email, String mobileNumber,
			String userId, String pwd, String confirmPwd) {
		super();
		CustomerID = customerID;
		Title = title;
		CustomerName = customerName;
		Email = email;
		MobileNumber = mobileNumber;
		UserId = userId;
		Pwd = pwd;
		ConfirmPwd = confirmPwd;
	}

	public long getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getPwd() {
		return Pwd;
	}

	public void setPwd(String pwd) {
		Pwd = pwd;
	}

	public String getConfirmPwd() {
		return ConfirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		ConfirmPwd = confirmPwd;
	}

	@Override
	public String toString() {
		return "Customer [CustomerID=" + CustomerID + ", Title=" + Title + ", CustomerName=" + CustomerName + ", Email="
				+ Email + ", MobileNumber=" + MobileNumber + ", UserId=" + UserId + ", Pwd=" + Pwd + ", ConfirmPwd="
				+ ConfirmPwd + "]";
	}

	

}