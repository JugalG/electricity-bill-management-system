const successDialog = document.getElementById("success-dialog");
const closeSuccessDialogButton = document.getElementById("success-dialog-button");

const passwordSuccessDialog = document.getElementById("password-success-dialog");
const closePasswordSuccessDialogButton = document.getElementById("password-success-dialog-button");

const deactivateConfirmationDialog = document.getElementById('deactivate-confirmation-dialog');
const closeDeactivateDialogButton = document.getElementById('close-deactivate-dialog');

const deactivateSuccessDialog = document.getElementById('deactivate-success-dialog');

const detailsSubmitButton = document.getElementById('update-form-submit');

let nameCorrect = true;
let mobileCorrect = true;
let emailCorrect = true;
let uniqueUser = true;

const tryToEnableSubmit = () => {
    if (mobileCorrect && nameCorrect && emailCorrect && uniqueUser) {
        detailsSubmitButton.removeAttribute('disabled');
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
        detailsSubmitButton.setAttribute('disabled', true);
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
        detailsSubmitButton.setAttribute('disabled', true);
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
        detailsSubmitButton.setAttribute('disabled', true);
    }
};

const resetUserIdValidation = () => { 
    let userIdField = document.getElementById('user-userid');
    let availabilityText = document.getElementById('user-uniqueness');
    let userId = userIdField.value;
    let checkButton = document.getElementById('check-availability-button');

    // Reset the flag and disable the submit button
    uniqueUser = false;
    detailsSubmitButton.setAttribute('disabled', true);

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
                detailsSubmitButton.setAttribute('disabled', true);
            }
        });
};

const updateFormReset = document.getElementById("update-form-reset");
updateFormReset.addEventListener('click', () => {
	detailsSubmitButton.setAttribute('disabled', true);
});

const passwordSubmitButton = document.getElementById("password-form-submit");

const checkPasswordIntermediate = (password) => {
    const minLength = 8;
    const hasUpperCase = /[A-Z]/.test(password);
    const hasLowerCase = /[a-z]/.test(password);
    const hasNumber = /\d/.test(password);
    const hasSpecialChar = /[@#$%^&+=!]/.test(password);

    return password.length >= minLength && hasUpperCase && hasLowerCase && hasNumber && hasSpecialChar;
}

let passwordField = document.getElementById('user-password');
const checkPasswordConstraints = () => {
    let constraintText = document.getElementById('password-constraint');
    let oldPasswordField = document.getElementById('old-password');
	console.log(passwordField.value == oldPasswordField.value);
	
	if (passwordField.value == oldPasswordField.value) {
        passwordField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'New password cannot be the same as old password';
    } else if (checkPasswordIntermediate(passwordField.value)) {
        passwordField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!'
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
        matchText.innerText = 'Passwords match!'
        passwordSubmitButton.removeAttribute('disabled');
    } else {
        confirmPasswordField.setAttribute('aria-invalid', 'true');
        matchText.innerText = 'Passwords do not match!'
        passwordSubmitButton.setAttribute('disabled', true);
    }
}

const passwordFormReset = document.getElementById("password-form-reset");
passwordFormReset.addEventListener('click', () => {
	passwordSubmitButton.setAttribute('disabled', true);
	checkPasswordConstraints();
});

closeSuccessDialogButton.addEventListener("click", () => {
    successDialog.close();
    window.location.replace('updateuser');
});

closePasswordSuccessDialogButton.addEventListener("click", () => {
	passwordSuccessDialog.close();
	window.location.replace('updateuser');
});

closeDeactivateDialogButton.addEventListener("click", () => {
	deactivateConfirmationDialog.close();
});

const closeDeactivateSuccessDialog = () => {
	deactivateSuccessDialog.close();
	window.location.replace('logout');
}

const closeDeactivateFailureDialog = () => {
	deactivateSuccessDialog.close();
}

const openDeactivateDialog = () => {
	deactivateConfirmationDialog.showModal();
}
