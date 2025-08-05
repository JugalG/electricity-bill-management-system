package com.Servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Dao.BillDao;
import com.Model.Bill;
import com.Service.BillService;
import com.Util.MyDatabase;

@WebServlet("/bills")
public class AdminBillsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminBillsPageServlet() {
		super();
	}

	// In AdminBillsPageServlet.java
	/**
	 * 
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		try {
			if (session.getAttribute("userType") == null) {
				RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
				view.forward(request, response);
			} else {
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("admin")) {
					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					// Call the BillDao to fetch unpaid bills
					BillDao billDao = new BillDao();
					try (Connection con = MyDatabase.getConnection()) {
						List<Bill> unpaidBills = billDao.getAllUnpaidBills(con);
						// Set unpaidBills as request attribute
						request.setAttribute("bills", unpaidBills); 
					} catch (SQLException e) {
						e.printStackTrace();
						System.out.println("Admin bill not loaded");
					}

					RequestDispatcher view = request.getRequestDispatcher("views/admin-bills.jsp");
					view.forward(request, response);
				} else {
					RequestDispatcher view = request.getRequestDispatcher("views/403.jsp");
					view.forward(request, response);
				}
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
			view.forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		resp.setDateHeader("Expires", 0); // Proxies.

		String statusFilter = req.getParameter("statusfilter");
		String billIDString = req.getParameter("billID");

		// Check if a session exists and don't create one if not there
		HttpSession session = req.getSession(false);

		if (session == null || session.getAttribute("userType") == null) {
			// No valid session or user type, redirect to 401
			req.getRequestDispatcher("views/401.jsp").forward(req, resp);
			return;
		}

		// Verify the user is an admin
		String userType = String.valueOf(session.getAttribute("userType"));
		if (!"admin".equals(userType)) {
			// Not authorized for this page, redirect to 403
			req.getRequestDispatcher("views/403.jsp").forward(req, resp);
		}

		// Default to "unpaid" if no status filter is provided
		if (statusFilter == null) {
			statusFilter = "unpaid";
		}

		// Get the complaints which will be displayed on the page
		BillService billService = new BillService();

		List<Bill> bills = billService.fetchFilteredBills(statusFilter, billIDString);

		req.setAttribute("bills", bills);
		req.setAttribute("selectedStatusFilter", statusFilter);
		req.setAttribute("selectedBillID", billIDString);

		RequestDispatcher view = req.getRequestDispatcher("views/admin-bills.jsp");
		view.forward(req, resp);
	}
}
