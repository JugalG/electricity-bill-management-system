<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="com.Model.Customer"%>

<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
<link rel="stylesheet" href="styles/master.css" />
<link rel="stylesheet" href="styles/admin-home.css" />
<title>Register a Customer | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="Add a Customer" userType="admin" icon="fa-user-plus" />
        
        <main class="container">
            <article class="form-card">
                <header>
                    <h3>Register a Customer</h3>
                </header>
                <form
                    action="admin-register-customer"
                    id="registration-form"
                    method="post">
                    <fieldset>
                        <legend>Consumer Details</legend>
                        <label> 
                            Consumer Number
                            <input
                                type="text"
                                name="consumer-number"
                                id="user-consumer-number"
                                minlength="13"
                                maxlength="13"
                                placeholder="Generate a random consumer number"
                                required
                                readonly
                                />
                            <button
                                type="button"
                                id="generate-consumerid-button"
                                onclick="generateNewUniqueConsumerID();"
                                >
                                    <i class="fa-solid fa-shuffle"></i>
                                    &nbsp;
                                    Generate a new Unique Consumer ID
                            </button>
                        </label>
                    </fieldset>
                    <fieldset>
                        <legend>User Details</legend>
                        <div class="grid">
                            <label>
                                Title
                                <select
                                    name="title"
                                    id="user-title">
                                    <option value="Mx.">Mx.</option>
                                    <option value="Mr.">Mr.</option>
                                    <option value="Ms.">Ms.</option>
                                    <option value="Mrs.">Mrs.</option>
                                    <option value="Dr.">Dr.</option>    
                                    <option value="Prof.">Prof.</option>
                                    <option value="Rev.">Rev.</option>
                                </select>
                            </label>
                            <label>
                                Name
                                <input
                                    type="text"
                                    name="name"
                                    id="user-name"
                                    pattern="^(?!\s*$)[A-Za-z]+(?: [A-Za-z]+)*$"
                                    placeholder="Enter your name"
                                    minlength="3"
                                    maxlength="50"
                                    aria-invalid=""
                                    aria-describedby="name-constraint"
                                    onkeyup="checkNameConstraints();"
                                    required />
                                 <small id="name-constraint"></small>
                            </label>
                        </div>
                        <div class="grid">
                            <label>
                                Email ID
                                <input
                                    type="email"
                                    name="email"
                                    id="user-email"
                                    placeholder="Enter your email-id"
                                    aria-invalid=""
                                    aria-describedby="email-constraint"
                                    onkeyup="checkEmailConstraints();"
                                    required />
                                <small id="email-constraint"></small>
                            </label>
                            <label>
                                Mobile Number
                                <select
                                    name="country-code"
                                    id="user-country-code"
                                    aria-label="country-code"
                                    required>
                                    <option
                                        value="+91"
                                        disabled
                                        selected>+91 (India)</option>
                                </select>
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
                        </div>
                    </fieldset>
                    <div class="grid">
                        <input
                            type="reset"
                            value="Reset" />
                        <input
                            type="submit"
                            value="Submit"
                            id="rgstr-form-submit"
                            disabled />
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
                <% Object custObj = request.getAttribute("customer"); %>
                <% if (custObj != null) { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"></button>
                        <p>
                            <strong>Adding a Customer was Successful!</strong>
                        </p>
                    </header>
                    <div>
                        <% Customer customer = (Customer)request.getAttribute("customer"); %>
                        <table>
                            <tr>
                                <th>Customer ID</th>
                                <td><%=customer.getCustomerID()%></td>
                            </tr>
                            <tr>
                                <th>Name</th>
                                <td><%=customer.getCustomerName()%></td>
                            </tr>
                            <tr>
                                <th>Email ID</th>
                                <td><%=customer.getEmail()%></td>
                            </tr>
                            <tr>
                                <th>Mobile Number</th>
                                <td><%=customer.getMobileNumber()%></td>
                            </tr>
                        </table>
                    </div>
                <% } else { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"></button>
                        <p>
                            <strong>Registration Failed</strong>
                        </p>
                    </header>
                    <div>
                        <p>
                            <strong>Reason:</strong>
                            Adding a Customer was Unsuccessful. Try again!
                        </p>
                    </div>
                 <% } %>
            </article>
        </dialog>   
        
        </main>
        
		<t:footer />
	</div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}" />
    <script src="scripts/admin-customer-register.js" defer></script>
	<script src="scripts/master.js" defer></script>
</body>

</html>