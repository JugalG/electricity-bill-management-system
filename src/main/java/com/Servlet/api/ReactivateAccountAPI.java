package com.Servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Service.CustomerService;

/**
 * Servlet implementation class ReactivateAccountAPI
 */

@WebServlet("/api/customer/reactivate")
public class ReactivateAccountAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReactivateAccountAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Set response type to JSON
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		// Get the customerID from the request
		String customerID = String.valueOf(req.getParameter("customerID"));

		// Call the service to reactivate the account
		CustomerService customerService = new CustomerService();

		// Reactivate the account
		boolean result = customerService.reactivateAccount(customerID);

		// Send the response
		if (result) {
			resp.setStatus(HttpServletResponse.SC_OK);
			resp.getWriter().write("{\"success\": true}");
		} else {
			resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			resp.getWriter().write("{\"success\": false}");
		}
	}

}
