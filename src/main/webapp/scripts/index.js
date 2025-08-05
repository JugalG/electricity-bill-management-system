const checkForAdminNamespace = () => {
    const userIdField = document.getElementById('login-user-id');
    const loginCard = document.getElementById('login-card');
    const fieldset = loginCard.querySelector('fieldset'); // Select the fieldset inside the card
	const legend = fieldset.querySelector('legend');
	
	const forbiddenAdminPattern = /^admin(\d+)?$/;
	
    if (forbiddenAdminPattern.test(userIdField.value)) {
        // Admin detected: Change the card background and fieldset border
        loginCard.style.backgroundColor = 'var(--pico-mark-background-color)';
        fieldset.style.border = '2px solid var(--pico-color)'; // Add a distinct border 
		legend.textContent = 'Welcome, Admin!';
    } else {
        // Default state: Reset to original styles
        loginCard.style.backgroundColor = 'var(--pico-background-color)';
        fieldset.style.border = '2px solid var(--pico-muted-border-color)'; // Reset the border
		legend.textContent = 'Existing User/Admin';
    }
};
