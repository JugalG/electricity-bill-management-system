package com.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Dao.ComplaintDao;
import com.Model.Complaint;
import com.Util.MyDatabase;

/**
 * Servlet implementation class UserRegisterComplaintPageServlet
 */
@WebServlet("/register-complaint")
public class UserRegisterComplaintPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		// Set request/response encoding
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// Retrieve form parameters
		String CustomerID = request.getParameter("consumer-number");
		String complaintType = request.getParameter("complaint-type");
		String Category = request.getParameter("complaint-category");
		String landmark = request.getParameter("landmark");
		String problemDescription = request.getParameter("problem-description");
		String address = request.getParameter("address");
		String mobileNumber = request.getParameter("mobile");

		// Validate data
		Complaint complaint = new Complaint(Long.parseLong(CustomerID), complaintType, Category, landmark,
				problemDescription, address, mobileNumber);
		// adding to database
		Complaint returnedComplaint = ComplaintDao.addComplaint(MyDatabase.getConnection(), complaint);

		// Forward to confirmation page or back to complaint form
		if (returnedComplaint != null) {
			request.setAttribute("complaint", returnedComplaint);
			request.setAttribute("showDialog", true);

			this.doGet(request, response);

		} else {
			request.setAttribute("reason", "Invalid Data");
			request.setAttribute("showDialog", true);
			this.doGet(request, response);
			return;
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Check if a session exists and don't create one if not there
		HttpSession session = request.getSession();

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

					// If yes, send the home page back with session in attribute
					RequestDispatcher view = request.getRequestDispatcher("views/customer-register-complaint.jsp");
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
