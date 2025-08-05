package com.Servlet.api;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Service.CustomerService;

@WebServlet("/api/customer/new")
public class GetUniqueCustomerIdAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetUniqueCustomerIdAPI() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response type to JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		Random random = new Random();
		long maxcustID = 9999999999999L, mincustID = 2000000000000L;

		long CustomerID = mincustID + (long) (random.nextDouble() * (maxcustID - mincustID + 1));
		// Check if the customer ID is unique
		CustomerService customerService = new CustomerService();
		while (customerService.checkCustomerID(CustomerID)) {
			CustomerID = mincustID + (long) (random.nextDouble() * (maxcustID - mincustID + 1));
		}

		// Send the response
		response.getWriter().write("{\"CustomerID\": " + CustomerID + "}");

		// Close the response
		response.getWriter().close();
	}

}
