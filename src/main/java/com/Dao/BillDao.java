package com.Dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import com.Model.Bill;
import com.Model.Payment;
import com.Util.MyDatabase;

public class BillDao {

	public boolean createBill(Connection con, Bill bill) {
		String query = "INSERT INTO Bill (BillID, CustomerID, DueAmount, PaymentAmount, PaymentID, PaymentStatus, BillDate, BillExpiryDate) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, bill.getBillID());
			pst.setLong(2, bill.getCustomerID());
			pst.setDouble(3, bill.getDueAmount());
			pst.setDouble(4, bill.getPaymentAmount());
			pst.setLong(5, bill.getPaymentID());
			pst.setString(6, bill.getPaymentStatus());
			pst.setDate(7, bill.getBillDate());
			pst.setDate(8, bill.getBillExpiryDate());
			int rowsAffected = pst.executeUpdate();
			System.out.println("Bill Created Status" + rowsAffected);
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean createBillByAdmin(Connection con, Bill bill) {
		String query = "INSERT INTO Bill (BillID, CustomerID, DueAmount, BillDate, BillExpiryDate,ConsumedUnits) "
				+ "VALUES (?, ?, ?, ?, ?,?)";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, bill.getBillID());
			pst.setLong(2, bill.getCustomerID());
			pst.setDouble(3, bill.getDueAmount());
			pst.setDate(4, bill.getBillDate());
			pst.setDate(5, bill.getBillExpiryDate());
			pst.setInt(6, bill.getConsumedUnits());

			int rowsAffected = pst.executeUpdate();
			System.out.println("Bill Created Status" + rowsAffected);
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Bill updateBillValueUponPayment(Payment paymt) {
		String query = "UPDATE Bill SET PaymentAmount = ?, PaymentID = ?,PaymentStatus  = ? WHERE BillID = ?";
		try {
			Connection con = MyDatabase.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, paymt.getPaymentAmount());
			pst.setLong(2, paymt.getPaymentID());
			pst.setString(3, "paid");
			pst.setLong(4, paymt.getIndiBillID());
			int rowsAffected = pst.executeUpdate();
			System.out.println("BillUpdateStatus: " + rowsAffected);
			if (rowsAffected > 0) {
				Bill b = new Bill();
				b.setBillID(paymt.getIndiBillID());
				b.setCustomerID(paymt.getCustomerID());
				b.setPaymentAmount(paymt.getPaymentAmount());
				b.setPaymentID(paymt.getPaymentID());
				b.setPaymentStatus("paid");
				b.setBillDate(Date.valueOf(paymt.getPaymentDate()));
				return b;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Error connection to DB");
			System.out.println(e.getMessage());
		}
		return null;

	}
	public static Bill updateBillValueUponPaymentFailed(Payment paymt) {
		String query = "UPDATE Bill SET PaymentAmount = ?, PaymentID = ?,PaymentStatus  = ? WHERE BillID = ?";
		try {
			Connection con = MyDatabase.getConnection();
			PreparedStatement pst = con.prepareStatement(query);
			pst.setDouble(1, 0);
			pst.setLong(2, 0L);
			pst.setString(3, "pending");
			pst.setLong(4, paymt.getIndiBillID());
			int rowsAffected = pst.executeUpdate();
			System.out.println("BillUpdateStatus: " + rowsAffected);
			if (rowsAffected > 0) {
				Bill b = new Bill();
				b.setBillID(paymt.getIndiBillID());
				b.setCustomerID(paymt.getCustomerID());
				b.setPaymentAmount(paymt.getPaymentAmount());
				b.setPaymentID(paymt.getPaymentID());
				b.setPaymentStatus("paid");
				b.setBillDate(Date.valueOf(paymt.getPaymentDate()));
				return b;
			} else {
				return null;
			}
		} catch (Exception e) {
			System.out.println("Error connection to DB");
			System.out.println(e.getMessage());
		}
		return null;
		
	}
	
	public static boolean deleteBillByPaymentID(Connection con, long paymntID) {
		String query = "DELETE FROM Bill WHERE PaymentID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {

			pst.setLong(1, paymntID);

			int rowsAffected = pst.executeUpdate();
			return rowsAffected > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static List<Bill> getBillsByCustomerId(long customerId) {
		String query = "SELECT * FROM Bill WHERE CustomerID = ?";
		List<Bill> bills = new ArrayList<>();

		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Bill bill = new Bill(rs.getLong("BillID"), rs.getLong("CustomerID"), rs.getString("ConnectionType"),
							rs.getInt("ConsumedUnits"), rs.getDouble("DueAmount"), rs.getDouble("PaymentAmount"),
							rs.getLong("PaymentID"), rs.getString("PaymentStatus"),
							Date.valueOf(rs.getDate("BillDate").toLocalDate()),
							Date.valueOf(rs.getDate("BillExpiryDate").toLocalDate())

					);
					bills.add(bill);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bills;
	}

	public static HashSet<Bill> getAllBills() {
		try (Connection con = MyDatabase.getConnection()) {
			String query = "SELECT * FROM Bill";
			HashSet<Bill> bills = new HashSet<>();

			try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

				while (rs.next()) {
					Bill bill = new Bill(rs.getLong("BillID"), rs.getLong("CustomerID"), rs.getString("ConnectionType"),
							rs.getInt("ConsumedUnits"), rs.getDouble("DueAmount"), rs.getDouble("PaymentAmount"),
							rs.getLong("PaymentID"), rs.getString("PaymentStatus"),
							Date.valueOf(rs.getDate("BillDate").toLocalDate()),
							Date.valueOf(rs.getDate("BillExpiryDate").toLocalDate()));
					bills.add(bill);
				}
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return bills;
		} catch (Exception e) {
			System.out.println("Databse error in fetching Bills");
			return null;
		}

	}

	public static Bill getBillById(long billId) {
		String query = "SELECT * FROM Bill WHERE BillID = ?";
		Bill bill = null;

		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(query)) {

			pst.setLong(1, billId);

			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					bill = new Bill(rs.getLong("BillID"), rs.getLong("CustomerID"), rs.getString("ConnectionType"),
							rs.getInt("ConsumedUnits"), rs.getDouble("DueAmount"), rs.getDouble("PaymentAmount"),
							rs.getLong("PaymentID"), rs.getString("PaymentStatus"),
							Date.valueOf(rs.getDate("BillDate").toLocalDate()),
							Date.valueOf(rs.getDate("BillExpiryDate").toLocalDate()));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return bill;
	}

	public List<Bill> getAllUnpaidBills(Connection con) {
		String query = "SELECT * FROM Bill WHERE PaymentStatus = 'pending'";
		List<Bill> unpaidBills = new ArrayList<>();

		try (PreparedStatement pst = con.prepareStatement(query)) {

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Bill bill = new Bill(rs.getLong("BillID"), rs.getLong("CustomerID"), rs.getString("ConnectionType"),
							rs.getInt("ConsumedUnits"), rs.getDouble("DueAmount"), rs.getDouble("PaymentAmount"),
							rs.getLong("PaymentID"), rs.getString("PaymentStatus"),
							Date.valueOf(rs.getDate("BillDate").toLocalDate()),
							Date.valueOf(rs.getDate("BillExpiryDate").toLocalDate()));
					unpaidBills.add(bill);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unpaidBills;
	}

	public static List<Bill> getAllUnpaidBillByID(long customerId) {
		String query = "SELECT * FROM Bill WHERE CustomerID = ? AND PaymentStatus = 'pending'";
		List<Bill> unpaidBills = new ArrayList<>();

		Connection connection = MyDatabase.getConnection();
		try (PreparedStatement pst = connection.prepareStatement(query)) {

			pst.setLong(1, customerId);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Bill bill = new Bill(rs.getLong("BillID"), rs.getLong("CustomerID"), rs.getInt("ConsumedUnits"),
							rs.getDouble("DueAmount"), rs.getDouble("PaymentAmount"), rs.getLong("PaymentID"),
							rs.getString("PaymentStatus"), Date.valueOf(rs.getDate("BillDate").toLocalDate()),
							Date.valueOf(rs.getDate("BillExpiryDate").toLocalDate()));
					unpaidBills.add(bill);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return unpaidBills;
	}

	public static List<Bill> getAllBillsByPaymentStatus(String status) {
		String query = "SELECT * FROM Bill WHERE PaymentStatus = ?";
		List<Bill> unpaidBills = new ArrayList<>();

		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, status);

			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Bill bill = new Bill(rs.getLong("BillID"), rs.getLong("CustomerID"), rs.getString("ConnectionType"),
							rs.getInt("ConsumedUnits"), rs.getDouble("DueAmount"), rs.getDouble("PaymentAmount"),
							rs.getLong("PaymentID"), rs.getString("PaymentStatus"),
							Date.valueOf(rs.getDate("BillDate").toLocalDate()),
							Date.valueOf(rs.getDate("BillExpiryDate").toLocalDate()));
					unpaidBills.add(bill);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return unpaidBills;
	}


	public static int fetchCountOfBillsRegardlessStatus() {
		String query = "SELECT COUNT(*) FROM Bill ";
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

	public static int fetchCountOfBillsWithStatus(String status) {
		String query = "SELECT COUNT(*) FROM Bill WHERE PaymentStatus = '" + status + "'";
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

}
