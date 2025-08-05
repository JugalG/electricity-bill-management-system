package com.Service;

import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Dao.CustomerDao;
import com.Dao.LoginDao;
import com.Model.Customer;
import com.Util.HashUtil;
import com.Util.Tuple;

public class CustomerService {
	public Tuple<String, Customer> validateAndRegisterCustomer(long CustomerID, String Title, String CustomerName,
			String Email, String MobileNumber, String userID, String pwd, String confirmPwd) {

		boolean flag = true;
		String reason = null;
		// title
		String[] titles = new String[] { "Mx.", "Mr.", "Mrs.", "Miss.", "Dr.", "Prof.", "Rev." };
		if (!java.util.Arrays.asList(titles).contains(Title)) {
			reason = new String("Enter valid title");
			flag = false;
		}
		// CustomerName
		final String nameRegex = "^(?!\\s*$)[A-Za-z]+(?: [A-Za-z]+)*$";
		if (CustomerName.length() > 50 || !CustomerName.matches(nameRegex)) {
			reason = new String("Name must contain only alphabets and spaces and should be less than 50 characters");
			flag = false;
		}
		// email
		final String emailRegex = "^[A-Za-z0-9-._]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
		String finalEmail = "";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(Email);
		if (matcher.matches()) {
			finalEmail = Email;
		} else {
			reason = new String("Enter valid email");
			flag = false;
		}
		// Check for unique Email
		if (LoginDao.checkIfEmailExists(Email)) {
			reason = new String("Email ID is already registered!");
			flag = false;
		}
		// Mobile number
		final String contactRegex = "^[6-9](?!(\\d)\\1{4})[0-9]{9}$";
		Pattern patternContact = Pattern.compile(contactRegex);

		Matcher matcherContact = patternContact.matcher(MobileNumber.toString());
		if (!(MobileNumber.toString().replaceAll("\\s", "").length() == 10) && !matcherContact.matches()) {
			reason = new String("Enter valid mobile number");
			flag = false;
		}
		// userId
		final String userIdRegex = "^[A-Za-z0-9]+$";
		Pattern patternUserId = Pattern.compile(userIdRegex);
		Matcher matcherUserId = patternUserId.matcher(userID.toString());
		if (!matcherUserId.matches()) {
			reason = new String("Enter valid userId");
			flag = false;
		}
		// password
		final String passwordRegex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=!])(?=.{5,}).*$";

		if (!(pwd.length() < 16 && pwd.toString().matches(passwordRegex))) {
			reason = new String("Enter valid password");
			flag = false;
		}
		// confirm pwd
		if (!(confirmPwd.length() < 16 && confirmPwd.toString().matches(passwordRegex))) {
			reason = new String("Enter valid confirm password");
			flag = false;
		}
		System.out.println("Registation password: " + pwd);
		String hashedPwd = "";
		if (!pwd.equals(confirmPwd)) {
			reason = new String("Passwords do not match");
			flag = false;
		} else {
			try {
				hashedPwd = HashUtil.hashPassword(pwd.trim());
				System.out.println("Hashed password: " + hashedPwd);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				flag = false;
			}

		}

		Tuple<String, Customer> result = null;

		if (!flag) {
			result = new Tuple<String, Customer>(reason, null);
			return result;
		} else {
			Customer cust = new Customer(CustomerID, Title, CustomerName, finalEmail, MobileNumber, userID, hashedPwd);
			System.out.println(cust.toString());

			Customer registeredCust = CustomerDao.insertCustomer(cust);

			if (registeredCust != null) {
				result = new Tuple<String, Customer>(reason, cust);
				return result;
			} else {
				result = new Tuple<String, Customer>("Database error: Registration Failed", null);
				return result;
			}
		}

	}

	// Check if the username is available
	public boolean checkUserId(String userID) {
		final String userIdRegex = "^[A-Za-z0-9]+$";
		Pattern patternUserId = Pattern.compile(userIdRegex);
		Matcher matcherUserId = patternUserId.matcher(userID.toString());

		if (!matcherUserId.matches()) {
			System.out.println("Enter valid userId");
			return false;
		}
		if (userID.length() < 5 || userID.length() > 20) {
			System.out.println("Enter valid userId");
			return false;
		}

		return CustomerDao.checkUserId(userID);
	}

	public boolean checkCustomerID(long customerID) {
		Customer customer = CustomerDao.searchByID(customerID);
		if (customer == null) {
			return false;
		} else {
			return true;
		}
	}

	public int getCountOfCustomers() {
		return CustomerDao.fetchCountOfUsers();
	}

	public Customer fetchUserDetailById(String customerID) {
		return CustomerDao.searchByID(Long.parseLong(customerID));
	}

	public Customer validateAndUpdateCustomer(long customerID, String title, String name, String email, String mobile,
			String userId, String hashedPwd) {
		boolean flag = true;
		// email
		final String emailRegex = "^[A-Za-z0-9-._]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}$";
		String finalEmail = "";
		Pattern pattern = Pattern.compile(emailRegex);
		Matcher matcher = pattern.matcher(email);

		if (matcher.matches()) {
			finalEmail = email;
		} else {
			System.out.println("Enter valid email");
			flag = false;
		}
		// Mobile number
		final String contactRegex = "[0-9]";
		Pattern patternContact = Pattern.compile(contactRegex);

		Matcher matcherContact = patternContact.matcher(mobile.toString());
		if (!(mobile.toString().replaceAll("\\s", "").length() == 10) && !matcherContact.matches()) {
			System.out.println("Enter valid mobile number");
			flag = false;
		}

		if (!flag) {
			return null;
		}

		Customer cust = new Customer(customerID, title, name, finalEmail, mobile, userId, hashedPwd);
		System.out.println(cust.toString());
		try {
			return ((CustomerDao.updateCustomerDetail(cust)) ? cust : null);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return cust;
	}

	public boolean validateHashedPassword(long customerID, String hashedPwd) {
		String tablePassword = CustomerDao.getCurrentHashedPassword(customerID);
		return (tablePassword.equals(hashedPwd));
	}

	public boolean updatePassword(long customerID, String hashedNewPwd) {
		return CustomerDao.updatePassword(customerID, hashedNewPwd);
	}

	public boolean deactivateCustomer(long customerID) {
		return CustomerDao.deactivateCustomer(customerID);
	}

	public boolean reactivateAccount(String customerID) {
		return CustomerDao.reactivateAccount(Long.parseLong(customerID));
	}
}
