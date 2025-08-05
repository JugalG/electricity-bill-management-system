<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="com.Model.Complaint"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <t:stylinglibs />
<link rel="stylesheet" href="styles/master.css">
<link rel="stylesheet" href="styles/home.css">
<title>My Complaints | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="My Complaints" userType="customer" icon="fa-file-waveform" /> 

		<main class="container">
			<article>
				<% 
                    String selectedStatusFilter = (String) request.getAttribute("selectedStatusFilter");
                    if (selectedStatusFilter == null) {
                        selectedStatusFilter = "all"; // Default filter
                    }
                    String selectedComplaintID = (String) request.getAttribute("selectedComplaintID");
                %>
                
                <form action="mycomplaints" method="post">
                    <fieldset>
                        <legend>Filters</legend>
                        <div class="grid">
                            <div class="flex-col">
                                <label>
                                    <input type="radio" id="allcomplaints" name="statusfilter" value="all" 
                                        <%= "all".equals(selectedStatusFilter) ? "checked" : "" %>> All Complaints
                                </label>
                                <label>
                                    <input type="radio" id="pendingcomplaints" name="statusfilter" value="pending" 
                                        <%= "pending".equals(selectedStatusFilter) ? "checked" : "" %>> Pending Complaints
                                </label>
                                <label>
                                    <input type="radio" id="resolvedcomplaints" name="statusfilter" value="resolved" 
                                        <%= "resolved".equals(selectedStatusFilter) ? "checked" : "" %>> Resolved Complaints
                                </label>
                            </div>
                            <label>
                                Complaint ID (optional, regardless of Status)
                                <input name="complaintID" type="text" pattern="[0-9]{10}" minlength="10" maxlength="10" 
                                    value="<%= selectedComplaintID != null ? selectedComplaintID : "" %>" />
                            </label>
                        </div>
                        <button type="submit"><i class="fa-solid fa-filter"></i>&nbsp;Apply Filters</button> 
                    </fieldset>
                </form>
			</article>
            <% Object complaintsObj = request.getAttribute("complaints"); %>
            <% if (complaintsObj != null) { %>
            	<% List<Complaint> complaints = (List<Complaint>) complaintsObj; %>
					<article>
						<h5>
							All Complaints
						</h5>
						<table>
	                        <thead>
		                        <tr>
		                        	<th>Complaint ID</th>
		                        	<th>Complaint Type</th>
		                        	<th>Category</th>
		                        	<th>Problem Description</th>
		                        	<th>Status</th>
		                        	<th>Registered On</th>
		                        </tr>
	                        </thead>
	                        <% for (Complaint complaint : complaints) { %>
		                        <tr>	
		                            <td><%=complaint.getComplaintID()%></td>
		                            <td><%=complaint.getComplaintType()%></td>
		                            <td><%=complaint.getCategory()%></td>
		                            <td><%=complaint.getProblem()%></td>
		                            <td><%=complaint.getComplaintStatus().toUpperCase()%></td>
		                            <td><%=complaint.getComplaintDate()%></td>
		                        </tr>
	                        <% } %>
	                    </table>
					</article>
			<% } else { %>
				<article>
					<h5>You have no complaints logged in the system.</h5>
				</article>
			<% } %>
		</main>

        <t:footer />
	</div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}"/>
	<script src="scripts/master.js" defer></script>
</body>

</html>