<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <t:stylinglibs />
<link rel="stylesheet" href="styles/master.css" />
<title>Home | Electricity Bill Management</title>
</head>

<body class="body-with-aside">
	<div class="main-content">
		<t:header title="Home" userType="customer" icon="fa-house" />

		<main class="container">
		<article>
			<header>
				<h3>Announcements</h3>
			</header>
			<p>
				<strong>2025-01-03</strong> - Tata Power has launched a new feature
				to view and pay your electricity bill online. You can also register
				complaints and check the status of your complaints.
			</p>
		</article>
		<article>
			<p>Tata Power is India's largest integrated power company with a
				significant international presence. The Company has an installed
				generation capacity of 10,763 MW in India and a presence in all the
				segments of the power sector viz. Generation (thermal, hydro, solar
				and wind), Transmission, Distribution and Trading. It has successful
				public-private partnerships in Generation, Transmission and
				Distribution in India namely &apos; Tata Power Delhi Distribution
				Limited &apos; (TPDDL), "Powerlinks Transmission Ltd." and "Maithon
				Power Limited".</p>
		</article>
		</main>

        <t:footer />
	</div>
    <t:sidepanel userId="${userId}" consumerID="${customerID}"/>
    <script src="scripts/master.js" defer></script>
</body>

</html>