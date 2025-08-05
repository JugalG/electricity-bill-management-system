<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
<link rel="stylesheet" href="styles/master.css" />
<link rel="stylesheet" href="styles/admin-home.css" />
<title>Admin Home | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="Home" userType="admin" icon="fa-home" />

		<main class="container">
        <div class="grid">
            <article class="flex-col flex-align-center">
            	<i class="fa-solid fa-users"></i>
            	<h5>
            		Total Users: <span id="total-users">${totalUsers}</span>
            	</h5>
            </article>
            <article class="flex-col flex-align-center">
				<i class="fa-solid fa-file-invoice-dollar"></i>
            	<h5>
            		Total Due Bills: <span id="total-bills">${totalDueBills}</span>
            	</h5>
            </article>
            <article class="flex-col flex-align-center">
            	<i class="fa-solid fa-file-circle-exclamation"></i>
            	<h5>
            		Total Pending Complaints: <span id="total-complaints">${pendingComplaints}</span>
            	</h5>
            </article>
        </div>
		</main>

		<t:footer />
	</div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}" />
	<script src="scripts/master.js" defer></script>
</body>

</html>