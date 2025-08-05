package com.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Model.Login;
import com.Util.HashUtil;
import com.Util.MyDatabase;

public class LoginDao {
	public static Login checkLoginDetailsAndReturnLogin(Login login) {

		String query = "Select LoginID,CustomerID,UserID,Password,UserType,Status FROM Login where UserId = ?";
		Connection con = MyDatabase.getConnection();
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, login.getUserID());

			try {
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					String loginIdUser = rs.getString("LoginID");
					// Retrieve login details from Login and Customer tables
					String loginUserID = rs.getString("UserId");
					long loginCustomerID = rs.getLong("CustomerID");
					String loginPassword = (String) rs.getString("Password").trim();
					String loginStatus = rs.getString("Status");
					String loginUserType = rs.getString("UserType");
					String hashedLoginPassword = HashUtil.hashPassword(login.getPassword().trim());

					if (hashedLoginPassword.equals(loginPassword)) {
						login.setLoginID(Integer.parseInt(loginIdUser));
						login.setUserID(loginUserID);
						login.setPassword(null);
						login.setStatus(loginStatus);
						login.setUserType(loginUserType);
						login.setCustomerID(loginCustomerID);
						return login;
					} else {
						System.out.println("Invalid Password");
						return null;
					}
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				con.close();
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());

		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		}
		return null;
	}

	public boolean isCustomerLoginPresent(Connection con, long customerId) throws SQLException {
		String query = "SELECT COUNT(*) FROM Login WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	public static boolean registerLoginDetails(Login login) throws SQLException {
		String query = "INSERT INTO Login (CustomerID, Email, UserID, Password, UserType, Status) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, login.getCustomerID());
			pst.setString(2, login.getEmail());
			pst.setString(3, login.getUserID());
			pst.setString(4, login.getPassword());
			pst.setString(5, login.getUserType());
			pst.setString(6, login.getStatus());
			return pst.executeUpdate() > 0;
		}
	}

	public Login getLoginDetails(Connection con, long customerId) throws SQLException {
		@SuppressWarnings("unused")
		Login lgn;
		String query = "SELECT * FROM Login WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					lgn = new Login(rs.getString("UserID"), rs.getString("Password"), rs.getString("UserType"),
							rs.getString("Status"));
				}
			}
		}
		return null;
	}
	public boolean changeUserTypeToAdmin(Connection con, long customerId) throws SQLException {
		String query = "UPDATE Login SET UserType = 'Admin' WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			return pst.executeUpdate() > 0;
		}
	}

	public boolean changeUserTypeToUser(Connection con, long customerId) throws SQLException {
		String query = "UPDATE Login SET UserType = 'User' WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			return pst.executeUpdate() > 0;
		}
	}

	public boolean changeUserStatusToActive(Connection con, long customerId) throws SQLException {
		String query = "UPDATE Login SET Status = 'Active' WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			return pst.executeUpdate() > 0;
		}
	}

	public boolean changeUserStatusToInactive(Connection con, long customerId) throws SQLException {
		String query = "UPDATE Login SET Status = 'Inactive' WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			return pst.executeUpdate() > 0;
		}
	}

	public static boolean checkIfEmailExists(String email) {
		String query = "SELECT COUNT(*) FROM Login WHERE Email = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, email);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String checkUserActiveInactiveStatus(Connection con, long customerId) throws SQLException {
		String query = "SELECT Status FROM Login WHERE CustomerID = ?";
		try (PreparedStatement pst = con.prepareStatement(query)) {
			pst.setLong(1, customerId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getString("Status");
				}
			}
		}
		return null;
	}

	public static String getHighestAdminUserID() {
		String query = "SELECT userID FROM Login WHERE customerID = (SELECT MAX(customerID) FROM Login WHERE userType = 'admin')";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getString(1);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace(); 
		} catch (Exception e) {
			e.printStackTrace(); 
		}

		return null;
	}

	public static Long getHighestCustomerIDByUserType(String userType) {
		String query = "SELECT MAX(CustomerID) FROM Login WHERE userType = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {

			pst.setString(1, userType);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return rs.getLong(1);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace(); 
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}

	public static List<Login> getAllAdmins() {
		List<Login> admins = new ArrayList<>();
		String query = "SELECT * FROM Login WHERE UserType = 'admin'";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			try (ResultSet rs = pst.executeQuery()) {
				while (rs.next()) {
					Login admin = new Login(rs.getInt("LoginID"), rs.getLong("CustomerID"), rs.getString("Email"),
							rs.getString("UserID"), rs.getString("Password"), rs.getString("UserType"),
							rs.getString("Status"));
					admins.add(admin);
				}
			}
		} catch (SQLException se) {
			se.printStackTrace(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return admins;
	}

	public static Login getAdminById(String adminId) {
		String query = "SELECT * FROM Login WHERE UserID = ?";
		try (Connection con = MyDatabase.getConnection(); PreparedStatement pst = con.prepareStatement(query)) {
			pst.setString(1, adminId);
			try (ResultSet rs = pst.executeQuery()) {
				if (rs.next()) {
					return new Login(rs.getInt("LoginID"), rs.getLong("CustomerID"), rs.getString("Email"),
							rs.getString("UserID"), rs.getString("Password"), rs.getString("UserType"),
							rs.getString("Status"));
				}
			}
		} catch (SQLException se) {
			se.printStackTrace(); 
		} catch (Exception e) {
			e.printStackTrace(); 
		}
		return null;
	}
}
