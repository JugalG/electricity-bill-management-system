<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@page import="java.util.LinkedHashMap"%>
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
<title>Pay Bills | Electricity Bill Management</title>
</head>
<body class="body-with-aside">
    <div class="main-content">
         <t:header title="Pay Bills" userType="customer" icon="fa-file-invoice-dollar" />
        
         <main class="container">
            <article>
                <form action="pay" method="post">
                <fieldset>
                    <legend>Invoice</legend>
                    <table>
                        <tr>
                            <th>Particulars</th>
                            <th>Amount (₹)</th>
                        </tr>
                        <tr>
                            <td>Bill Amount</td>
                            <td id="bill-amount"><%= request.getAttribute("totalbill") %></td>
                        </tr>
                        <tr>
                            <td>PG Charge</td>
                            <td id="pg-charge">0</td>
                        </tr>
                        <tfoot>
                            <tr>
                                <td>Total Payable</td>
                                <td id="total-payable">0</td>
                                <input
                                    type="hidden"
                                    id="total-payable-hidden"
                                    name="total-payable"
                                    value="0" />
                            </tr>
                        </tfoot>
                    </table>
                </fieldset>
                <fieldset>
                    <legend>Please select your payment option</legend>
                        <label>
                            <input
                                type="radio"
                                name="paymentoption"
                                value="Credit"
                                checked
                                />
                            Credit Card
                        </label>
                        <label>
                            <input
                                type="radio"
                                name="paymentoption"
                                value="Debit" />
                            Debit Card 
                        </label>
                    </fieldset>
                <footer>
                        <input
                            type="submit"
                            value="Pay Now"
                            id="pay-button" />
                    </footer>
                </form>
            </article>
        </main>
        
        <t:footer />
    </div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}"/>
    
    <script
        src="scripts/master.js"
        defer></script>
     <script src="scripts/payBill.js" defer></script>
</body>

</html>