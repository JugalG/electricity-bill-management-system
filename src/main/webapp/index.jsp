<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html
	lang="en"
	data-theme="dark">

<head>
    <meta charset="UTF-8" />
    <meta
    	name="viewport"
    	content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
    <link
    	rel="stylesheet"
    	href="styles/master.css" />
    <link
    	rel="stylesheet"
    	href="styles/index.css" />        
    <title>Electricity Bill Management</title>
</head>

<body class="body-without-aside">
    <button class="theme-toggle theme-toggle-public" id="theme-toggle" aria-label="Toggle Dark Mode" onclick="toggleTheme()">
        <i class="fa-solid fa-sun"></i>
    </button>

	<header class="hero hero-title">
		<picture> <img src="assets/images/tataPowerLogo.png" /> </picture>
		<hgroup>
			<h1>Tata Power</h1>
			<h2>Empowering India one watt a time!</h2>
		</hgroup>
	</header>

	<main class="container" style="padding-top:10rem;">
		<div class="grid">
			<article id="login-card">
                <header>
                    <h3>
                        Login to your account
                    </h3>
                </header>
				<form
					id="login-form"
					method="POST"
					action="login">
					<fieldset>
                        <% boolean invalid = (String.valueOf(session.getAttribute("error"))).equals("invalid"); %>
						<legend>Existing User/Admin</legend>
                        <label for="user-id">
                        	User ID
                        	<input
                        		name="user-id"
                        		type="text"
                        		id="login-user-id"
                        		placeholder="User ID"
                        		<% if (invalid) { %>
                        			aria-invalid="true"
                        		<% } %>
                        		onkeyup="checkForAdminNamespace();"
                        		required>
                        </label>
						<label for="password">
							Password
							<input
								name="password"
								type="password"
								<% if (invalid) { %>
									aria-invalid="true"
								<% } %>
								placeholder="Enter your password"
								required>
						</label>
						<% if (invalid) { %>
							<small style="color: #b86a6b;" id="login-status">Invalid User ID or Password!</small>
						<% } %>
                         <button type="submit" >
                            <i class="fa-solid fa-sign-in"></i> &nbsp; Login
                         </button>
					</fieldset>
				</form>
                <hr />
				<a  href="${pageContext.request.contextPath}/register"
					role="button"
					class="outline">
                    <i class="fa-solid fa-user-plus"></i> 
                    &nbsp; Register with your Consumer Number
                 </a>
			</article>

			<div class="container">
				<h2>Why Choose Tata Electric?</h2>
				<p>Tata Electric is a leading electricity provider in India. We
					have been providing electricity to millions of households and
					businesses for over 100 years. Our commitment to quality and
					reliability has made us the preferred choice for customers across
					the country. With Tata Electric, you can be assured of
					uninterrupted power supply at competitive rates. Our customer
					service team is available 24/7 to assist you with any queries or
					concerns. Join the Tata Electric family today and experience the
					difference!</p>
				<h2>Our Services</h2>
				<ul>
					<li>Electricity Supply</li>
					<li>Bill Payment</li>
					<li>Customer Support</li>
				</ul>
			</div>
		</div>
	</main>
    
    <t:footer />
    
    <script src="scripts/index.js"></script>
    <script src="scripts/master.js"></script>
</body>

</html>