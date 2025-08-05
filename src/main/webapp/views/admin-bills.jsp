<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page import="com.Model.Bill" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.List" %>
<%@ page import="com.Service.BillService" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
    <link rel="stylesheet" href="styles/master.css" />
    <script src="scripts/sorttable.js" defer></script>
    <title>Bills | Electricity Bill Management</title>
</head>
<body class="body-with-aside">
    <div class="main-content">
        <t:header title="Bills" userType="admin" icon="fa-file-invoice-dollar" />
        <main class="container">
        
            <article>
                <% 
                    String selectedStatusFilter = (String) request.getAttribute("selectedStatusFilter");
                    if (selectedStatusFilter == null) {
                        selectedStatusFilter = "pending"; // Default filter
                    }
                    String selectedBillID = (String) request.getAttribute("selectedBillID");
                %>
                
                <header>
                    <h3>Filters</h3>                    
                        <form
                            action="bills"
                            method="post">
                            <div class="grid">
                                <fieldset>
                                    <legend>Payment Status</legend>
                                    <div class="flex-col">
                                        <label>
                                            <input type="radio" id="unpaidbills" name="statusfilter" value="pending" 
                                                <%= "pending".equals(selectedStatusFilter) ? "checked" : "" %>> Unpaid Bills
                                        </label>
                                        <label>
                                            <input type="radio" id="paidbills" name="statusfilter" value="paid" 
                                                <%= "paid".equals(selectedStatusFilter) ? "checked" : "" %>> Paid Bills
                                        </label>
                                    </div>
                                </fieldset>
                               <label>
                                    Bill ID (optional, regardless of Status)
                                    <input name="billID" type="text" pattern="^[1-9][0-9]{9}$" minlength="10" maxlength="10" 
                                        value="<%= selectedBillID != null ? selectedBillID : "" %>" />
                                </label>
                            </div>
                            
                            <button type="submit"><i class="fa-solid fa-filter"></i>&nbsp;Apply Filters</button> 
                        </form>
                </header>
            </article>
        
            <article>
                <header>
                    <h3>
                        <% if ("pending".equals(selectedStatusFilter)) { %>   
                            Unpaid Bills
                        <% } else { %>
                            Paid Bills
                        <% } %>
                    </h3>
                </header>
                <div class="overflow-auto">
                    <table class="sortable">
                        <thead>
                            <tr>
                                <th>Bill Number</th>
                                <th>CustomerID</th>
                                <th>Connection Type</th>
                                <th>Consumed Units</th>
                                <th>Due Amount (₹)</th>
                                <th>Payment Amount (₹)</th>
                                <th>Status</th>
                                <th>Bill Month</th>
                                <th>Expiry Date</th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                Object billsOBJ = request.getAttribute("bills");
                                if (billsOBJ instanceof List<?>) {
                                    List<?> rawList = (List<?>) billsOBJ;
                                    boolean hasBills = false;

                                    for (Object obj : rawList) {
                                        if (obj instanceof Bill) {
                                            hasBills = true;
                                            Bill bill = (Bill) obj;
                                            
                                            BillService billService = new BillService();
                                            String billMonth = billService.getBillMonth(bill.getBillDate());
                            %>
                                            <tr>
                                                <td><%= bill.getBillID() %></td>
                                                <td><%= bill.getCustomerID() %></td>
                                                <td><%= bill.getConnectionType() != null ? bill.getConnectionType() : "N/A" %></td>
                                                <td><%= bill.getConsumedUnits() %></td>
                                                <td><%= bill.getDueAmount() %></td>
                                                <td><%= bill.getPaymentAmount() %></td>
                                                <td><%= bill.getPaymentStatus() %></td>
                                                <td><%= billMonth %></td>
                                                <td><%= bill.getBillExpiryDate() != null ? bill.getBillExpiryDate() : "N/A" %></td>
                                            </tr>
                            <%
                                        }
                                    }
                                    if (!hasBills) {
                            %>
                                        <tr>
                                            <td colspan="9">No bills with selected filters found.</td>
                                        </tr>
                            <%
                                    }
                                } else {
                            %>
                                <tr>
                                    <td colspan="9">Unable to retrieve bills.</td>
                                </tr>
                            <%
                                }
                            %>
                        </tbody>
                        <% if ("pending".equals(selectedStatusFilter)) { %>
                            <tfoot>
                                <tr>
                                    <td>Total Unpaid Bills</td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>₹ <span id="total-bill">
                                            <%
                                                double totalAmount = 0;
                                                if (billsOBJ instanceof List<?>) {
                                                    for (Object obj : (List<?>) billsOBJ) {
                                                        if (obj instanceof Bill) {
                                                            Bill bill = (Bill) obj;
                                                            totalAmount += bill.getDueAmount();
                                                        }
                                                    }
                                                }
                                                out.print(String.format("%.2f", totalAmount));
                                            %>
                                        </span>
                                    </td>
                                    <td colspan="4"></td>
                                </tr>
                            </tfoot>
                        <% } %>
                    </table>
                </div>
            </article>
        </main>
        <t:footer />
    </div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}" />
    <script src="scripts/master.js" defer></script>
</body>
</html>
