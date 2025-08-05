<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
    <link rel="stylesheet" href="styles/master.css" />
    <title>Bills | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
    <div class="main-content">
        <t:header title="Bills" userType="admin" icon="fa-file-invoice-dollar" />

        <main class="container">
            <article>
                <fieldset>
                    <legend>All bills</legend>

                    <table>
                        <tr>
                            <th>Bill Number</th>
                            <th>Location</th>
                            <th>Status</th>
                            <th>Amount</th>
                        </tr>
                        <tr>
                            <td>10001</td>
                            <td>Pune</td>
                            <td>Paid</td>
                            <td>8478</td>
                        </tr>
                        <tr>
                            <td>10002</td>
                            <td>Mumbai</td>
                            <td>Paid</td>
                            <td>3576</td>
                        </tr>
                        <tr>
                            <td>10003</td>
                            <td>Pune</td>
                            <td>Unpaid</td>
                            <td>2548</td>
                        </tr>
                        <tr>
                            <td>10004</td>
                            <td>Nashik</td>
                            <td>Unpaid</td>
                            <td>1486</td>
                        </tr>
                        <tfoot>
                            <tr>
                                <td>Total Unpaid Bills</td>
                                <td></td>
                                <td></td>
                                <td id="total-bill">4034</td>
                            </tr>
                        </tfoot>
                    </table>
                </fieldset>
            </article>
        </main>

        <t:footer />
    </div>
    <t:sidepanel userId="${userId}" consumerID="ADMIN" />
    <script src="scripts/master.js" defer></script>
</body>

</html>