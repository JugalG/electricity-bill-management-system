package com.Servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Model.Customer;
import com.Service.CustomerService;
import com.Util.Tuple;

@WebServlet("/register")
public class PublicRegisterPage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PublicRegisterPage() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("register.jsp");
		view.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Get the parameters from the request
		long custid = Long.parseLong(req.getParameter("consumer-number"));
		String title = req.getParameter("title");
		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String mobile = req.getParameter("mobile");
		String userId = req.getParameter("userid");
		String password = req.getParameter("password");
		String confirmPassword = req.getParameter("confirm-password");

		CustomerService customerservice = new CustomerService();

		Tuple<String, Customer> tuple = customerservice.validateAndRegisterCustomer(custid,title, name, email, mobile, userId,
				password, confirmPassword);

		if (tuple.getSecond() == null) {
			req.setAttribute("reason", tuple.getFirst());
			req.setAttribute("showDialog", true);
			this.doGet(req, resp);
			return;
		}

		req.setAttribute("customer", tuple.getSecond());
		req.setAttribute("showDialog", true);

		this.doGet(req, resp);
	}
}