package com.Servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Model.Bill;
import com.Service.BillService;
import com.Service.CustomerService;


@WebServlet("/generate-bill")
public class AdminGenerateBillsPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminGenerateBillsPageServlet() {
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
				// Check if the session belongs to an admin and not a customer
				String userType = String.valueOf(session.getAttribute("userType"));
				if (userType.equals("admin")) {

					response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", 0);

					RequestDispatcher view = request.getRequestDispatcher("views/admin-generate-bills.jsp");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession(false);

	    String customerID = request.getParameter("consumer-number");
	    String unitsConsumedString = request.getParameter("units-consumed");
	    String billDateString = request.getParameter("billDate");
	    String additionalChargesString = request.getParameter("additional-charges");
	    String totalAmountString = request.getParameter("total-amount");

	    if (additionalChargesString == null || additionalChargesString.trim().isEmpty()) {
	        additionalChargesString = "0"; // Default to 0 if not provided
	    }

	    // Validate inputs
	    if (customerID == null || customerID.trim().isEmpty() ||
	        unitsConsumedString == null || unitsConsumedString.trim().isEmpty() ||
	        billDateString == null || billDateString.trim().isEmpty() ||
	        totalAmountString == null || totalAmountString.trim().isEmpty()) {

	        request.setAttribute("error", "All fields are required.");
	        this.doGet(request, response);
	        return;
	    }
	    
	    // Check if customer ID exists
	    CustomerService customerService = new CustomerService();
	    if (!customerService.checkCustomerID(Long.parseLong(customerID.trim()))) {
	        request.setAttribute("showDialog", true);
	        request.setAttribute("status", "failure");
	        request.setAttribute("reason", "This customer ID does not exist.");
	        this.doGet(request, response);
	        return;
	    }

	    try {
	        Bill bill = new Bill();
	        bill.setCustomerID(Long.parseLong(customerID.trim()));
	        bill.setDueAmount(Double.parseDouble(totalAmountString.trim()));
	        bill.setBillDate(Date.valueOf(billDateString.trim()));
	        bill.setConsumedUnits(Integer.parseInt(unitsConsumedString.trim()));

	        Bill insertBillObj = BillService.insertValuesInBillTableByAdmin(bill);

	        if (insertBillObj == null) {
	            request.setAttribute("showDialog", false);
	            this.doGet(request, response);
	            return;
	        }

	        request.setAttribute("showDialog", true);
	        request.setAttribute("status", "success");
	        this.doGet(request, response);
	    } catch (IllegalArgumentException e) {
	        request.setAttribute("error", "Invalid input format.");
	        this.doGet(request, response);
	    }
	}


}
