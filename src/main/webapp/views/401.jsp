<!DOCTYPE html>
<html lang="en" data-theme="dark">

<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2/css/pico.min.css" />
    <link rel="stylesheet" href="styles/index.css" />
    <link rel="stylesheet" href="styles/master.css" />
    <title>401 Unauthorized | Electricity Bill Management</title>
</head>

<body class="body-without-aside">
    <header class="hero hero-title">
        <picture>
            <img src="assets/images/tataPowerLogo.png" />
        </picture>
        <hgroup>
            <h1>Tata Electric</h1>
            <h2>Empowering India one watt a time!</h2>
        </hgroup>
    </header>
	<main class="container" style="padding-top:10rem;">
        <h2>401 Unauthorized</h2>
        <p>
            The resource you're trying to access needs authentication. Please authenticate yourself.
        </p>
        <a href="${pageContext.request.contextPath}/" role="button" class="back-btn outline secondary">
            Go back to Login Page
        </a>
    </main>
    <footer class="main-footer">
        <div class="top-footer">
            <div class="grid">
                <div class="logo-container">
                    <img src="assets/images/tataPowerLogo.png" alt="Tata Power" />
                </div>
                <div class="company-links-container">
                    <h6>Who We Are</h6>
                    <a href="#">Our Legacy</a>
                    <a href="#">Our Story</a>
                    <a href="#">Our Vision</a>
                    <a href="#">Our Leadership</a>
                    <a href="#">Company Resource Center</a>
                </div>

                <div class="company-links-container">
                    <h6>What We Do</h6>
                    <a href="#">Energy Solutions</a>
                    <a href="#">Renewable Energy</a>
                    <a href="#">Solar Energy</a>
                    <a href="#">Solar Rooftop</a>
                    <a href="#">Wind Energy</a>
                    <a href="#">Hydro Energy</a>
                </div>

                <div class="company-links-container">
                    <h6>Sustainability</h6>
                    <a href="#">Sustainability Ethos</a>
                    <a href="#">Environmental Commitment</a>
                    <a href="#">UNSDG Alignment</a>
                    <a href="#">Sustainable Governance</a>
                    <a href="#">Ethics</a>
                </div>

                <div class="company-links-container">
                    <h6>Investors</h6>
                    <a href="#">Investor Hub</a>
                    <a href="#">Corporate Governance</a>
                    <a href="#">Investor Resource Center</a>
                </div>

                <div class="company-links-container">
                    <h6>News & Media</h6>
                    <a href="#">Our Latest Updates</a>
                    <a href="#">Media Releases</a>
                    <a href="#">Featured Stories</a>
                    <a href="#">Blogs</a>
                    <a href="#">Media Kit</a>
                </div>
            </div>
        </div>
        <div class="bottom-footer">
            <small>Copyright TCS © 2025</small>
            <div>
                <a href="#">
                    <img src="assets/images/facebook.png" alt="Facebook" class="social-icon" />
                </a>
                <a href="#">
                    <img src="assets/images/instagram.png" alt="Instagram" class="social-icon" />
                </a>
                <a href="#">
                    <img src="assets/images/linkedin.png" alt="LinkedIn" class="social-icon" />
                </a>
                <a href="#">
                    <img src="assets/images/youtube.png" alt="YouTube" class="social-icon" />
                </a>
            </div>
        </div>

    </footer>
    <script src="scripts/master.js" defer></script>
    <script src="scripts/index.js" defer></script>
</body>

</html>