<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.Model.Complaint" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
    <link rel="stylesheet" href="styles/master.css" />
    <title>Register Complaint | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="Register Complaint" userType="customer" icon="fa-file-circle-exclamation" />

		<main class="container">
                        <article>
                <header>
                    <h2>Register your complaint</h2>
                </header>
                <form id="complaint-form" action="register-complaint" method="post">
                    <fieldset>
                        <legend>Complaint Details </legend>

                        <label for="complaint-type">
                            Complaint Type 
                            <select
                                id="complaint-type" name="complaint-type">
                                    <option value="billing">Billing Related</option>
                                    <option value="voltage">Voltage Related</option>
                                    <option value="disruption">Frequent Disruption</option>
                                    <option value="streetlight">Street Light Related</option>
                                    <option value="pole">Pole Related</option>
                            </select>
                        </label> 
                  
                        <label for="complaint-category"> Complaint Category
                            <select id="complaint-category" name="complaint-category">
    						</select>
						</label> 
                        <label>  
                            Problem Description 
                            <textarea 
                                id="problem-description"
                                placeholder="Describe the problem"
                                name="problem-description" 
                                aria-invalid=""
                                aria-describedby="description-constraint"
                                onkeyup="checkDescriptionConstraints();"
                                required></textarea>
                            <small id="description-constraint"></small>
                        </label>
                    </fieldset>
                    
                    <fieldset>
                        <legend>User Details</legend>
                        <label> 
                            Consumer Number 
                            <input
                                type="text" id="consumer-number" name="consumer-number"
                                value="${customerID}"
                                maxlength="13" readonly />
                        </label>                         
                        <label>  
                            Landmark 
                            <input 
                                type="text"
                                id="landmark" 
                                name="landmark" 
                                placeholder="Enter Landmark" 
                                aria-invalid=""
                                aria-describedby="landmark-constraint"
                                onkeyup="checkLandmarkConstraints();"
                                required />
                            <small id="landmark-constraint"></small>
                        </label>
                        <label> 
                            Mobile Number 
                            <input
                                type="tel"
                                name="mobile"
                                id="user-mobile"
                                pattern="^[6-9](?!(\d)\1{4})[0-9]{9}$"
                                minlength="10"
                                maxlength="10"
                                placeholder="Enter your mobile number"
                                aria-invalid=""
                                aria-describedby="mobile-constraint"
                                onkeyup="checkMobileConstraints();"
                                required />
                            <small id="mobile-constraint"></small>
                        </label> 
                        <label> 
                            Address 
                            <textarea 
                                id="address" 
                                name="address" 
                                placeholder="Enter Address"
                                aria-invalid=""
                                aria-describedby="address-constraint"
                                onkeyup="checkAddressConstraints();"
                                required></textarea>
                            <small id="address-constraint"></small>
                        </label>
                    </fieldset>
                    <div class="grid">
                        <input type="reset" value="Reset" /> 
                        <input id="complaint-form-submit" type="submit" value="Submit Complaint" />
                    </div>
                </form>
            </article>
			
			 <% String status = String.valueOf(request.getAttribute("showDialog")); %> 
			<dialog 
	            <% if (status.equals("true")) { %>
	            	open 
	            <% } %>
	        >
				<article>
                <% Object complaintObj = request.getAttribute("complaint"); %>
                <% if (complaintObj != null) { %>
	                <header>
	                    <button
	                        aria-label="Close"
	                        rel="prev"></button>
	                    <p>
	                        <strong>Complaint Submitted Successfully!</strong>
	                    </p>
	                </header>
	                <div>
	                    <% Complaint complaint = (Complaint) complaintObj; %>
	                    <table>
	                        <tr>
	                            <th>Complaint ID</th>
	                            <td><%=complaint.getComplaintID()%></td>
	                        </tr>
	                        <tr>
	                            <th>Customer ID</th>
	                            <td><%=complaint.getCustomerID()%></td>
	                        </tr>
	                        <tr>
	                            <th>Complaint Type and Category</th>
	                            <td><%=complaint.getComplaintType() + " : " + complaint.getCategory()%></td>
	                        </tr>
	                        <tr>
	                            <th>Problem Description</th>
	                            <td><%=complaint.getProblem()%></td>
	                        </tr>
	                        <tr>
	                            <th>Status</th>
	                            <td><%=complaint.getComplaintStatus()%></td>
	                        </tr>
	                        <tr>
	                        	<th>Registered On</th>
	                        	<td><%=complaint.getComplaintDate()%></td>
	                       	</tr>
	                    </table>
	                </div>
                <% } else { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"
                            ></button>
                        <p>
                            <strong>Complaint Submission Failed!</strong>
                        </p>
                    </header>
                	<div>
                        <p>
                            <strong>Reason:</strong>
                            <%= String.valueOf(request.getAttribute("reason"))%>
                        </p>
                    </div>
                 <% } %>
            </article>
			</dialog>
		</main>
        
        <t:footer />
	</div>
    
    <t:sidepanel userId="${userId}" consumerID="${customerID}"/>
    
	<script src="scripts/master.js" defer></script>
    <script src="scripts/register-complaint.js" defer></script>
</body>

</html>