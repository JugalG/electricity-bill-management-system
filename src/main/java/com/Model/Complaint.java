package com.Model;

import java.time.LocalDate;

public class Complaint {
	private long ComplaintID;
	private long CustomerID;
	private String ComplaintType;
	private String Category;
	private String Landmark;
	private String Problem;
	private String Address;
	private String MobileNumber;
	private LocalDate ComplaintDate;
	private String ComplaintStatus;

	public Complaint(long complaintID, long customerID, String complaintType, String category, String landmark,
			String problem, String address, String mobileNumber, LocalDate complaintDate, String complaintStatus) {
		super();
		ComplaintID = complaintID;
		CustomerID = customerID;
		ComplaintType = complaintType;
		Category = category;
		Landmark = landmark;
		Problem = problem;
		Address = address;
		MobileNumber = mobileNumber;
		ComplaintDate = complaintDate;
		ComplaintStatus = complaintStatus;
	}

	public Complaint(long customerID, String complaintType, String category, String landmark, String problem,
			String address, String mobileNumber) {
		super();
		CustomerID = customerID;
		ComplaintType = complaintType;
		Category = category;
		Landmark = landmark;
		Problem = problem;
		Address = address;
		MobileNumber = mobileNumber;

	}

	public Complaint(long customerID, String complaintType, String category, String landmark, String problem,
			String address, String mobileNumber, LocalDate complaintDate, String complaintStatus) {
		super();
		CustomerID = customerID;
		ComplaintType = complaintType;
		Category = category;
		Landmark = landmark;
		Problem = problem;
		Address = address;
		MobileNumber = mobileNumber;
		ComplaintDate = complaintDate;
		ComplaintStatus = complaintStatus;

	}

	public long getComplaintID() {
		return ComplaintID;
	}

	public void setComplaintID(long complaintID) {
		ComplaintID = complaintID;
	}

	public long getCustomerID() {
		return CustomerID;
	}

	public void setCustomerID(long customerID) {
		CustomerID = customerID;
	}

	public String getComplaintType() {
		return ComplaintType;
	}

	public void setComplaintType(String complaintType) {
		ComplaintType = complaintType;
	}

	public String getCategory() {
		return Category;
	}

	public void setCategory(String category) {
		Category = category;
	}

	public String getLandmark() {
		return Landmark;
	}

	public void setLandmark(String landmark) {
		Landmark = landmark;
	}

	public String getProblem() {
		return Problem;
	}

	public void setProblem(String problem) {
		Problem = problem;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getMobileNumber() {
		return MobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		MobileNumber = mobileNumber;
	}

	public LocalDate getComplaintDate() {
		return ComplaintDate;
	}

	public void setComplaintDate(LocalDate complaintDate) {
		ComplaintDate = complaintDate;
	}

	public String getComplaintStatus() {
		return ComplaintStatus;
	}

	public void setComplaintStatus(String complaintStatus) {
		ComplaintStatus = complaintStatus;
	}

	@Override
	public String toString() {
		return "Complaint [ComplaintID=" + ComplaintID + ", CustomerID=" + CustomerID + ", ComplaintType="
				+ ComplaintType + ", Category=" + Category + ", Landmark=" + Landmark + ", Problem=" + Problem
				+ ", Address=" + Address + ", MobileNumber=" + MobileNumber + ", ComplaintDate=" + ComplaintDate
				+ ", ComplaintStatus=" + ComplaintStatus + "]";
	}

}
