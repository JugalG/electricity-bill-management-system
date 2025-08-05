// Theme toggle using html data-theme 
const themeToggleButton = document.getElementById('theme-toggle');

// Function to toggle the theme
const toggleTheme = () => {
    let currentTheme = document.documentElement.getAttribute('data-theme');
    let newTheme = currentTheme === 'dark' ? 'light' : 'dark';
    
    // Update the button icon based on the theme
    if (newTheme === 'dark') {
        themeToggleButton.innerHTML = '<i class="fas fa-sun" style="color: #FFFFFF"></i>';
    } else {
        themeToggleButton.innerHTML = '<i class="fas fa-moon" style="color: #000000"></i>';
    }

    // Apply the new theme
    document.documentElement.setAttribute('data-theme', newTheme);
    localStorage.setItem('theme', newTheme); // Save the preference in localStorage
};

// Function to initialize the theme on page load
const initializeTheme = () => {
    // Get the stored theme preference from localStorage
    let storedTheme = localStorage.getItem('theme');
    
    // If no preference is stored, default to light
    if (!storedTheme) {
        storedTheme = 'light';
    }

    // Apply the stored theme
    document.documentElement.setAttribute('data-theme', storedTheme);

    // Update the button icon to match the current theme
    if (storedTheme === 'dark') {
        themeToggleButton.innerHTML = '<i class="fas fa-sun" style="color: #FFFFFF"></i>';
    } else {
        themeToggleButton.innerHTML = '<i class="fas fa-moon" style="color: #000000"></i>';
    }
};

// Initialize the theme on page load
initializeTheme();