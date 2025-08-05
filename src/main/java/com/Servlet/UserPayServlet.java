package com.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PayServlet
 */
@WebServlet("/pay")
public class UserPayServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPayServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		response.sendRedirect("mybills");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1.
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0.
		response.setDateHeader("Expires", 0); // Proxies.

		long customerID = Long.parseLong(String.valueOf(session.getAttribute("SessioncustomerID123")));
		if(customerID>0) {
			request.setAttribute("customerID",customerID);
			System.out.println("Customer ID found on UserPayServlet: "+ customerID);
		}else System.out.println("Customer ID not found on UserPayServlet");
		
		// Show all the parameters
		String totalPayable = request.getParameter("total-payable");
		String paymentOption = request.getParameter("paymentoption");

		if (session != null) {
			List<Integer> billIdsSession = (List<Integer>) session.getAttribute("BILLIDS");

			System.out.println("Session Page 3" + billIdsSession);
		}

		System.out.println("Total Payable: " + totalPayable);
		System.out.println("Payment Option: " + paymentOption);

		session.setAttribute("totalPayable", totalPayable);
		session.setAttribute("paymentOption", paymentOption);

		request.getRequestDispatcher("views/customer-payment.jsp").forward(request, response);
	}
}
