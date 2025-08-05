package com.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Service.BillService;
import com.Service.ComplaintService;
import com.Service.CustomerService;

/**
 * Servlet implementation class AdminHomePageServlet
 */
@WebServlet("/admin-home")
public class AdminHomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminHomePageServlet() {
		super();
		// TODO Auto-generated constructor stub
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
			if (session == null || session.getAttribute("userType") == null) {
				RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
				view.forward(request, response);
			} else {
				// Check if the session belongs to an admin and not a customer
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("admin")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					CustomerService customerservice = new CustomerService();
					int totalUsers = customerservice.getCountOfCustomers();

					BillService billservice = new BillService();
					int totalDueBills = billservice.getCountOfBills("pending");

					ComplaintService complaintservice = new ComplaintService();
					int pendingComplaints = complaintservice.getCountOfComplaints();

					// String totalDueBills = "4";
					// String pendingComplaints = "9";

					request.setAttribute("totalUsers", totalUsers);
					request.setAttribute("totalDueBills", totalDueBills);
					request.setAttribute("pendingComplaints", pendingComplaints);

					RequestDispatcher view = request.getRequestDispatcher("views/admin-home.jsp");
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
