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

@WebServlet("/complaints")
public class AdminComplaintsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdminComplaintsPageServlet() {
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

				// Check if the session belongs to an admin and not a customer
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("admin")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					// Since this is a GET request, we send all complaints to the view
					ComplaintService complaintService = new ComplaintService();
					List<Complaint> complaints = complaintService.fetchAllComplaints();
					request.setAttribute("complaints", complaints);

					RequestDispatcher view = request.getRequestDispatcher("views/admin-complaints.jsp");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Set cache-control headers (applies to all responses)
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0
		response.setDateHeader("Expires", 0); // Proxies

		// Retrieve filter parameters from the request
		String statusFilter = request.getParameter("statusfilter") != null ? request.getParameter("statusfilter")
				: "all";
		String complaintIDString = request.getParameter("complaintID") != null ? request.getParameter("complaintID")
				: "";
		String complaintType = request.getParameter("complaint-type") != null ? request.getParameter("complaint-type")
				: "";
		String category = request.getParameter("complaint-category") != null
				? request.getParameter("complaint-category")
				: "";

		// Check for session and user type
		HttpSession session = request.getSession(false); // Don't create a session if none exists
		if (session == null || session.getAttribute("userType") == null) {
			// No valid session or user type, redirect to 401
			request.getRequestDispatcher("views/401.jsp").forward(request, response);
			return;
		}

		// Verify the user is an admin
		String userType = String.valueOf(session.getAttribute("userType"));
		if (!"admin".equals(userType)) {
			// Not authorized for this page, redirect to 403
			request.getRequestDispatcher("views/403.jsp").forward(request, response);
			return;
		}

		try {
			// Fetch filtered complaints using ComplaintService
			ComplaintService complaintService = new ComplaintService();
			List<Complaint> complaints = complaintService.fetchFilteredComplaints(statusFilter, complaintIDString,
					complaintType, category);

			// Attach complaints and filter parameters to the request attributes
			request.setAttribute("complaints", complaints);
			request.setAttribute("selectedStatusFilter", statusFilter);
			request.setAttribute("selectedComplaintID", complaintIDString);
			request.setAttribute("selectedComplaintType", complaintType);
			request.setAttribute("selectedComplaintCategory", category);

			// Forward to admin-complaints.jsp
			request.getRequestDispatcher("views/admin-complaints.jsp").forward(request, response);

		} catch (Exception e) {
			e.printStackTrace();
			// Handle exceptions gracefully by redirecting to an error page
			request.getRequestDispatcher("views/500.jsp").forward(request, response);
		}
	}

}
