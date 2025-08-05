package com.Util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Model.Login;
import com.Service.LoginService;

public class AdminConsole {

	public static void main(String[] args) {
		// Step 1: Attempt to connect to the database
		Connection conn = null;
		try {
			conn = MyDatabase.getConnection();
			System.out.println("Database connection: Successful");
		} catch (Exception e) {
			System.err.println("Failed to connect to the database: " + e.getMessage());
			return; // Exit if connection fails
		}

		// Step 2: Attempt to create database tables/triggers
		try {
			MyDatabase.createMyDatabase(conn);
			System.out.println("Database initialization: Completed");
			conn.close();
		} catch (Exception e) {
			System.err.println("Failed to initialize database: " + e.getMessage());
			return; // Exit if initialization fails
		}

		// Step 3: Launch the menu-driven program
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("=======================");
			System.out.println(" Admin Console Menu ");
			System.out.println("=======================");
			System.out.println("1. Request Admin ID");
			System.out.println("2. Show List of All Admins");
			System.out.println("3. Show Admin by UserID");
			System.out.println("4. Exit");
			System.out.print("Enter your choice: ");

			int choice;
			try {
				choice = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.err.println("Invalid input. Please enter a number between 1 and 4.");
				continue;
			}

			switch (choice) {
			case 1:
				requestAdminID(scanner);
				break;
			case 2:
				showAllAdmins();
				break;
			case 3:
				showAdminByID(scanner);
				break;
			case 4:
				System.out.println("Exiting Admin Console. Goodbye!");
				scanner.close();
				return;
			default:
				System.err.println("Invalid choice. Please select a number between 1 and 4.");
			}
		}
	}
	// Function for requesting an Admin ID
	private static void requestAdminID(Scanner scanner) {
	    System.out.println("Requesting a new Admin ID...");
	    LoginService loginService = new LoginService();
	    String newAdminId;

	    // Step 1: Get the next Admin ID
	    try {
	        newAdminId = loginService.getNextAdminUserId();
	        System.out.println("Your new Admin ID: " + newAdminId);
	    } catch (Exception e) {
	        System.err.println("Failed to generate Admin ID: " + e.getMessage());
	        return;
	    }

	    // Step 2: Get a unique Employee ID
	    long employeeID;
	    try {
	        employeeID = loginService.getUniqueEmployeeID();
	        System.out.println("Your Employee ID: " + employeeID);
	    } catch (Exception e) {
	        System.err.println("Failed to generate Employee ID: " + e.getMessage());
	        return;
	    }

	    // Step 3: Input validation with exit option
	    String email = getInputWithValidation(scanner, 
	        "Enter your email (or type 'exit' to return to the main menu): ", 
	        input -> input.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"),
	        "Invalid email format. Please enter a valid email address.");

	    if (email.equalsIgnoreCase("exit")) return;

	    // Check if email already exists
	    boolean emailExists;
	    try {
	        emailExists = loginService.checkIfEmailExists(email);
	        if (emailExists) {
	            System.err.println("Email ID is already registered! Returning to the main menu.");
	            return;
	        }
	    } catch (Exception e) {
	        System.err.println("Error checking email existence: " + e.getMessage());
	        return;
	    }

	    // Step 4: Password input with constraints
	    String password = getInputWithValidation(scanner, 
	        "Enter your password (or type 'exit' to return to the main menu): ",
	        input -> input.length() >= 8 && input.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).+$"),
	        "Password must be at least 8 characters long and include uppercase, lowercase, a digit, and a special character.");

	    if (password.equalsIgnoreCase("exit")) return;

	    String confirmPassword = getInputWithValidation(scanner,
	        "Confirm your password (or type 'exit' to return to the main menu): ",
	        input -> input.equals(password),
	        "Passwords do not match. Please try again.");

	    if (confirmPassword.equalsIgnoreCase("exit")) return;

	    // Step 5: Hash the password
	    String hashedPassword;
	    try {
	        hashedPassword = HashUtil.hashPassword(password);
	    } catch (Exception e) {
	        System.err.println("Error hashing password: " + e.getMessage());
	        return;
	    }

	    // Step 6: Register admin
	    String userType = "admin";
	    Login admin = new Login(employeeID, email, newAdminId, hashedPassword, userType, "active");
	    try {
	        boolean success = loginService.registerAdmin(admin);
	        if (success) {
	            System.out.println("Admin registration successful! Welcome, Admin " + newAdminId);
	        } else {
	            System.err.println("Failed to register admin. Please try again later.");
	        }
	    } catch (Exception e) {
	        System.err.println("Error registering admin: " + e.getMessage());
	    }
	}

	// Utility function for input with validation and exit option
	private static String getInputWithValidation(Scanner scanner, String prompt, java.util.function.Predicate<String> validator, String errorMessage) {
	    while (true) {
	        System.out.print(prompt);
	        String input = scanner.nextLine().trim();
	        if (input.equalsIgnoreCase("exit")) {
	            return "exit";
	        }
	        if (validator.test(input)) {
	            return input;
	        } else {
	            System.err.println(errorMessage);
	        }
	    }
	}


//	// Function for requesting an Admin ID
//	private static void requestAdminID(Scanner scanner) {
//		System.out.println("Requesting a new Admin ID...");
//
//		// Step 1: Get the next Admin ID
//		LoginService loginService = new LoginService();
//		String newAdminId;
//		try {
//			newAdminId = loginService.getNextAdminUserId();
//			System.out.println("Your new Admin ID: " + newAdminId);
//		} catch (Exception e) {
//			System.err.println("Failed to generate Admin ID: " + e.getMessage());
//			return;
//		}
//
//		// Step 2: Get a unique Employee ID
//		long employeeID;
//		try {
//			employeeID = loginService.getUniqueEmployeeID();
//			System.out.println("Your Employee ID: " + employeeID);
//		} catch (Exception e) {
//			System.err.println("Failed to generate Employee ID: " + e.getMessage());
//			return;
//		}
//
//		// Step 3: Prompt user for registration details
//		System.out.print("Enter your email: ");
//		String email = scanner.nextLine();
//
//		// Email constraint
//		if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
//			System.err.println("Invalid email format. Please enter a valid email address.");
//			return;
//		}
//
//		// Check if email already exists
//		boolean emailExists = loginService.checkIfEmailExists(email);
//		if (emailExists) {
//			System.err.println("Email ID is already registered!");
//			return;
//		}
//
//		System.out.print("Enter your password: ");
//		String password = scanner.nextLine();
//
//		// Password constraint
//		if (password.length() < 8) {
//			System.err.println("Password must be at least 8 characters long.");
//			return;
//		}
//
//		String passwordTest = "^(?=(.*[A-Z]))(?=(.*[a-z]))(?=(.*\\d))(?=(.*[@#$%^&+=!])).+$";
//
//		if (!password.matches(passwordTest)) {
//			System.err.println(
//					"Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
//			return;
//		}
//
//		System.out.print("Confirm your password: ");
//		String confirmPassword = scanner.nextLine();
//
//		// Password match constraint
//		if (!password.equals(confirmPassword)) {
//			System.err.println("Passwords do not match. Please try again.");
//			return;
//		}
//
//		// Step 4: Hash the password
//		String hashedPassword;
//		try {
//			hashedPassword = HashUtil.hashPassword(password);
//		} catch (Exception e) {
//			System.err.println("Error hashing password: " + e.getMessage());
//			return;
//		}
//
//		// Step 5: Set admin
//		String userType = "admin";
//
//		// Step 6: Create Login object and register admin
//		Login admin = new Login(employeeID, email, newAdminId, hashedPassword, userType, "active");
//		try {
//			boolean success = loginService.registerAdmin(admin);
//			if (success) {
//				System.out.println("Admin registration successful! Welcome, Admin " + newAdminId);
//			} else {
//				System.err.println("Failed to register admin. Please try again later.");
//			}
//		} catch (Exception e) {
//			System.err.println("Error registering admin: " + e.getMessage());
//		}
//	}

	// Placeholder for showing all Admins
	private static void showAllAdmins() {
		LoginService loginService = new LoginService();
		List<Login> admins = new ArrayList<>();
		try {
			admins = loginService.getAllAdmins();
			System.out.println("List of all Admins:");
			for (Login admin : admins) {
				System.out.println(admin.toStringWithoutPassword());
			}
		} catch (Exception e) {
			System.err.println("Failed to retrieve list of Admins: " + e.getMessage());
		}
	}

	// Placeholder for showing Admin by ID
	private static void showAdminByID(Scanner scanner) {
		System.out.print("Enter Admin ID: ");
		String adminId = scanner.nextLine();

		LoginService loginService = new LoginService();
		Login admin;
		try {
			admin = loginService.getAdminById(adminId);
			if (admin != null) {
				System.out.println("Admin details:");
				System.out.println(admin.toStringWithoutPassword());
			} else {
				System.err.println("Admin ID not found.");
			}
		} catch (Exception e) {
			System.err.println("Failed to retrieve Admin details: " + e.getMessage());
		}
	}
}
