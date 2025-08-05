package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.Model.Customer;
import com.Util.MyDatabase;

public class CustomerDao {

	public static Customer insertCustomerAdmin(Customer customer) {

		String sqlCustomer = "INSERT INTO Customer (CustomerID, Title, CustomerName, Email, MobileNumber)"
				+ " VALUES (?, ?, ?, ?, ?)";

		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(sqlCustomer);) {

			pst.setLong(1, customer.getCustomerID());
			pst.setString(2, customer.getTitle());
			pst.setString(3, customer.getCustomerName());
			pst.setString(4, customer.getEmail());
			pst.setString(5, customer.getMobileNumber());

			int customerRowsAffected = pst.executeUpdate();

			if (customerRowsAffected > 0) {
				return customer;
			} else {
				System.out.println("Customer registration failed.");
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert Error: Customer Registration Data");
			e.printStackTrace();
		}
		return null;
	}

	public static Customer insertCustomer(Customer customer) {

		String sqlCustomer = "UPDATE Customer " + "SET " + "Title = ?, " + "CustomerName = ?, " + "Email = ?, "
				+ "MobileNumber = ?, " + "UserID = ?, " + "Password = ?," + "Status = 'active' "
				+ "WHERE CustomerID = ?";

		String sqlLogin = "INSERT INTO Login (CustomerID, Email, UserID, Password, Status)"
				+ " VALUES (?, ?, ?, ?, 'active')";

		try (Connection con = MyDatabase.getConnection();
				PreparedStatement pst = con.prepareStatement(sqlCustomer);
				PreparedStatement pstLogin = con.prepareStatement(sqlLogin)) {

			// Insert into Customer table
			pst.setLong(7, customer.getCustomerID());
			pst.setString(1, customer.getTitle());
			pst.setString(2, customer.getCustomerName());
			pst.setString(3, customer.getEmail());
			pst.setString(4, customer.getMobileNumber());
			pst.setString(5, customer.getUserId());
			pst.setString(6, customer.getPwd());

			// checkPaswordToInvalidateRegistration
			Customer CustTableValues = CustomerDao.searchByID(customer.getCustomerID());
			if (CustTableValues != null && CustTableValues.getPwd() != null) {
				return null;
			}

			int customerRowsAffected = pst.executeUpdate();

			if (customerRowsAffected > 0) {
				try {
					pstLogin.setLong(1, customer.getCustomerID());
					pstLogin.setString(2, customer.getEmail());
					pstLogin.setString(3, customer.getUserId());
					pstLogin.setString(4, customer.getPwd());

					int loginRowsAffected = pstLogin.executeUpdate();

					if (loginRowsAffected > 0) {
						System.out.println("Customer registration and login details insertion are successful.");
						return customer;
					} else {
						System.out.println("Customer registered, but login details insertion failed.");
					}
					con.close();
				} catch (SQLException e) {
					System.out.println("Error inserting into Login table.");

					// TODO: Delete from customer PENDING IMPLEMENTATION
					return null;
				}
			} else {
				System.out.println("Customer registration failed.");
			}
			con.close();
		} catch (SQLException e) {
			System.out.println("Insert Error: Customer Registration Data");
			e.printStackTrace();
		}
		return null;
	}

	public static Customer searchByID(long ID) {
		Customer cust = null;
		String sql = "SELECT CustomerID, Title, CustomerName, UserID, Email, MobileNumber, Password FROM Customer WHERE CustomerID = ?";
		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(sql)) {
			pst.setLong(1, ID);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					cust = new Customer();
					cust.setCustomerID(rs.getLong("CustomerID"));
					cust.setTitle(rs.getString("Title"));
					cust.setCustomerName(rs.getString("CustomerName"));
					cust.setUserId(rs.getString("UserID"));
					cust.setEmail(rs.getString("Email"));
					cust.setMobileNumber(rs.getString("MobileNumber"));
					cust.setPwd(rs.getString("Password"));
				}
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return cust;
	}

	public static boolean deactivateCustomer(long customerId) {
		String query = "UPDATE Customer SET Status = 'inactive' WHERE CustomerID = ?";
		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(query)) {

			pst.setLong(1, customerId);

			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public boolean updateCustomer(Customer cust) {
		String query = "UPDATE Customer SET Title = ?, CustomerName = ?, Email = ?, MobileNumber = ?, UserID = ?, Password = ? WHERE CustomerID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, cust.getTitle());
			pst.setString(2, cust.getCustomerName());
			pst.setString(3, cust.getEmail());
			pst.setString(4, cust.getMobileNumber());
			pst.setString(5, cust.getUserId());
			pst.setString(6, cust.getPwd());
			pst.setLong(7, cust.getCustomerID());

			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean updateCustomerDetail(Customer cust) {
		String query = "UPDATE Customer SET Title = ?, CustomerName = ?, Email = ?, MobileNumber = ?, UserId = ?,  Password = ? WHERE CustomerID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, cust.getTitle());
			pst.setString(2, cust.getCustomerName());
			pst.setString(3, cust.getEmail());
			pst.setString(4, cust.getMobileNumber());
			pst.setString(5, cust.getUserId());
			pst.setString(6, cust.getPwd());
			pst.setLong(7, cust.getCustomerID());

			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	public List<Customer> getCustomersByEmailDomain(String emailDomain) {
		String query = "SELECT * FROM Customer WHERE Email LIKE ?";
		List<Customer> customers = new ArrayList<>();

		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, "%" + emailDomain);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Customer cust = new Customer();
					cust.setCustomerID(rs.getLong("customerID"));
					cust.setTitle(rs.getString("Title"));
					cust.setCustomerName(rs.getString("customerName"));
					cust.setEmail(rs.getString("Email"));
					cust.setMobileNumber(rs.getString("MobileNumber"));
					cust.setUserId(rs.getString("UserID"));
					cust.setPwd(rs.getString("Password"));
					customers.add(cust);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	public static boolean checkUserId(String userID) {
		String query = "SELECT * FROM Customer WHERE UserID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, userID);

			try (ResultSet rs = pst.executeQuery()) {
				return !rs.next();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static int fetchCountOfUsers() {
		// TODO Auto-generated method stub
		String query = "SELECT COUNT(*) FROM Customer";
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

	public static String getCurrentHashedPassword(long customerID) {
		String hashedPassword = null;
		String query = "SELECT Password FROM Customer WHERE CustomerID = ?";
		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerID);
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				hashedPassword = new String(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return hashedPassword;
	}

	public static boolean updatePassword(long customerID, String hashedNewPwd) {
		String query = "UPDATE Customer SET Password = ? WHERE CustomerID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, hashedNewPwd);
			pst.setLong(2, customerID);

			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean reactivateAccount(long customerID) {
		String query = "UPDATE Customer SET Status = 'active' WHERE CustomerID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerID);

			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}