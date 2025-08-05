package com.Servlet;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Model.Customer;
import com.Service.CustomerService;
import com.Util.HashUtil;

/**
 * Servlet implementation class UserUpdateServlet
 */
@WebServlet("/updateuser")
public class UserUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public UserUpdateServlet() {
		super();
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if a session exists and don't create one if not there
		HttpSession session = request.getSession(false);

		try {
//			System.out.println("User update Servlet: UserType = "+session.getAttribute("userType"));
			if (session.getAttribute("userType") == null) {
				RequestDispatcher view = request.getRequestDispatcher("views/errors/401.jsp");
				view.forward(request, response);
			} else {
				// Check if the session belongs to a customer and not an admin
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("user")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);

					String customerID = String.valueOf(session.getAttribute("customerID"));
					if (customerID != null) {
						try {
							CustomerService customerservice = new CustomerService();
							Customer cust = customerservice.fetchUserDetailById(customerID);
//							System.out.println("User Update Servlet DO GET, fetched Customer ID: "+cust.toString());
							request.setAttribute("cust", cust);

							RequestDispatcher view = request.getRequestDispatcher("views/customer-update-details.jsp");
							view.forward(request, response);
						} catch (Exception e) {
							e.printStackTrace();
							RequestDispatcher view = request.getRequestDispatcher("views/403.jsp");
							view.forward(request, response);
						}
					}
				} else {
					RequestDispatcher view = request.getRequestDispatcher("views/403.jsp");
					view.forward(request, response);
				}
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = req.getSession(false);

		String typeAction = String.valueOf(req.getParameter("type"));

		if (typeAction.equals("details")) {
			String title = (String) req.getParameter("title");
			String name = (String) req.getParameter("name");
			String email = (String) req.getParameter("email");
			String userId = (String) req.getParameter("userid");
			String mobile = String.valueOf(req.getParameter("mobile"));
			String password = (String) req.getParameter("auth-password");

			String hashedPwd = "";

			try {
				hashedPwd = HashUtil.hashPassword(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CustomerService customerservice = new CustomerService();
			long customerID = Long.parseLong(String.valueOf(session.getAttribute("customerID")));
			boolean passwordValidated = customerservice.validateHashedPassword(customerID, hashedPwd);

			if (!passwordValidated) {
				req.setAttribute("showDialog", true);
				req.setAttribute("reason", "The password you entered is incorrect");
				this.doGet(req, resp);
				return;
			}

			Customer customer = customerservice.validateAndUpdateCustomer(customerID, title, name, email, mobile,
					userId, hashedPwd);

			if (customer == null) {
				req.setAttribute("showDialog", true);
				req.setAttribute("reason", "Invalid Data");
				this.doGet(req, resp);
				return;
			}

			req.setAttribute("customer", customer);
			session.setAttribute("userId", customer.getUserId());
			req.setAttribute("showDialog", true);
			this.doGet(req, resp);

		} else if (typeAction.equals("password")) {
			String oldPassword = (String) req.getParameter("old-password");
			String newPassword = (String) req.getParameter("new-password");
			String newConfirmPassword = (String) req.getParameter("new-confirm-password");

			if (!newPassword.equals(newConfirmPassword)) {
				req.setAttribute("showPasswordDialog", true);
				req.setAttribute("passwordSuccess", false);
				req.setAttribute("passwordReason", "Passwords do not match!");
				this.doGet(req, resp);
				return;
			}

			String hashedOldPwd = "";
			String hashedNewPwd = "";
			try {
				hashedOldPwd = HashUtil.hashPassword(oldPassword);
				hashedNewPwd = HashUtil.hashPassword(newPassword);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}

			CustomerService customerservice = new CustomerService();
			long customerID = Long.parseLong(String.valueOf(session.getAttribute("customerID")));

			boolean passwordValidated = customerservice.validateHashedPassword(customerID, hashedOldPwd);

			if (!passwordValidated) {
				req.setAttribute("showPasswordDialog", true);
				req.setAttribute("passwordSuccess", false);
				req.setAttribute("passwordReason", "The old password you entered is incorrect!");
				this.doGet(req, resp);
				return;
			}

			boolean passwordUpdated = customerservice.updatePassword(customerID, hashedNewPwd);
			if (!passwordUpdated) {
				req.setAttribute("showPasswordDialog", true);
				req.setAttribute("passwordSuccess", false);
				req.setAttribute("passwordReason", "Password could not be updated!");
				this.doGet(req, resp);
				return;
			}

			req.setAttribute("showPasswordDialog", true);
			req.setAttribute("passwordSuccess", true);
			req.setAttribute("passwordReason", "Password updated successfully!");

			this.doGet(req, resp);
		} else if (typeAction.equals("deactivate")) {
			String password = (String) req.getParameter("deactivate-password");

			String hashedPwd = "";

			try {
				System.out.println("Inside Update User Post Meythod for Deactivate Account");
				hashedPwd = HashUtil.hashPassword(password);
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			CustomerService customerservice = new CustomerService();
			long customerID = Long.parseLong(String.valueOf(session.getAttribute("customerID")));

			boolean passwordValidated = customerservice.validateHashedPassword(customerID, hashedPwd);

			if (!passwordValidated) {
				req.setAttribute("showDeactivateDialog", true);
				req.setAttribute("deactivateReason", "The password you entered is incorrect");
				this.doGet(req, resp);
				return;
			}

			boolean deactivated = customerservice.deactivateCustomer(customerID);

			if (!deactivated) {
				req.setAttribute("showDeactivateDialog", true);
				req.setAttribute("deactivateReason", "Account could not be deactivated!");
				this.doGet(req, resp);
				return;
			}

//			session.removeAttribute("userType");
			req.setAttribute("showDeactivateDialog", true);
			req.setAttribute("deactivateReason", "Successfully Deactivated!");
			session.invalidate();
			this.doGet(req, resp);
		} else {
			req.setAttribute("showDeactivateDialog", true);
			req.setAttribute("deactivateReason", "Invalid Action!");

			this.doGet(req, resp);
		}
	}

}
