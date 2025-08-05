const dialog = document.querySelector("dialog");
const closeButton = document.querySelector("dialog button");
let submitButton = document.getElementById('rgstr-form-submit');

let uniqueUser = false;
let passwordMatch = false;
let nameCorrect = false;
let mobileCorrect = false;
let emailCorrect = false;
let consumerNumberCorrect = false;

const tryToEnableSubmit = () => {
    if (uniqueUser == true && passwordMatch == true && nameCorrect == true && mobileCorrect == true && emailCorrect == true && consumerNumberCorrect == true) {
        submitButton.removeAttribute('disabled');
    }
};

let passwordField = document.getElementById('user-password');
const checkPasswordIntermediate = (password) => {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /\d/.test(password);
    const hasSpecialChar = /[@#$%^&+=!]/.test(password);

    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
}

const checkPasswordConstraints = () => {
    let constraintText = document.getElementById('password-constraint');

    if (checkPasswordIntermediate(passwordField.value)) {
        passwordField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
    } else {
        passwordField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Password should have an Uppercase Character, a Lowercase Character, a Number and a Special Character (@#$%^&+=!), and be at least 8 characters.';
    }
	
	checkPasswordMatch();
}

const checkPasswordMatch = () => {
    let confirmPasswordField = document.getElementById('user-confirm-password');
    let matchText = document.getElementById('password-matcher');

    if (passwordField.value == confirmPasswordField.value && checkPasswordIntermediate(passwordField.value)) {
        confirmPasswordField.setAttribute('aria-invalid', 'false');
        matchText.innerText = 'Passwords match!';
        passwordMatch = true;
		tryToEnableSubmit();
    } else {
        confirmPasswordField.setAttribute('aria-invalid', 'true');
        matchText.innerText = 'Passwords do not match!';
        passwordMatch = false;
        submitButton.setAttribute('disabled', true);
    }
}

const checkNameIntermediate = (name) => {
	const matchesNameRegex = /^(?!\s*$)[A-Za-z]+(?: [A-Za-z]+)*$/.test(name);
	const isNotTooLong = name.length <= 50;
	const isNotTooShort = name.length >= 3;
	
	return matchesNameRegex && isNotTooLong && isNotTooShort;
}

const checkNameConstraints = () => {
	let nameField = document.getElementById('user-name');
	let constraintText = document.getElementById('name-constraint');

    if (checkNameIntermediate(nameField.value)) {
        nameField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        nameCorrect = true;
        tryToEnableSubmit();
    } else {
        nameField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Name should contain only alphabets and spaces, and be at least 3 characters long. Must not start or end with spaces.';
        nameCorrect = false;
        submitButton.setAttribute('disabled', true);
    }
}

const checkMobileIntermediate = (mobile) => {
    const matchesMobileRegex = /^[6-9](?!(\d)\1{4})[0-9]{9}$/.test(mobile);
    const isCorrectLength = mobile.length === 10;

    return matchesMobileRegex && isCorrectLength;
};

const checkMobileConstraints = () => {
    let mobileField = document.getElementById('user-mobile');
    let constraintText = document.getElementById('mobile-constraint');

    if (checkMobileIntermediate(mobileField.value)) {
        mobileField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        mobileCorrect = true;
        tryToEnableSubmit();
    } else {
        mobileField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Mobile number should start with 6-9, must be 10 digits long, and cannot have 5 consecutive identical digits.';
        mobileCorrect = false;
        submitButton.setAttribute('disabled', true);
    }
};

const checkEmailIntermediate = (email) => {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
};

const checkEmailConstraints = () => {
    let emailField = document.getElementById('user-email');
    let constraintText = document.getElementById('email-constraint');

    if (checkEmailIntermediate(emailField.value)) {
        emailField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        emailCorrect = true;
        tryToEnableSubmit();
    } else {
        emailField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Please enter a valid email address (e.g., user@example.com).';
        emailCorrect = false;
        submitButton.setAttribute('disabled', true);
    }
};

const checkConsumerNumberIntermediate = (consumerNumber) => {
    const consumerNumberRegex = /^[1-9][0-9]{12}$/; // Ensures 13-digit numbers starting with 1-9
    const isCorrectLength = consumerNumber.length === 13;

    return consumerNumberRegex.test(consumerNumber) && isCorrectLength;
};

const checkConsumerNumberConstraints = () => {
    let consumerNumberField = document.getElementById('user-consumer-number');
    let constraintText = document.getElementById('consumer-number-constraint');
    let populateButton = document.getElementById('populate-details-button');

    // Check the consumer number validity (13 digits starting with non-zero)
    if (checkConsumerNumberIntermediate(consumerNumberField.value)) {
        consumerNumberField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        consumerNumberCorrect = true;

        populateButton.removeAttribute('disabled');        
        tryToEnableSubmit();
    } else {
        consumerNumberField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Consumer number must be a 13-digit number starting with a non-zero digit.';
        consumerNumberCorrect = false;

        populateButton.setAttribute('disabled', true);
        submitButton.setAttribute('disabled', true);
    }
};


const resetUserIdValidation = () => { 
    let userIdField = document.getElementById('user-userid');
    let availabilityText = document.getElementById('user-uniqueness');
    let userId = userIdField.value;
    let checkButton = document.getElementById('check-availability-button');

    // Reset the flag and disable the submit button
    uniqueUser = false;
    submitButton.setAttribute('disabled', true);

    // Pattern and length checks
    const userIdPattern = /^[A-Za-z][A-Za-z0-9]{4,19}$/;
    const forbiddenAdminPattern = /^admin(\d+)?$/; 

    if (userId.length < 5) {
        availabilityText.innerText = 'User ID should be at least 5 characters long.';
        checkButton.setAttribute('disabled', true);
    } else if (!userIdPattern.test(userId)) {
        availabilityText.innerText = 'User ID must start with a letter and can only contain letters and numbers.';
        checkButton.setAttribute('disabled', true);
    } else if (forbiddenAdminPattern.test(userId.toLowerCase())) { // Convert to lowercase to ensure case-insensitivity
        availabilityText.innerText = 'That username is forbidden.';
        checkButton.setAttribute('disabled', true);
    } else {
        availabilityText.innerText = 'Looks good, but User ID must be unique. Click below to check for availability.';
        checkButton.removeAttribute('disabled');
    }

    userIdField.setAttribute('aria-invalid', 'true');
};


const checkUserIdAvailability = () => {
	let userIdField = document.getElementById('user-userid');
    let userId = userIdField.value;
    let availabilityText = document.getElementById('user-uniqueness');

    if (userId.length < 5) {
        availabilityText.innerText = 'User ID should be at least 5 characters long.';
        submitButton.setAttribute('disabled', true);
        return;
    }

    fetch('api/username?checkUserId=' + userId)
        .then(response => response.text())
        .then(data => {
            if (JSON.parse(data).available) {
                availabilityText.innerText = 'User ID available!';
				userIdField.setAttribute('aria-invalid', 'false');
                uniqueUser = true;
                tryToEnableSubmit();
            } else {
                availabilityText.innerText = 'User ID already taken!';
				userIdField.setAttribute('aria-invalid', 'true');
                uniqueUser = false;
                submitButton.setAttribute('disabled', true);
            }
        });
};

const populateDetails = async () => {
	let consumerIDField = document.getElementById('user-consumer-number');
	let consumerID = consumerIDField.value;
	let availabilityText = document.getElementById('consumer-number-constraint');

	// Validate the consumer ID length first
	if (consumerID.length !== 13) {
		availabilityText.innerText = 'Consumer ID must be exactly 13 digits.';
		return;
	}

	try {
		// Making the HTTP GET request to fetch the customer details
		let response = await fetch('api/customer?customerID=' + encodeURIComponent(consumerID));

		// Check if the response status is not OK (e.g., 404, 500)
		if (!response.ok) {
			let errorData = await response.json();
			throw new Error(errorData.message || 'Failed to fetch customer details.');
		}

		// Parse the JSON data
		let data = await response.json();

		// Check if the customer data exists
		if (data && data.customerID) {
			// Populate the form with the returned customer data
			document.getElementById('user-name').value = data.customerName || '';
			document.getElementById('user-email').value = data.email || '';
			document.getElementById('user-mobile').value = data.mobileNumber || '';

			// Set the title if available
			if (data.title) {
				document.getElementById('user-title').value = data.title;
			}
			
			// Call their respective constraint checks
			checkNameConstraints();
			checkEmailConstraints();
			checkMobileConstraints();

			// Provide feedback to the user
			consumerIDField.setAttribute('aria-invalid', 'false');
			availabilityText.innerText = 'Customer details successfully populated!';
		} else {
			// If the customer data is not found
			consumerIDField.setAttribute('aria-invalid', 'true');
			availabilityText.innerText = 'This consumer ID does not exist in our system. Please check the ID again carefully.';
		}
	} catch (error) {
		// Handle any errors from the fetch or JSON parsing
		console.error('Error fetching consumer details:', error);
		consumerIDField.setAttribute('aria-invalid', 'true');
		availabilityText.innerText = error.message || 'An unexpected error occurred. Please try again later.';
	}
};


closeButton.addEventListener("click", () => {
    dialog.close();
    window.location.replace('index.jsp');
});