let dialog = document.getElementById('dialog');
let closeButton = document.getElementById('dialog-close-button');
let submitButton = document.getElementById('generate-bill-form-submit');

let unitsField = document.getElementById('units-consumed');
let additionalChargesField = document.getElementById('additional-charges');

let consumerNumberCorrect = false;
let unitsCorrect = false;
let billDateCorrect = false;
let additionalChargesCorrect = true;
let totalAmountCorrect = false;

const tryToEnableSubmit = () => {
    if (consumerNumberCorrect && unitsCorrect && billDateCorrect && additionalChargesCorrect && totalAmountCorrect) {
        submitButton.removeAttribute('disabled');
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

    // Check the consumer number validity (13 digits starting with non-zero)
    if (checkConsumerNumberIntermediate(consumerNumberField.value)) {
        consumerNumberField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        consumerNumberCorrect = true;
     
        tryToEnableSubmit();
    } else {
        consumerNumberField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Consumer number must be a 13-digit number starting with a non-zero digit.';
        consumerNumberCorrect = false;

        submitButton.setAttribute('disabled', true);
    }
};

const checkUnitsConsumedConstraints = () => {
    let unitsField = document.getElementById('units-consumed');
    let constraintText = document.getElementById('units-consumed-constraint');

    // Convert input to a number for validation
    let units = unitsField.value.trim(); // Trim the input to avoid spaces

    // Check if the field is empty
    if (units === "") {
        unitsField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Units consumed cannot be empty.';
        unitsCorrect = false;
        submitButton.setAttribute('disabled', true);
        return;
    }

    // Convert to a number after checking it's not empty
    units = Number(units);

    // Check validity
    if (!isNaN(units) && units >= 0 && units <= 100000) {
        unitsField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        unitsCorrect = true;

        tryToEnableSubmit();
    } else {
        unitsField.setAttribute('aria-invalid', 'true');
        if (units < 0) {
            constraintText.innerText = 'Units cannot be negative.';
        } else if (units > 100000) {
            constraintText.innerText = 'Units cannot exceed 100,000.';
        } else {
            constraintText.innerText = 'Please enter a valid number for units consumed.';
        }
        unitsCorrect = false;

        submitButton.setAttribute('disabled', true);
    }
};


// Add an event listener for the "Units Consumed" field
document.getElementById('units-consumed').addEventListener('input', checkUnitsConsumedConstraints);

const checkBillDateConstraints = () => {
    let billDateField = document.getElementById('bill-date');
    let constraintText = document.getElementById('bill-date-constraint');
    const today = new Date().toISOString().split('T')[0]; // Get today's date in YYYY-MM-DD format

    // Check if the bill date is not a future date
    if (billDateField.value > today) {
        billDateField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Bill date cannot be a future date.';
        billDateCorrect = false;
		submitButton.setAttribute('disabled', true);
	} else if (billDateField.value === '') {
		billDateField.setAttribute('aria-invalid', 'true');
		constraintText.innerText = 'Bill date cannot be empty.';
		billDateCorrect = false;
		submitButton.setAttribute('disabled', true);
	} else if (new Date(billDateField.value) < new Date(new Date().setMonth(new Date().getMonth() - 3))) {
		billDateField.setAttribute('aria-invalid', 'true');
		constraintText.innerText = 'Bill date cannot be more than three months old.';
		billDateCorrect = false;
		submitButton.setAttribute('disabled', true);
    } else {
        billDateField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        billDateCorrect = true;
    }

    tryToEnableSubmit();
};

// Add an event listener for the "Bill Date" field
document.getElementById('bill-date').addEventListener('input', checkBillDateConstraints);

const checkAdditionalChargesConstraints = () => {
    let additionalChargesField = document.getElementById('additional-charges');
    let constraintText = document.getElementById('additional-charges-constraint');

    const additionalCharges = parseFloat(additionalChargesField.value);

    // Check if the value matches the valid currency format (up to two decimal places)
    const validCurrencyFormat = /^\d+(\.\d{1,2})?$/;

    if (isNaN(additionalCharges) || additionalCharges < 0) {
        additionalChargesField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Additional charges must be a valid positive number.';
        additionalChargesCorrect = false;
        submitButton.setAttribute('disabled', true);
    } else if (!validCurrencyFormat.test(additionalChargesField.value)) {
        additionalChargesField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Additional charges must have no more than two decimal places.';
        additionalChargesCorrect = false;
        submitButton.setAttribute('disabled', true);
    } else if (additionalCharges > 100000000) {
        additionalChargesField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Additional charges cannot exceed 100,000,000.';
        additionalChargesCorrect = false;
        submitButton.setAttribute('disabled', true);
    } else {
        additionalChargesField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        additionalChargesCorrect = true;
    }

    tryToEnableSubmit();
};

// Add an event listener for the "Additional Charges" field
document.getElementById('additional-charges').addEventListener('input', checkAdditionalChargesConstraints);

const calculateTotalAmount = () => {
    let totalAmountField = document.getElementById('total-amount');

    const unitsConsumed = parseFloat(unitsField.value);
    const additionalCharges = parseFloat(additionalChargesField.value);

    let energyCharges = 0;
    if (unitsConsumed <= 100) {
        energyCharges = unitsConsumed * 2.18;
    } else if (unitsConsumed <= 300) {
        energyCharges = 100 * 2.18 + (unitsConsumed - 100) * 5.36;
    } else if (unitsConsumed <= 500) {
        energyCharges = 100 * 2.18 + 200 * 5.36 + (unitsConsumed - 300) * 11.62;
    } else {
        energyCharges = 100 * 2.18 + 200 * 5.36 + 200 * 11.62 + (unitsConsumed - 500) * 12.56;
    }

    const cssCharges = unitsConsumed * 0.01;
    const wheelingCharges = unitsConsumed * 2.60;
    const toseCharges = unitsConsumed * 0.26;
    const fixedDemandCharges = 160;

    const totalAmount = energyCharges + cssCharges + wheelingCharges + toseCharges + fixedDemandCharges + additionalCharges;

    // Set the calculated total amount to the readonly field
    totalAmountField.value = totalAmount.toFixed(2);

    // Check if the total amount is valid and update the correctness flag
    if (!isNaN(totalAmount) && totalAmount >= 0) {
        totalAmountCorrect = true;
    } else {
        totalAmountCorrect = false;
		submitButton.setAttribute('disabled', true);
    }

    tryToEnableSubmit();
};

// Add an event listener for the "Units Consumed" field
document.getElementById('units-consumed').addEventListener('input', calculateTotalAmount);
document.getElementById('additional-charges').addEventListener('input', calculateTotalAmount);


closeButton.addEventListener('click', () => {
	dialog.close();
	window.location.replace('generate-bill');
});

