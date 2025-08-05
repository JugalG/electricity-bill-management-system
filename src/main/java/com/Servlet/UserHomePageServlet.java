package com.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserHomePageServlet
 */
@WebServlet("/home")
public class UserHomePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserHomePageServlet() {
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
				// Check if the session belongs to a customer and not an admin
				String userType = String.valueOf(session.getAttribute("userType"));
//				session.setAttribute("SessionCustomerID", request.getAttribute("customerID"));
				if (userType.equals("user")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
					response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
					response.setDateHeader("Expires", 0); // Proxies.

					// If yes, send the home page back with session in attribute
					RequestDispatcher view = request.getRequestDispatcher("views/customer-home.jsp");
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
