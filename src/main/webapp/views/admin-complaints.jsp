<%@page
    contentType="text/html"
    pageEncoding="UTF-8"%>
<%@taglib
    prefix="t"
    tagdir="/WEB-INF/tags"%>
<%@page import="com.Model.Complaint"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
<html
    lang="en"
    data-theme="dark">
<head>
<meta charset="UTF-8">
<meta
    name="viewport"
    content="width=device-width, initial-scale=1.0">
<t:stylinglibs />
<link
    rel="stylesheet"
    href="styles/master.css">
<script src="scripts/sorttable.js"></script>

<title>Complaints | Electricity Bill Management</title>
</head>
<body class="body-with-aside">
    <div class="main-content">
        <t:header title="Complaints" userType="admin" icon="fa-triangle-exclamation" />
        <main class="container">
            <%
                // Retrieve filter values from the request
                String selectedStatusFilter = (String) request.getAttribute("selectedStatusFilter");
                if (selectedStatusFilter == null) {
                    selectedStatusFilter = "all"; // Default to "all"
                }
                String selectedComplaintID = (String) request.getAttribute("selectedComplaintID");
                String selectedComplaintType = (String) request.getAttribute("selectedComplaintType");
                String selectedComplaintCategory = (String) request.getAttribute("selectedComplaintCategory");
            %>
            
            <article>
                <header>
                    <h3>Filter Complaints</h3>
                </header>
                <form action="complaints" method="post">
                    <fieldset>
                        <legend>Filters</legend>
                        <div class="grid">
                            <fieldset class="flex-col">
                                <legend>Status</legend>
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
                            </fieldset>
                            <label>
                                Complaint ID (optional, regardless of Status)
                                <input name="complaintID" type="text" pattern="[0-9]{10}" minlength="10" maxlength="10"
                                    value="<%= selectedComplaintID != null ? selectedComplaintID : "" %>" />
                            </label>
                        </div>
                        <div class="grid">
                            <label for="complaint-type">
                                Complaint Type
                                <select id="complaint-type" name="complaint-type">
                                    <option value="all" <%= "all".equals(selectedComplaintType) ? "selected" : "" %>>All Types</option>
                                    <option value="billing" <%= "billing".equals(selectedComplaintType) ? "selected" : "" %>>Billing Related</option>
                                    <option value="voltage" <%= "voltage".equals(selectedComplaintType) ? "selected" : "" %>>Voltage Related</option>
                                    <option value="disruption" <%= "disruption".equals(selectedComplaintType) ? "selected" : "" %>>Frequent Disruption</option>
                                    <option value="streetlight" <%= "streetlight".equals(selectedComplaintType) ? "selected" : "" %>>Street Light Related</option>
                                    <option value="pole" <%= "pole".equals(selectedComplaintType) ? "selected" : "" %>>Pole Related</option>
                                </select>
                            </label>
            
                            <label for="complaint-category">
                                Complaint Category
                                <select id="complaint-category" name="complaint-category">
                                    <% 
                                        if (selectedComplaintCategory != null && !selectedComplaintCategory.isEmpty()) {
                                    %>
                                        <option value="<%= selectedComplaintCategory %>" selected>
                                            <%= selectedComplaintCategory.replace("-", " ").toUpperCase() %>
                                        </option>
                                    <% } else { %>
                                        <option value="" disabled selected>Select Category</option>
                                    <% } %>
                                </select>
                            </label>
                        </div>
                            <button type="submit"><i class="fa-solid fa-filter"></i>&nbsp;Filter Complaints</button> 
                    </fieldset>
                </form>
            </article>

            <% Object complaintsObj = request.getAttribute("complaints"); %>
            <% if (complaintsObj != null) { %>
                <% List<Complaint> complaints = (List<Complaint>)complaintsObj; %>
                    <article>
                        <header>
                            <h3>Complaints</h3>
                        </header>
                        <div class="overflow-auto">
                            <table class="sortable">
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
                                            <td>
                                                <button
                                                    onclick="changeComplaintStatus('<%=complaint.getComplaintID()%>')"
                                                    class="outline secondary"
                                                    data-tooltip="Change Status"
                                                    >
                                                    <%=complaint.getComplaintStatus().toUpperCase()%>
                                                </button>
                                            </td>
                                            <td><%=complaint.getComplaintDate()%></td>
                                        </tr>
                                    <% } %>
                            </table>
                        </div>
                    </article>
            <% } else { %>
                <article>
                    <h5>You have no complaints logged in the system.</h5>
                </article>
            <% } %>
            
        </main>
        <t:footer />
    </div>
    <t:sidepanel
        userId="${userId}"
        consumerID="${customerID}" />
    <script
        src="scripts/master.js"
        defer></script>
     <script src="scripts/admin-complaints.js" defer></script>
</body>
</html>