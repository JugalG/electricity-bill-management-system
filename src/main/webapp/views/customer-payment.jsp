<%@ page
    language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date"%>
<%@ taglib
    prefix="t"
    tagdir="/WEB-INF/tags"%>
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
    href="styles/payment.css" />
<script
    src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.4.0/jspdf.umd.min.js"></script>
<title>Payment | Electricity Bill Management</title>
</head>
<body>
    <header class="hero">
        <h1>Payment with ${paymentOption} Card</h1>
    </header>
    <main class="container main-content payment-main">
        <article class="credit-card-form-card">
            <form
                id="credit-card-form"
                action="processpayment"
                method="post"
                onsubmit="return validateExpiryDate();">
                <fieldset>
                    <legend id="cardtype">${paymentOption} Card details</legend>
                    <label for="card-number">
                        Card Number
                        <input
                            type="text"
                            name="card-number"
                            id="card-number"
                            minlength="16"
                            maxlength="16"
                            pattern="[0-9]{16}"
                            placeholder="Enter your 16-digit card number"
                            aria-invalid=""
                            aria-describedby="card-number-constraint"
                            onkeyup="checkCardNumberConstraints();"
                            required />
                        <small id="card-number-constraint"></small>
                    </label>
                    <div class="grid">
                        <label for="expiry-month">
                            Expiry Month
                            <select name="month" id="expiry-month" aria-describedby="expiry-constraint-month" required>
                                <option value="" disabled selected>Select Month</option>
                                <option value="1">January</option>
                                <option value="2">February</option>
                                <option value="3">March</option>
                                <option value="4">April</option>
                                <option value="5">May</option>
                                <option value="6">June</option>
                                <option value="7">July</option>
                                <option value="8">August</option>
                                <option value="9">September</option>
                                <option value="10">October</option>
                                <option value="11">November</option>
                                <option value="12">December</option>
                            </select>
                            <small id="expiry-constraint-month"></small>
                        </label>
                        <label for="expiry-year">
                            Expiry Year
                            <input
                                type="number"
                                name="year"
                                id="expiry-year"
                                min="25"
                                max="39"
                                placeholder="Enter year (e.g., 25)"
                                aria-describedby="expiry-constraint-year"
                                required />
                            <small id="expiry-constraint-year"></small>
                            </label>
                            <label for="cvv">
                                CVV/CVC
                                <input
                                    type="number"
                                    name="cvc"
                                    id="cvv"
                                    min="100"
                                    max="999"
                                    placeholder="Enter 3-digit CVV"
                                    aria-describedby="cvv-constraint"
                                    required />
                                 <small id="cvv-constraint"></small>
                            </label>
                    </div>
                    <label for="card-holder-name">
                        Card Holder Name
                        <input
                            type="text"
                            name="card-holder-name"
                            id="card-holder-name"
                            pattern="^[a-zA-Z]+( [a-zA-Z]+)*$"
                            placeholder="Enter full name as on the card"
                            aria-invalid=""
                            aria-describedby="name-constraint"
                            onkeyup="checkCardHolderName();"
                            required />
                        <small id="name-constraint"></small>
                    </label>
                </fieldset>
                <fieldset>
                    <legend>Payment Details</legend>
                    <table>
                        <tr>
                            <th>For Bills:</th>
                            <td>${BILLIDS}</td>
                        </tr>
                        <tr>
                            <th>Total Payable</th>
                            <td>(₹) ${totalPayable}</td>
                        </tr>
                        <tr>
                            <th>Payment Method</th>
                            <td>${paymentOption}</td>
                        </tr>
                    </table>        
                </fieldset>
                <div class="grid">
                    <input
                        type="button"
                        onclick="window.location.href='mybills'"
                        class="outline secondary"
                        value="Cancel Transaction" />
                    <input
                        type="reset"
                        value="Clear" />
                    <input
                        id="credit-card-form-submit"
                        type="submit"
                        value="Make Payment"
                        disabled
                        />
                </div>
            </form>
        </article>
        <dialog
            <% if (request.getAttribute("PaymentID") != null) { %>
                open
            <% } %>
        >
            <article>
                <header>
                    <h3>Transaction Details</h3>
                </header>
                <main class="overflow-auto">
                    <table id="transaction-table">
                        <tr>
                            <th>Payment ID</th>
                            <th>For Bills</th>
                            <th>Amount</th>
                            <th>Payment Type</th>
                            <th>Transaction Date</th>
                        </tr>
                        <tr>
                            <th id="transaction-id">${PaymentID}</th>
                            <th>${billIDs}</th>
                            <th>(₹) ${totalPayable}</th>
                            <th>${paymentOption}</th>
                            <th><%= new Date() %></th>
                        </tr>
                    </table>
                </main>
                <footer>
                    <button
                        class="secondary"
                        id="dialog-close"
                        onclick="closeDialog()">
                        Close and Return to Home
                    </button>
                    <button
                        id="downloadButton"
                        onclick="downloadPDF()">Download these
                        Details</button>
                </footer>
            </article>
        </dialog>
    </main>
    <footer class="main-footer">
        <!-- Footer Content -->
        <small>Copyright TCS © 2025</small>
    </footer>
    <button class="theme-toggle theme-toggle-public" id="theme-toggle" aria-label="Toggle Dark Mode" onclick="toggleTheme()">
        <i class="fa-solid fa-sun"></i>
    </button>
    <script
        src="scripts/master.js"
        defer></script>
    <script
        src="scripts/payment.js"
        defer></script>
</body>
</html>
