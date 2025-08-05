package com.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.Dao.ComplaintDao;
import com.Model.Complaint;

public class ComplaintService {

	public static List<Complaint> fetchUserComplaints(long customerID) {
		// Return an empty list if no complaints are found for the customer
		return ComplaintDao.getComplaintsByCustomerId(customerID);
	}

	public List<Complaint> fetchFilteredUserComplaints(long customerID, String statusFilter, String complaintIDString) {
		List<Complaint> complaints = new ArrayList<>();

		if (complaintIDString == null || complaintIDString.isEmpty()) {
			if ("all".equals(statusFilter)) {
				return ComplaintService.fetchUserComplaints(customerID);
			}
			// Retrieve complaints based on customer ID and status
			complaints = ComplaintDao.getComplaintsByCustomerIdAndStatus(customerID, statusFilter);
		} else {
			try {
				long complaintID = Long.parseLong(complaintIDString);
				// Add a single complaint if complaintID is specified
				complaints.add(ComplaintDao.getComplaintByCustomerIdAndComplaintID(customerID, complaintID));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return complaints;
	}

	public int getCountOfComplaints() {
		return ComplaintDao.fetchCountOfComplaints();
	}

	public List<Complaint> fetchAllComplaints() {
		return ComplaintDao.getAllComplaints();
	}

	public List<Complaint> fetchFilteredComplaints(String statusFilter, String complaintIDString, String complaintType,
			String category) {
		// Check if a specific complaint ID is provided
		if (complaintIDString != null && !complaintIDString.isEmpty()) {
			try {
				long complaintID = Long.parseLong(complaintIDString);
				// Fetch the complaint by ID (ignores all other filters)
				Complaint complaint = ComplaintDao.getComplaintByComplaintID(complaintID);
				// Return a single-element list if the complaint exists, or an empty list
				// otherwise
				return complaint != null ? Arrays.asList(complaint) : new ArrayList<>();
			} catch (NumberFormatException e) {
				// Invalid complaint ID format; return an empty list
				return new ArrayList<>();
			}
		}

		// Build the SQL query dynamically if no complaint ID is provided
		StringBuilder sqlQuery = new StringBuilder("SELECT * FROM Complaint WHERE 1=1");
		List<String> params = new ArrayList<>();

		// Add filters to the query based on input
		if (!"all".equalsIgnoreCase(statusFilter)) {
			sqlQuery.append(" AND ComplaintStatus = ?");
			params.add(statusFilter.toLowerCase());
		}

		if (!"all".equalsIgnoreCase(complaintType)) {
			sqlQuery.append(" AND ComplaintType = ?");
			params.add(complaintType.toLowerCase());
		}

		if (!"all".equalsIgnoreCase(category)) {
			sqlQuery.append(" AND Category = ?");
			params.add(category.toLowerCase());
		}

		// Call the DAO to fetch complaints based on the generated query and parameters
		return ComplaintDao.getComplaintsByFilters(sqlQuery.toString(), params);
	}

	public boolean toggleComplaintStatus(String complaintID) {
		// Check what the current status of the complaint is
		Complaint complaint = ComplaintDao.getComplaintByComplaintID(Long.parseLong(complaintID));
		if (complaint == null) {
			return false;
		}

		// Change the status of the complaint
		if ("pending".equalsIgnoreCase(complaint.getComplaintStatus())) {
			// If the complaint is open, change it to closed
			return ComplaintDao.changeComplaintStatus(Long.parseLong(complaintID), "resolved");
		} else if ("closed".equalsIgnoreCase(complaint.getComplaintStatus())) {
			// If the complaint is closed, change it to open
			return ComplaintDao.changeComplaintStatus(Long.parseLong(complaintID), "pending");
		}

		return false;
	}

}
