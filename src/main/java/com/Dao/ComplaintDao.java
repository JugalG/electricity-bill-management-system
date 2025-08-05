package com.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.Model.Complaint;
import com.Util.MyDatabase;

public class ComplaintDao {

	private static long generateUniqueComplaintID(Connection connection) {
		Random random = new Random();
		long maxComplaintID = 9999999999L, minComplaintID = 1000000000L;
		long ComplaintID;

		do {
			ComplaintID = minComplaintID + (long) (random.nextDouble() * (maxComplaintID - minComplaintID + 1));
		} while (isComplaintIDExists(connection, ComplaintID));

		return ComplaintID;
	}

	private static boolean isComplaintIDExists(Connection connection, long ComplaintID) {
		String checkQuery = "SELECT COUNT(*) FROM Complaint WHERE ComplaintID = ?";
		try (PreparedStatement stmt = connection.prepareStatement(checkQuery)) {
			stmt.setLong(1, ComplaintID);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException e) {
			System.err.println("Error checking payment ID: " + e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	public static Complaint addComplaint(Connection con, Complaint complaint) {
		String query = "INSERT INTO Complaint (ComplaintID,CustomerID, ComplaintType, Category,Landmark, Problem, Address, MobileNumber, ComplaintDate, ComplaintStatus) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?)";
		long ComplaintID = generateUniqueComplaintID(con);
		complaint.setComplaintDate(Date.valueOf(LocalDate.now()).toLocalDate());
		complaint.setComplaintStatus("pending");

		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, ComplaintID);
			pst.setLong(2, complaint.getCustomerID());
			pst.setString(3, complaint.getComplaintType());
			pst.setString(4, complaint.getCategory());
			pst.setString(5, complaint.getLandmark());
			pst.setString(6, complaint.getProblem());
			pst.setString(7, complaint.getAddress());
			pst.setString(8, complaint.getMobileNumber());
			pst.setDate(9, Date.valueOf(LocalDate.now()));
			pst.setString(10, complaint.getComplaintStatus());

			int rowsAffected = pst.executeUpdate();
			if (rowsAffected > 0) {

				return new Complaint(ComplaintID, complaint.getCustomerID(), complaint.getComplaintType(),
						complaint.getCategory(), complaint.getLandmark(), complaint.getProblem(),
						complaint.getAddress(), complaint.getMobileNumber(), complaint.getComplaintDate(),
						complaint.getComplaintStatus());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Complaint> getComplaintsByFilters(String sqlQuery, List<String> params) {
		List<Complaint> complaints = new ArrayList<>();
		try (Connection connection = MyDatabase.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
			for (int i = 0; i < params.size(); i++) {
				preparedStatement.setString(i + 1, params.get(i));
			}
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				complaints.add(new Complaint(resultSet.getLong("ComplaintID"), resultSet.getLong("CustomerID"),
						resultSet.getString("ComplaintType"), resultSet.getString("Category"),
						resultSet.getString("Landmark"), resultSet.getString("Problem"), resultSet.getString("Address"),
						resultSet.getString("MobileNumber"), resultSet.getDate("ComplaintDate").toLocalDate(),
						resultSet.getString("ComplaintStatus")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public static Complaint getComplaintByComplaintID(long complaintId) {
		String query = "SELECT * FROM Complaint WHERE ComplaintID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, complaintId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<Complaint> getComplaintsByCustomerId(long customerId) {
		String query = "SELECT * FROM Complaint WHERE CustomerID = ?";
		List<Complaint> complaints = new ArrayList<>();
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					complaints.add(new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public static List<Complaint> getComplaintsByCustomerIdAndStatus(long customerId, String complaintStatus) {
		String query = "SELECT * FROM Complaint WHERE CustomerID = ? AND complaintStatus = ?";
		List<Complaint> complaints = new ArrayList<>();
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			pst.setString(2, complaintStatus);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					complaints.add(new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public static Complaint getComplaintByCustomerIdAndComplaintID(long customerId, long complaintID) {
		String query = "SELECT * FROM Complaint WHERE CustomerID = ? AND complaintID = ?";
		Complaint complaint = null;
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			pst.setLong(2, complaintID);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					complaint = new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaint;
	}

	public List<Complaint> getComplaintByStatus(Connection con, String status) {
		String query = "SELECT * FROM Complaint WHERE ComplaintStatus = ?";
		List<Complaint> complaints = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, status);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					complaints.add(new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public List<Complaint> getComplaintByComplaintType(Connection con, String complaintType) {
		String query = "SELECT * FROM Complaint WHERE ComplaintType = ?";
		List<Complaint> complaints = new ArrayList<>();
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, complaintType);
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					complaints.add(new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public static int fetchCountOfComplaints() {
		String query = "SELECT COUNT(*) FROM Complaint";
		try (Connection con = MyDatabase.getConnection(); Statement stmt = con.createStatement()) {

			try (ResultSet rs = stmt.executeQuery(query)) {
				if (rs.next()) {
					return rs.getInt(1);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
		return 0;
	}

	public static List<Complaint> getAllComplaints() {
		String query = "SELECT * FROM Complaint";
		List<Complaint> complaints = new ArrayList<>();
		try (Connection con = MyDatabase.getConnection(); Statement stmt = con.createStatement()) {
			try (ResultSet rs = stmt.executeQuery(query)) {
				while (rs.next()) {
					complaints.add(new Complaint(rs.getLong("ComplaintID"), rs.getLong("CustomerID"),
							rs.getString("ComplaintType"), rs.getString("Category"), rs.getString("Landmark"),
							rs.getString("Problem"), rs.getString("Address"), rs.getString("MobileNumber"),
							rs.getDate("ComplaintDate").toLocalDate(), rs.getString("ComplaintStatus")));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return complaints;
	}

	public static boolean changeComplaintStatus(long complaintID, String status) {
		String query = "UPDATE Complaint SET ComplaintStatus = ? WHERE ComplaintID = ?";
		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, status);
			pst.setLong(2, complaintID);
			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
