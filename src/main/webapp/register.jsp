<%@page import="com.Model.Customer"%>
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
    href="styles/register.css" />
<title>Register | Electricity Bill Management</title>
</head>

<body>
    <header class="hero hero-title">
        <picture> <img src="assets/images/tataPowerLogo.png" />
        </picture>
        <h1>Register New User</h1>
    </header>
    <main class="container">
        <a
            href="${pageContext.request.contextPath}/"
            role="button"
            class="back-btn outline secondary"> 
                <i class="fa-solid fa-arrow-left"></i> &nbsp;
                Go back Home 
            </a>
        <article class="form-card">
            <header>
                <h3>Register using your consumer number</h3>
            </header>
            <form
                action="register"
                id="registration-form"
                method="post">
                <fieldset>
                    <legend>Consumer Details</legend>
                    <div class="grid">
                        <label> 
                            Consumer Number
                            <input
                                type="text"
                                name="consumer-number"
                                id="user-consumer-number"
                                pattern="^[1-9][0-9]{12}$"
                                minlength="13"
                                maxlength="13"
                                placeholder="Enter your consumer number"
                                aria-invalid=""
                                aria-describedby="consumer-number-constraint"
                                onkeyup="checkConsumerNumberConstraints();"
                                required />
                            <small id="consumer-number-constraint"></small>
                            <button
                                type="button"
                                id="populate-details-button"
                                onclick="populateDetails();"
                                disabled
                                >
                                <i class="fa-solid fa-truck-ramp-box"></i>
                                &nbsp;
                                Populate known details into the form
                                &nbsp;
                                <i class="fa-brands fa-wpforms"></i>
                            </button>
                        </label>
                    </div>
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
                <fieldset>
                    <legend>Login Details</legend>
                    <div class="grid">
                        <label>
                            User ID
                            <input
                                type="text"
                                name="userid"
                                id="user-userid"
                                minlength="5"
                                maxlength="20"
                                placeholder="Please select a user ID"
                                pattern="^[A-Za-z][A-Za-z0-9]{4,19}$"
                                aria-invalid=""
                                aria-describedby="user-uniqueness"
                                onkeyup="resetUserIdValidation();"
                                required />
                            <small id="user-uniqueness">User ID must be unique. Click below to check for availability.</small>
                            <button
                                type="button"
                                id="check-availability-button"
                                onclick="checkUserIdAvailability();"
                                disabled
                            >
                                <i class="fa-solid fa-arrows-rotate"></i>
                                Check Availability
                            </button>
                        </label>
                        <div>
                            <label>
                                Password
                                <input
                                    type="password"
                                    name="password"
                                    id="user-password"
                                    minlength="8"
                                    maxlength="16"
                                    placeholder="Please enter a strong password"
                                    aria-invalid=""
                                    aria-describedby="password-constraint"
                                    onkeyup="checkPasswordConstraints();"
                                    required />
                                <small id="password-constraint">
                                    <strong>Password must
                                        contain:</strong>
                                        <ul>   
                                            <li>At least 8 characters</li>
                                            <li>At least 1 uppercase
                                                letter</li>
                                            <li>At least 1 lowercase
                                                letter</li>
                                            <li>At least 1 number</li>
                                            <li>At least 1 special
                                                character</li>
                                        </ul>
                                </small>
                            </label>
                            <label>
                                Confirm Password
                                   <input
                                    type="password"
                                    name="confirm-password"
                                    id="user-confirm-password"
                                    minlength="8"
                                    maxlength="16"
                                    placeholder="Please confirm your password"
                                    aria-invalid=""
                                    aria-describedby="password-matcher"
                                    onkeyup="checkPasswordMatch();"
                                    required />
                                <small id="password-matcher"></small>
                            </label>
                        </div>
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
	                        <strong>Customer Registration Successful!</strong>
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
	                        <tr>
	                            <th>User ID</th>
	                            <td><%=customer.getUserId()%></td>
	                        </tr>
	                    </table>
	                </div>
                <% } else { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"></button>
                        <p>
                            <strong>Customer Registration Failed!</strong>
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
    <button class="theme-toggle theme-toggle-public" id="theme-toggle" aria-label="Toggle Dark Mode" onclick="toggleTheme()">
        <i class="fa-solid fa-sun"></i>
    </button>
    <script src="scripts/master.js"></script>
    <script src="scripts/register.js"></script>
</body>
</html>