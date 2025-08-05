package com.Servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PayBillsServlet
 */
@WebServlet("/paybills")
public class UserPayBillsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPayBillsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("mybills");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.

		Map<String, String[]> parameterMap = request.getParameterMap();

		ArrayList<String> billIDs = new ArrayList<String>();

		// Iterate through the map to get everthing that starts with 'bill' and add it
		for (String parameterName : parameterMap.keySet()) {
			if (parameterName.startsWith("bill")) {
				billIDs.add(parameterName.substring(4));
			}
		}
		Collections.sort(billIDs);

		request.setAttribute("totalbill", request.getParameter("totalbill"));

		request.setAttribute("BILLIDS", billIDs);

		HttpSession session = request.getSession(false);
		if (session != null) {
			long custID = Long.parseLong(String.valueOf(session.getAttribute("SessioncustomerID123")));
			if (custID > 0) {
				request.setAttribute("customerID", custID);
			} else
				System.out.println("Customer ID not found on UserPayServlet");
			session.setAttribute("BILLIDS", billIDs);
			System.out.println();
		} else
			System.out.println("Session could not create: Bill IDS not passed to page 3");

		request.getRequestDispatcher("views/customer-paybills.jsp").forward(request, response);
	}

}
