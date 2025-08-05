<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="com.Model.Bill"%>
<%@page import="com.Service.BillService"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html
    lang="en"
    data-theme="dark">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
<link rel="stylesheet" href="styles/master.css" />
<script src="scripts/sorttable.js"></script>

<title>View/Pay Bill | Electricity Bill Management</title>
</head>
<body class="body-with-aside">
    <div class="main-content">
        <t:header title="View/Pay Bill" userType="customer" icon="fa-file-invoice-dollar" />
        <main class="container">
            <% List<Bill> bills = (List<Bill>) request.getAttribute("bills"); %>
            <article>
                <header>
                    <h3>Your Due Bills</h3>
                </header>
                <% Object billsObject = request.getAttribute("bills"); %>
                <% if (billsObject != null) { %>
                <form
                    action="paybills"
                    method="post">
                        <table class="sortable">
                            <thead>
                                <tr>
                                    <th>Pay Now</th>
                                    <th>Bill ID</th>
                                    <th>Units(kWH)</th>
                                    <th>Month</th>
                                    <th>Expiration Date</th>
                                    <th>Status</th>
                                    <th>Amount (₹)</th>
                                </tr>
                            </thead>
                            <% 
                                for (Bill bill : bills) { 
                                    if(!bill.getPaymentStatus().equalsIgnoreCase("paid")) { 
                                        BillService billService = new BillService();
                                        String month = billService.getBillMonth(bill.getBillDate());
                            %>
                            <tr>
                                <td><input
                                        type="checkbox"
                                        class="billchecker"
                                        name="bill<%=bill.getBillID()%>"
                                        data-amount="<%=bill.getDueAmount()%>" /></td>
		                                <td><%=bill.getBillID()%></td>
		                                <td><%=bill.getConsumedUnits()%></td>
                                        <td><%=month%></td>
                                        <td><%=bill.getBillExpiryDate()%></td>
		                                <td><%=bill.getPaymentStatus()%></td>
		                                <td><%=bill.getDueAmount()%></td>
                            </tr>
                            <% }} %>
                            <tfoot>
                                <tr>
                                    <td colspan="6">Total Amount</td>
                                    <td>₹ <span  id="total-bill"> 0 </span></td>
                                    <input type="hidden" name="totalbill" id="total-bill-hidden" value="0" />
                                </tr>
                            </tfoot>
                        </table>
                    <footer>
                        <input
                            type="submit"
                            id="pay-button"
                            value="Pay Selected"
                           	disabled
                            />
                    </footer>
                </form>
                <% } else { %>
                	<p>No bills found</p>
                <% } %>
            </article>
            
            <article>
                <header>
                    <h3>Your Bill History</h3>
                </header>
                <table class="sortable">
                    <thead>
                        <tr>
                            <th>Bill ID</th>
                            <th>Units(kWH)</th>
                            <th>Month</th>
                            <th>Status</th>
                            <th>Transaction ID</th>
                        </tr>
                    </thead>
                    <%
                    for (Bill bill : bills) {
                    	if (bill.getPaymentStatus().equalsIgnoreCase("paid")) {
                    		BillService billService = new BillService();
                    		String month = billService.getBillMonth(bill.getBillDate());
                    %>
                    <tr>
                        <td><%=bill.getBillID()%></td>
                        <td><%=bill.getConsumedUnits()%></td>
                        <td><%=month%></td>
                        <td><%=bill.getPaymentStatus()%></td>
                        <td><%=bill.getPaymentID()%></td>
                    </tr>
                    <% }} %>
                </table>
            </article>
        </main>
        <t:footer />
    </div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}"/>
    <script
        src="scripts/master.js"
        defer></script>
     <script src="scripts/bill.js" defer></script>
</body>
</html> 