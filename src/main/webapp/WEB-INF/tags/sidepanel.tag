<%@tag description="Side panel" pageEncoding="UTF-8"%> 
<%@attribute name="userId" required="true"%>
<%@attribute name="consumerID" required="true"%>

<aside>
    <div>
        <% String userType = (String) session.getAttribute("userType"); %>
    	<hgroup>
	        <h3 id="user-id">
	            Welcome, ${userId}!
	        </h3>
            <% if (userType.equals("user")) { %>
                <h5>Customer</h5>
    	        <h6>
    	        	Consumer Number : ${consumerID}
    	        </h6>
            <% } else { %>
                <h5>Admin</h5>
                <h6>Employee ID: ${consumerID}</h6>
            <% } %>
        </hgroup>
    	

    	<% if (userType.equals("user")) { %>
	    	 <a href="${pageContext.request.contextPath}/updateuser" role="button">
	            Update My details &nbsp;
	            <i class="fa-solid fa-user-pen"></i>
	         </a>
	    <% } %>
    	
        <a href="${pageContext.request.contextPath}/logout" role="button" class="secondary">
            Logout &nbsp;
            <i class="fa-solid fa-right-from-bracket"></i>
         </a>
        <button class="theme-toggle" id="theme-toggle" aria-label="Toggle Dark Mode" onclick="toggleTheme()">
            <i class="fa-solid fa-sun"></i>
        </button>
    </div>
    <div class="logo-container">
        <img src="assets/images/tataPowerLogo.png" alt="Tata Power" />
    </div>
</aside>