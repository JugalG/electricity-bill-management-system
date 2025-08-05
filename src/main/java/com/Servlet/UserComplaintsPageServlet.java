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

import com.Model.Complaint;
import com.Service.ComplaintService;

@WebServlet("/mycomplaints")
public class UserComplaintsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserComplaintsPageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if a session exists and don't create one if not there
		HttpSession session = request.getSession(false);

		try {
			if (session.getAttribute("userType") == null) {
				RequestDispatcher view = request.getRequestDispatcher("views/401.jsp");
				view.forward(request, response);
			} else {
				// Check if the session belongs to a customer and not an admin
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("user")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					// Get the complaints which will be displayed on the page
					long customerID = Long.parseLong(String.valueOf(session.getAttribute("customerID")));
					List<Complaint> complaints = ComplaintService.fetchUserComplaints(customerID);
					request.setAttribute("complaints", complaints);

					RequestDispatcher view = request.getRequestDispatcher("views/customer-mycomplaints.jsp");
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		resp.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		resp.setDateHeader("Expires", 0); // Proxies.

		String statusFilter = req.getParameter("statusfilter");
		String complaintIDString = req.getParameter("complaintID");

		// Check if a session exists and don't create one if not there
		HttpSession session = req.getSession(false);

		// Default to "all" if no status filter is provided
		if (statusFilter == null) {
			statusFilter = "all";
		}

		// Get the complaints which will be displayed on the page
		ComplaintService complaintService = new ComplaintService();
		long customerID = Long.parseLong(String.valueOf(session.getAttribute("customerID")));

		List<Complaint> complaints = complaintService.fetchFilteredUserComplaints(customerID, statusFilter,
				complaintIDString);

		req.setAttribute("complaints", complaints);
		req.setAttribute("selectedStatusFilter", statusFilter);
		req.setAttribute("selectedComplaintID", complaintIDString);

		RequestDispatcher view = req.getRequestDispatcher("views/customer-mycomplaints.jsp");
		view.forward(req, resp);
	}
}
