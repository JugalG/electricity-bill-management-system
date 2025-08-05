package com.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Model.Login;
import com.Service.LoginService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginService loginService;

	public LoginServlet() {
		super();
		loginService = new LoginService();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// If user directly tries to go to login then resend to index
		HttpSession session = request.getSession(false);
		session.invalidate();
		response.sendRedirect("index.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String userId = request.getParameter("user-id");
		String password = request.getParameter("password");

		try {
			Login login = new Login();
			login.setUserID(userId);
			login.setPassword(password);

			Login validatedLogin = loginService.checkLoginDetailsAndReturnLogin(login);
//			System.out.println("Validated Login Object: " + validatedLogin);

			if (validatedLogin != null && "active".equalsIgnoreCase(validatedLogin.getStatus())) {
				HttpSession session = request.getSession(true);
				session.setAttribute("SessioncustomerID123", validatedLogin.getCustomerID());
				int minutes = 30;
				session.setMaxInactiveInterval(minutes * 60);

				// Set user details in session
				session.setAttribute("userId", validatedLogin.getUserID());
				session.setAttribute("userType", validatedLogin.getUserType());
				session.setAttribute("customerID", validatedLogin.getCustomerID());
				session.setAttribute("email", validatedLogin.getEmail());

				// Add session cookie
				Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
				sessionCookie.setMaxAge(minutes * 60);
				response.addCookie(sessionCookie);

				// Redirect based on user type
				if ("admin".equals(validatedLogin.getUserType())) {
					response.sendRedirect("admin-home");
				} else {
					response.sendRedirect("home");
				}
			} else if (validatedLogin != null && "inactive".equalsIgnoreCase(validatedLogin.getStatus())) {
				// If the user is inactive AND userID is null then that means the user is not
				// registered
				// Send to login.jsp with this message

				if (validatedLogin.getUserID() == null) {
					request.setAttribute("error", "unregistered");
				} else {
					request.setAttribute("customerID", validatedLogin.getCustomerID());

					request.setAttribute("error", "inactive");
				}

				request.getRequestDispatcher("login.jsp").forward(request, response);
			} else {
				HttpSession session = request.getSession(false);
				session.setAttribute("customerID", validatedLogin.getCustomerID());
				session.setMaxInactiveInterval(10);
				session.setAttribute("error", "invalid");
				response.sendRedirect("index.jsp");
			}
		} catch (Exception e) {
			e.printStackTrace();
			HttpSession session = request.getSession(false);
			session.setAttribute("customerID", null);
			session.setMaxInactiveInterval(10);
			session.setAttribute("error", "invalid");
			response.sendRedirect("index.jsp");
		}
	}
}
