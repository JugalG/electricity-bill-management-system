package com.Util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDatabase {

	public static void main(String[] args) {
		Connection con = getConnection();
		createMyDatabase(con);
	}

	public static Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

			String derbyDbPath = System.getenv("Derby_DB_PATH");
			String url = "";
			if (derbyDbPath != null) {
				url = derbyDbPath;
			} else {
				// jugal derby url
//	        	url = "jdbc:derby:C:/Users/jugal/Desktop/TCS_Sprints/Final/tcs1/tcs;create=true";
				// bhargav derby url
				url = "jdbc:derby:D:\\Users\\2784290\\ElectricDB;create=true";
				// OFFICE URL
//				url = "jdbc:derby:D:/Users/2784103/tcswork/ems;create=true";
			}
			String username = "";
			String password = "";
			// New Method
			con = DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Failed to load Derby driver.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Failed to establish connection.");
			e.printStackTrace();
		}
		return con;
	}

	public static boolean checkTableExist(Connection con, String tableName) throws SQLException {
		DatabaseMetaData metaData = con.getMetaData();
		try (ResultSet rs = metaData.getTables(null, null, tableName.toUpperCase(), new String[] { "TABLE" })) {
			System.out.print("Cheking: Exists?" + tableName + "Table");
			return rs.next();
		} catch (Exception e) {
			System.out.println("Database Error: Cannot check if Table Exist");
			System.out.println(e.getMessage());
			return false;
		}
	}

	public static boolean checkTriggerExist(Connection con, String triggerName) throws SQLException {
		String query = "SELECT COUNT(*) AS trigger_count " + "FROM sys.systriggers " + "WHERE triggername = ?";
		try (PreparedStatement pstmt = con.prepareStatement(query)) {
			pstmt.setString(1, triggerName);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("trigger_count") > 0;
				}
			}
		}
		return false;
	}

	public static void createCustomerTable(Connection con) throws SQLException {
		if (checkTableExist(con, "Customer")) {
			System.out.println(": Already Exists");
			return;
		} else {
			try {
				int check = 0;
				Statement stmt = con.createStatement();
				String sql1 = "CREATE TABLE Customer(" + "CustomerID BIGINT NOT NULL PRIMARY KEY,"
						+ "Title VARCHAR(10) ," + "CustomerName VARCHAR(50) ," + "Email VARCHAR(100) UNIQUE,"
						+ "MobileNumber VARCHAR(10) UNIQUE," + "UserID VARCHAR(20) UNIQUE ," + "Password VARCHAR(70) ,"
						+ "Status VARCHAR(10) DEFAULT 'inactive')";
				check = stmt.executeUpdate(sql1);
				System.out.println("CustomerTableCreated Status: " + check);

			} catch (SQLException e) {
				System.out.println("Cannot create Customer Table");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

	}

	public static void createLoginTable(Connection con) throws SQLException {
		if (checkTableExist(con, "Login")) {
			System.out.println(": Already Exists");
			return;
		} else {
			try {
				int check = 0;
				Statement stmt = con.createStatement();
				String sql1 = "CREATE TABLE Login (" + "LoginID INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,"
						+ "CustomerID BIGINT NOT NULL," + "Email VARCHAR(50) UNIQUE NOT NULL,"
						+ "UserID VARCHAR(20) UNIQUE NOT NULL," + "Password VARCHAR(70) NOT NULL,"
						+ "UserType VARCHAR(10) NOT NULL DEFAULT 'user'," + "Status VARCHAR(10) DEFAULT 'active')";
				check = stmt.executeUpdate(sql1);
				System.out.println("LoginTableCreated Status: " + check);
			} catch (SQLException e) {
				System.out.println("Cannot create Login Table");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public static void createBillTable(Connection con) throws SQLException {
		if (checkTableExist(con, "Bill")) {
			System.out.println(": Already Exists");
			return;
		} else {
			try {
				int check = 0;
				Statement stmt = con.createStatement();
				String sql1 = "CREATE TABLE Bill (" + "BillID BIGINT PRIMARY KEY," + "CustomerID BIGINT NOT NULL,"
						+ "ConnectionType VARCHAR(20) DEFAULT 'residential' ," + "ConsumedUnits INT,"
						+ "DueAmount DECIMAL(10, 2) NOT NULL," + "PaymentAmount DECIMAL(10, 2) ," + "PaymentID BIGINT ,"
						+ "PaymentStatus VARCHAR(10) DEFAULT 'pending'," + "BillDate DATE NOT NULL,"
						+ "BillExpiryDate DATE NOT NULL)";
				check = stmt.executeUpdate(sql1);
				System.out.println("BillTableCreated Status: " + check);
			} catch (SQLException e) {
				System.out.println("Cannot create Bill Table");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public static void createComplaintTable(Connection con) throws SQLException {
		if (checkTableExist(con, "Complaint")) {
			System.out.println(": Already Exists");
			return;
		} else {
			try {
				int check = 0;
				Statement stmt = con.createStatement();
				String sql1 = "CREATE TABLE Complaint (" + "ComplaintID BIGINT PRIMARY KEY,"
						+ "CustomerID BIGINT NOT NULL," + "ComplaintType VARCHAR(50) NOT NULL,"
						+ "Category VARCHAR(50) NOT NULL," + "Landmark VARCHAR(100)," + "Problem CLOB NOT NULL,"
						+ "Address VARCHAR(200)," + "MobileNumber VARCHAR(10) NOT NULL,"
						+ "ComplaintDate DATE NOT NULL," + "ComplaintStatus VARCHAR(20) DEFAULT 'pending',"
						+ "FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE)";
				check = stmt.executeUpdate(sql1);
				System.out.println("ComplaintTableCreated Status: " + check);
			} catch (SQLException e) {
				System.out.println("Cannot create Complaint Table");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public static void createPaymentTable(Connection con) throws SQLException {
		if (checkTableExist(con, "Payment")) {
			System.out.println(": Already Exists");
			return;
		} else {
			try {
				int check = 0;
				Statement stmt = con.createStatement();
				String sql1 = "CREATE TABLE Payment (" + "PaymentID BIGINT PRIMARY KEY,"
						+ "BillID VARCHAR(100) NOT NULL," + "CustomerID BIGINT NOT NULL,"
						+ "PaymentDate DATE NOT NULL DEFAULT CURRENT_DATE," + "PaymentAmount DECIMAL(10, 2) NOT NULL,"
						+ "PaymentMode VARCHAR(20),"
						+ "FOREIGN KEY (CustomerID) REFERENCES Customer(CustomerID) ON DELETE CASCADE)";
				check = stmt.executeUpdate(sql1);
				System.out.println("PaymentTableCreated Status: " + check);
			} catch (SQLException e) {
				System.out.println("Cannot create Payment Table");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public static void createAfterCustomerUpdateTrigger(Connection con) throws SQLException {
		String triggerName = "after_customer_update";
		if (checkTriggerExist(con, triggerName)) {
			System.out.println(triggerName + " Trigger Already Exists");
			return;
		}
		try {
			Statement stmt = con.createStatement();
			String sql = "CREATE TRIGGER " + triggerName + " "
					+ "AFTER UPDATE OF Email, UserID, Password, Status ON Customer " + "REFERENCING NEW AS NEWROW "
					+ "FOR EACH ROW " + "UPDATE Login " + "SET Email = NEWROW.Email, " + "    UserID = NEWROW.UserID, "
					+ "    Password = NEWROW.Password, " + "Status = NEWROW.Status "
					+ "WHERE CustomerID = NEWROW.CustomerID";
			stmt.executeUpdate(sql);
			System.out.println(triggerName + " Trigger Created Successfully");
		} catch (SQLException e) {
			System.out.println("Error Creating " + triggerName + " Trigger");
			e.printStackTrace();
		}
	}

	public static void createMyDatabase(Connection con) {
		try {
			createCustomerTable(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			createLoginTable(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			createBillTable(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			createPaymentTable(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			createComplaintTable(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			createAfterCustomerUpdateTrigger(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
