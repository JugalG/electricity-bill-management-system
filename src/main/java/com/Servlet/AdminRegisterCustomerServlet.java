package com.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Dao.CustomerDao;
import com.Model.Customer;

/**
 * Servlet implementation class AdminRegisterCustomerServlet
 */
@WebServlet("/admin-register-customer")
public class AdminRegisterCustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminRegisterCustomerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if a session exists and don't create one if not there
		HttpSession session = request.getSession(false);

		try {
			if (session == null || session.getAttribute("userType") == null) {
				RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
				view.forward(request, response);
			} else {
				// Check if the session belongs to an admin and not a customer
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("admin")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);

					RequestDispatcher view = request.getRequestDispatcher("views/admin-add-customer.jsp");
					view.forward(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			String userType = String.valueOf(session.getAttribute("userType"));

			if (session != null && userType.equals("admin")) {
				System.out.println("Session caught on RegisterCustomer:ByAdmin");
			} else {
				RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
				view.forward(request, response);
			}

			try {
				String customerID = request.getParameter("consumer-number");
				String title = request.getParameter("title");
				String name = request.getParameter("name");
				String email = request.getParameter("email");
				String mobile = request.getParameter("mobile");

				long customerId = Long.parseLong(customerID);
				// Create a Customer object
				Customer customer = new Customer();

				customer.setCustomerID(customerId);
				customer.setTitle((title != null && !title.trim().isEmpty()) ? title : "");
				customer.setCustomerName((name != null && !name.trim().isEmpty()) ? name : "");
				customer.setEmail((email != null && !email.trim().isEmpty()) ? email : "");
				customer.setMobileNumber((mobile != null && !mobile.trim().isEmpty()) ? mobile : "");

				Customer isInserted = CustomerDao.insertCustomerAdmin(customer);

				request.setAttribute("showDialog", true);

				if (isInserted != null) {
					request.setAttribute("customer", isInserted);
					this.doGet(request, response);
				} else {
					request.setAttribute("customer", null);
					this.doGet(request, response);
				}
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("error", "An unexpected error occurred. Please try again.");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
			view.forward(request, response);
		}

	}
}
