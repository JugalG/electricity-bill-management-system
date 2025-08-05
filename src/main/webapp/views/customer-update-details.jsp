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
<title>Update Details | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="Update Details" userType="customer" icon="fa-user-pen" />

		<main class="container">
			<!-- TODO: Insert Form and details here -->
			<article>
				<header>
					<h3>Update your Details</h3>
				</header>
				<form action="updateuser" method="post">
				<% Customer cust = (Customer) request.getAttribute("cust"); %>
				<% String selectedUserTitle = (String) cust.getTitle(); %>
					<fieldset>
						<legend>
							User Details
						</legend>
						<div class="grid">
							<label>
	                            Title
	                            <select
	                                name="title"
	                                id="user-title">
	                                <option value="Mx." <%= "Mx.".equals(selectedUserTitle) ? "selected" : "" %>>Mx.</option>
	                                <option value="Mr." <%= "Mr.".equals(selectedUserTitle) ? "selected" : "" %>>Mr.</option>
	                                <option value="Ms." <%= "Ms.".equals(selectedUserTitle) ? "selected" : "" %>>Ms.</option>
	                                <option value="Mrs." <%= "Mrs.".equals(selectedUserTitle) ? "selected" : "" %>>Mrs.</option>
	                                <option value="Dr." <%= "Dr.".equals(selectedUserTitle) ? "selected" : "" %>>Dr.</option>    
	                                <option value="Prof." <%= "Prof.".equals(selectedUserTitle) ? "selected" : "" %>>Prof.</option>
	                                <option value="Rev." <%= "Rev.".equals(selectedUserTitle) ? "selected" : "" %>>Rev.</option>
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
	                                value="<%=cust.getCustomerName()%>"
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
	                                value="<%=cust.getEmail()%>"
	                                required />
	                            <small id="email-constraint"></small>
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
	                                value="<%=cust.getMobileNumber()%>"
	                                required />
	                            <small id="mobile-constraint"></small>
	                        </label>
                        </div>
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
                                value="<%=cust.getUserId()%>"
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
                        <label>
	                        Enter your password to update your details
	                        <input
	                            type="password"
	                            name="auth-password"
	                            id="auth-password"
	                            minlength="8"
	                            maxlength="16"
	                            placeholder="Please enter your password"
	                            required
	                            >
                        </label>
					</fieldset>
                    <input type="hidden" name="type" value="details"/>
					
					<div class="grid">
						<input
                        type="reset"
                        value="Reset"
                        id="update-form-reset"
                        />
                    <input
                        type="submit"
                        value="Submit"
                        id="update-form-submit"
                        disabled
                        />
					</div>
				</form>
			</article>
            
            <article>
            	<header>
					<h3>Or change to a new password</h3>
				</header>
                <form action="updateuser" method="post">
                    <fieldset>
                        <label>
                                Old Password
                                <input
                                    type="password"
                                    name="old-password"
                                    id="old-password"
                                    minlength="8"
                                    maxlength="16"
                                    placeholder="Please enter your old password"
                                    required
								/>
                        </label>
                        <label>
                                New Password
                                <input
                                    type="password"
                                    name="new-password"
                                    id="user-password"
                                    minlength="8"
                                    maxlength="16"
                                    placeholder="Please enter a strong password"
                                    aria-invalid=""
                                    aria-describedby="password-constraint"
                                    onkeyup="checkPasswordConstraints();" />
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
                                    name="new-confirm-password"
                                    id="user-confirm-password"
                                    minlength="8"
                                    maxlength="16"
                                    placeholder="Please confirm your password"
                                    aria-invalid=""
                                    aria-describedby="password-matcher"
                                    onkeyup="checkPasswordMatch();"
                                    value=""  />
                                <small id="password-matcher"></small>
                         </label>
                    </fieldset> 
                    <input type="hidden" name="type" value="password"/>
                    <div class="grid">
                        <input
	                        type="reset"
	                        value="Reset"
	                        id="password-form-reset"
                        />
                    <input
                        type="submit"
                        value="Submit"
                        id="password-form-submit"
                    	disabled    
                    />
                    </div>
                </form>
            </article>
            
            <article>
            	<header>
            		<h3>Account Management</h3>
            	</header>
                <fieldset>
                    <button onclick="openDeactivateDialog();">
                        <i class="fa-solid fa-ban"></i>&nbsp;
                        Deactivate My Account
                    </button>
                </fieldset>
            </article>
			
		<% String status = String.valueOf(request.getAttribute("showDialog")); %>    
        <dialog id="success-dialog"
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
	                        rel="prev"
                            id="success-dialog-button"
                            ></button>
                        <h3>Customer Details Updated Successfully!</h3>
	                </header>
	                <div>
	                    <% Customer customer = (Customer)request.getAttribute("customer"); %>
	                    <table>
	                        <tr>
	                            <th>Customer ID</th>
	                            <td><%=customer.getCustomerID()%></td>
	                        </tr>
	                        <tr>
	                            <th>Title</th>
	                            <td><%=customer.getTitle()%></td>
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
                            rel="prev"
                            id="success-dialog-button"
                            ></button>
                        <h3>Updating Details Failed!</h3>
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
           
        <% String passwordDialogStatus = String.valueOf(request.getAttribute("showPasswordDialog")); %>
        <dialog 
            id="password-success-dialog"
            <% if (passwordDialogStatus.equals("true")) { %>
                open 
            <% } %>>
            <article>
                <% String passwordSuccess = String.valueOf(request.getAttribute("passwordSuccess")); %>
                <% if (passwordSuccess.equals("true")) { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"
                            id="password-success-dialog-button"></button>
                        <h3>Password Updated Successfully!</h3>
                    </header>
                    <div>
                        <p>Your password has been updated successfully!</p>
                        <p>Click the button to close the dialog.</p>
                    </div>
                <% } else { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"
                            id="password-success-dialog-button"></button>
                        <h3>Password Update Failed!</h3>
                    </header>
                    <div>
                        <p>
                            <strong>Reason:</strong>
                            <%= String.valueOf(request.getAttribute("passwordReason")) %>
                        </p>
                    </div>
                <% } %>
            </article>
        </dialog>

        <dialog id="deactivate-confirmation-dialog">
            <article>
                <header>
                    <button
                        aria-label="Close"
                        rel="prev"
                        id="close-deactivate-dialog"
                        ></button>
                    <p>
                        <strong>Deactivate Account</strong>
                    </p>
                </header>
                <div>
                    <p>Are you sure you want to deactivate your
                        account?</p>
                    <form
                        action="updateuser"
                        method="post">
                        <label>
                            Confirm your current password to deactivate
                            <input
                                type="password"
                                name="deactivate-password"
                                id="deactivate-password"
                                minlength="8"
                                maxlength="16"
                                placeholder="Please enter your password"
                                required
                            />
                        </label>
                        <input
                            type="hidden"
                            name="type"
                            value="deactivate" />
                        <input
                            type="submit"
                            value="Yes, Deactivate" />
                    </form>
                </div>
            </article>
        </dialog>
        
        
        <% String deactivateDialogStatus = String.valueOf(request.getAttribute("showDeactivateDialog")); %>
        <dialog
            id="deactivate-success-dialog"
            <% if (deactivateDialogStatus.equals("true")) { %> open <% } %>>
            <article>
                <% Object deactivateSuccessReasonObj = request.getAttribute("deactivateReason"); %>
                <% if (deactivateSuccessReasonObj == null) { %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"
                            onclick="closeDeactivateSuccessDialog();"></button>
                        <h3>Account Deactivated Successfully!</h3>
                    </header>
                    <div>
                        <p>Your account has been successfully deactivated!</p>
                        <p>Click the button to close the dialog.</p>
                    </div>
                <% } else { %>
                    <% String deactivateReason = String.valueOf(deactivateSuccessReasonObj); %>
                    <header>
                        <button
                            aria-label="Close"
                            rel="prev"
                            onclick="closeDeactivateFailureDialog();"
                            ></button>
                        <h3>Account Deactivation Failed!</h3>
                    </header>
                    <div>
                        <p>
                            <strong>Reason:</strong>
                            <%= deactivateReason %>
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
	<script src="scripts/update-details.js" defer></script>
</body>

</html>