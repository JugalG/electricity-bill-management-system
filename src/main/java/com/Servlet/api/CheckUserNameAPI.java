package com.Servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Service.CustomerService;

/**
 * Servlet implementation class UserNameServlet
 */
@WebServlet("/api/username")
public class CheckUserNameAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CheckUserNameAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Sends JSON data
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		// Get the username from the request (checkUserId)
		String userID = request.getParameter("checkUserId");

		// Check if the username is available
		CustomerService service = new CustomerService();
		boolean isAvailable = service.checkUserId(userID);

		// Send the response
		response.getWriter().write("{\"available\":" + isAvailable + "}");

		// Close the response
		response.getWriter().close();
	}

}
