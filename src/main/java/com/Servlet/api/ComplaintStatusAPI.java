package com.Servlet.api;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Service.ComplaintService;

/**
 * Servlet implementation class ComplaintStatusAPI
 */
@WebServlet("/api/complaint/status")
public class ComplaintStatusAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ComplaintStatusAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Set response type to JSON
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		String complaintID = String.valueOf(request.getParameter("complaintID"));

		ComplaintService complaintService = new ComplaintService();

		boolean result = complaintService.toggleComplaintStatus(complaintID);

		// Send the response
		if (result) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.getWriter().write("{\"success\": true}");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("{\"success\": false}");
		}
	}

}
