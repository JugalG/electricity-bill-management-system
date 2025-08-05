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
    	href="styles/index.css" />
    <link
    	rel="stylesheet"
    	href="styles/master.css" />
        
    <title>Login | Electricity Bill Management</title>
</head>

<body class="body-without-aside">
    <header class="hero hero-title">
        <picture> <img src="assets/images/tataPowerLogo.png" />
        </picture>
        <h1>Login Status</h1>
    </header>

	<main class="container" style="padding-top:10rem;">
        <% String errorType = String.valueOf(request.getAttribute("error")); %>
        <article>
            <% if (errorType.equalsIgnoreCase("unregistered")) { %>
                <header>
                    <h3>User is not registered!</h3>
                </header>
                <p class="error">User is not registered. Please register first.</p>
            <% } else if (errorType.equalsIgnoreCase("inactive")) { %>
                <header>
                    <h3>User account is deactivated!</h3>
                </header>
                <p class="error">User account was deactivated as per your request. Do you wish to reactivate your account?</p>
                <button onclick="reactivateAccount('${customerID}');">
                    <i class="fa-solid fa-recycle"></i>
                    Reactivate Account
                </button>
            <% } %>
            <footer>
                <a href="${pageContext.request.contextPath}/" role="button" class="back-btn outline secondary">
                    Go back to Login Page
                </a>
            </footer>
        </article>
	</main>
    
    <t:footer />
    
    <button class="theme-toggle theme-toggle-public" id="theme-toggle" aria-label="Toggle Dark Mode" onclick="toggleTheme()">
        <i class="fa-solid fa-sun"></i>
    </button>
    <script src="scripts/login.js"></script>
    <script src="scripts/master.js"></script>
</body>

</html>