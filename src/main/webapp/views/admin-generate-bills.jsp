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
<title>Generate Bills | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="Generate Bill" userType="admin" icon="fa-gears" />
        
        <main class="container">
            <article class="form-card">
                <header>
                    <h3>Generate Bills for a Customer</h3>
                </header>
                <form
				    action="generate-bill"
				    id="generate-bill-form"
				    method="post">
				    <fieldset>
				        <legend>Customer Details</legend>
				        <label> 
				            Consumer Number
				            <input
				                type="text"
				                name="consumer-number"
				                id="user-consumer-number"
				                pattern="^[1-9][0-9]{12}$"
				                minlength="13"
				                maxlength="13"
				                placeholder="Enter consumer number"
				                aria-invalid=""
				                aria-describedby="consumer-number-constraint"
				                onkeyup="checkConsumerNumberConstraints();"
				                required />
				            <small id="consumer-number-constraint"></small>
				        </label>
				    </fieldset>
				    <fieldset>
				        <legend>Bill Details</legend>
				        <div class="grid">
				            <label>
				                Units Consumed (kWh)
				                <input
				                    type="number"
				                    name="units-consumed" 
				                    id="units-consumed"
				                    min="0"
				                    max="100000"
				                    step="1"
				                    placeholder="Enter units consumed"
				                    aria-invalid=""
				                    aria-describedby="units-consumed-constraint"
				                    required />
				                <small id="units-consumed-constraint"></small>
				            </label>
				            <label>
				                Bill Date
				                <input
				                    type="date"
				                    name="billDate"
				                    id="bill-date"
				                    aria-invalid=""
				                    aria-describedby="bill-date-constraint"
				                    required />
				                <small id="bill-date-constraint"></small>
				            </label>
				       </div>
				       <label>
				            Additional Charges (₹)
				            <input
				                type="number"
				                name="additional-charges"
				                id="additional-charges"
				                placeholder="Enter additional charges"
				                aria-invalid=""
				                aria-describedby="additional-charges-constraint"
				                min="0"
				                step="0.01"
				                value="0"
				                required />
				            <small id="additional-charges-constraint"></small>
				        </label>
				    </fieldset>
				    <fieldset>
				        <legend>Total</legend>
				        <label>
				            Total Amount (₹)
				            <input
				                type="number"
				                name="total-amount"
				                id="total-amount"
				                value="0"
				                readonly />
				         </label>
				    </fieldset>
				    <div class="grid">
				        <input
				            type="reset"
				            value="Reset" />
				        <input
				            type="submit"
				            value="Submit"
				            id="generate-bill-form-submit"
				            disabled />
				    </div>
				</form>

            </article>
            
    		<% String dialogStatus = String.valueOf(request.getAttribute("showDialog")); %>    
            <dialog 
                id="dialog"
                <% if (dialogStatus.equals("true")) { %>
                open 
                <% } %>
            >
                <article>
                	<header>
    	                    <button id="dialog-close-button" aria-label="Close" rel="prev"></button>
    						<h3>Bill Generation Status</h3>
    	           	</header>
                    <% String status = String.valueOf(request.getAttribute("status")); %>
                    <% if (status.equalsIgnoreCase("success")) { %>
    	                <div>
                            <p>
    							Bill was generated successfully.
                            </p>
    	                </div>
                    <% } else { %>
                    	<div>
                            <p>
    							Bill Generation Failed. Please Try again later.
                            </p>
                            <p>
                            	Reason : ${reason}
                            </p>
                        </div>
                     <% } %>
                </article>
            </dialog>
        </main>
        
		<t:footer />
	</div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}" />
    <script src="scripts/admin-generate-bills.js" defer></script>
	<script src="scripts/master.js" defer></script>
</body>

</html>