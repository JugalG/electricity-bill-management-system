const dialog = document.querySelector("dialog");
const closeButton = document.querySelector("dialog button");

let submitButton = document.getElementById('rgstr-form-submit');

let nameCorrect = false;
let mobileCorrect = false;
let emailCorrect = false;
let consumerNumberGenerated = false;

const tryToEnableSubmit = () => {
    if (nameCorrect == true && mobileCorrect == true && emailCorrect == true && consumerNumberGenerated == true) {
        submitButton.removeAttribute('disabled');
    }
};

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

const generateNewUniqueConsumerID = () => {
    let consumerNumberField = document.getElementById('user-consumer-number');

    // Disable the input field to prevent further interaction while the request is processing
    consumerNumberField.disabled = true;

    // Show some loading feedback, if needed
    consumerNumberField.placeholder = "Generating...";

    // Make the GET request to the backend to generate a new unique consumer ID
    fetch('api/customer/new', {  // Update with the actual backend URL
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    })
    .then(response => response.json())  // Assuming the backend returns a JSON response
    .then(data => {
        // If the backend successfully generates and returns the consumer ID
        if (data && data.CustomerID) {
            consumerNumberField.value = data.CustomerID;
		
			consumerNumberField.disabled = false;  // Re-enable the input field
            consumerNumberGenerated = true;  // Mark the consumer number as generated
            consumerNumberField.placeholder = "Consumer Number Generated";  // Update placeholder text
			
            tryToEnableSubmit();  // Check if we can enable the submit button now
        } else {
            console.error("Failed to generate consumer ID");
            // Handle error, display a message, or try again
            consumerNumberField.placeholder = "Failed to generate. Try again.";
        }
    })
    .catch(error => {
        console.error("Error while generating consumer ID:", error);
        // Handle network error or other issues
        consumerNumberField.placeholder = "Error. Try again.";
        consumerNumberField.disabled = false;
    });
};

closeButton.addEventListener("click", () => {
    dialog.close();
    window.location.replace('admin-register-customer');
});
