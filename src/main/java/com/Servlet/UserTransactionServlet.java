package com.Servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Model.Payment;
import com.Service.PaymentService;

/**
 * Servlet implementation class UserTransactionServlet
 */
@WebServlet("/processpayment")
public class UserTransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserTransactionServlet() {
		super();
		// TODO Auto-generated constructor stub
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

		HttpSession session = request.getSession(false);
		long cusIDpage4 = (long) session.getAttribute("SessioncustomerID123");
		@SuppressWarnings("unchecked")
		List<String> billIDs = (List<String>) session.getAttribute("BILLIDS");
		String paymentOption = (String) session.getAttribute("paymentOption");
		String totalPayable = (String) session.getAttribute("totalPayable");

		System.out.println("Transaction Page amount: : " + totalPayable);

//	     Assuming you have a Payment object ready to be inserted
		Payment payment = new Payment();
		payment.setBillID(billIDs);
		payment.setCustomerID(cusIDpage4);
		payment.setPaymentAmount(Double.parseDouble(totalPayable));
		payment.setPaymentDate(LocalDate.now());
		payment.setPaymentMode(paymentOption);
		payment.setPaymentMode("online");
		System.out.println(payment.toString());
//	    // Call PaymentDao to add the payment
		PaymentService paymentService = new PaymentService();
		Payment insertedPayment = paymentService.processPayment(payment);

		if (insertedPayment != null && insertedPayment.getPaymentID() > 0) {
			session.setAttribute("PaymentID", insertedPayment.getPaymentID());

			System.out.println("Payment successfully inserted:");
			System.out.println("PaymentID: " + insertedPayment.getPaymentID());
			System.out.println("BillIDs: " + billIDs); // Assuming you also want to print billIDs

//	        response.sendRedirect("mybills"); // Adjust the URL as per your application
		} else {
			System.out.println("Failed to insert payment or invalid payment data.");
		}

		request.setAttribute("PaymentID", insertedPayment.getPaymentID());
		request.setAttribute("billIDs", billIDs);
		request.setAttribute("totalPayable", totalPayable);
		request.setAttribute("paymentOption", paymentOption);

		request.getRequestDispatcher("views/customer-payment.jsp").forward(request, response);
	}

}
