<%@tag description="Header" pageEncoding="UTF-8"%> 
<%@attribute name="title" required="true" rtexprvalue="true"%>
<%@attribute name="userType" required="true" rtexprvalue="true"%>
<%@attribute name="icon" required="true" rtexprvalue="true"%>

<header class="hero">
    <div class="hero-title">
        <picture> <img src="assets/images/tataPowerLogo.png" /> </picture>
        <h1><i class="fa-solid ${icon}"></i> ${title}</h1>
    </div>
    <% if (userType.equals("admin")) { %>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/admin-home">Admin Home</a></li>
                <li><a href="${pageContext.request.contextPath}/bills">View Bills</a></li>
                <li><a href="${pageContext.request.contextPath}/complaints">View Complaints</a></li>
                <li><a href="${pageContext.request.contextPath}/admin-register-customer">Register Customer</a></li>
                <li><a href="${pageContext.request.contextPath}/generate-bill">Generate Bills</a></li>
            </ul>
        </nav>
    <% } else { %>
        <nav>
             <ul>
                <li><a href="${pageContext.request.contextPath}/home">Home</a></li>
                <li><a href="${pageContext.request.contextPath}/mybills">View/Pay Bill</a></li>
                <li><a href="${pageContext.request.contextPath}/register-complaint">Register Complaint</a></li>
                <li><a href="${pageContext.request.contextPath}/mycomplaints">Complaint Status</a></li>
             </ul>
        </nav>
     <% } %>
</header>