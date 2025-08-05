package com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Model.Bill;
import com.Service.BillService;

/**
 * Servlet implementation class UserBillPageServlet
 */
@WebServlet("/mybills")
public class UserBillPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserBillPageServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if a session exists and don't create one if not there
		HttpSession session = request.getSession(false);
		try {
			if (session != null && session.getAttribute("userType") == null) {
				RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
				view.forward(request, response);
			} else {
				System.out.println(session.getAttribute("customerID"));
				// Check if the session belongs to a customer and not an admin
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("user")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					// Get the bills which will be displayed on the page
					String customerID = String.valueOf(session.getAttribute("customerID"));
					BillService billService = new BillService();
					List<Bill> bills = billService.getBillsByCustomerID(Long.parseLong(customerID));
					request.setAttribute("bills", bills);

					// Forward the request to the bill.jsp page
					RequestDispatcher view = request.getRequestDispatcher("views/customer-mybills.jsp");
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

}
