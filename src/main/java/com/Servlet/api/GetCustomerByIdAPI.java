package com.Servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Dao.CustomerDao;
import com.Model.Customer;

@WebServlet("/api/customer")
public class GetCustomerByIdAPI extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response type to JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String customerIDParam = request.getParameter("customerID");

		// Validate the customerID parameter
		if ((customerIDParam == null || customerIDParam.isEmpty())) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"exists\": false, \"message\": \"Customer ID is required.\"}");
			return;

		}

		try {
			// Parse the customerID to a long
			long customerID = Long.parseLong(customerIDParam);

			// Retrieve customer details using CustomerDao
			Customer customer = CustomerDao.searchByID(customerID);

			if (customer != null) {
				// Manually build the JSON response
				String jsonResponse = String.format(
						"{ \"customerID\": %d, \"title\": \"%s\", \"customerName\": \"%s\", \"email\": \"%s\", \"mobileNumber\": \"%s\" }",
						customer.getCustomerID(), customer.getTitle() == null ? "" : escapeJson(customer.getTitle()),
						customer.getCustomerName() == null ? "" : escapeJson(customer.getCustomerName()),
						customer.getEmail() == null ? "" : escapeJson(customer.getEmail()),
						customer.getMobileNumber() == null ? "" : escapeJson(customer.getMobileNumber()));

				response.getWriter().write(jsonResponse);
			} else {
				// Customer not found
				response.getWriter().write("{\"exists\": false, \"message\": \"Customer not found.\"}");
			}
		} catch (NumberFormatException e) {
			// Handle invalid customerID format
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			response.getWriter().write("{\"exists\": false, \"message\": \"Invalid Customer ID format.\"}");
		} catch (Exception e) {
			// Handle unexpected errors
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"exists\": false, \"message\": \"An unexpected error occurred.\"}");
			e.printStackTrace();
		}
	}

	// Helper method to escape JSON special characters
	private String escapeJson(String input) {
		return input.replace("\"", "\\\"").replace("\\", "\\\\").replace("\b", "\\b").replace("\f", "\\f")
				.replace("\n", "\\n").replace("\r", "\\r").replace("\t", "\\t");
	}
}
