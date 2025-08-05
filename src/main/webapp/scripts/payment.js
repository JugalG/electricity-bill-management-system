let submitButton = document.getElementById('credit-card-form-submit');

let cardNumberCorrect = false;
let expiryDateCorrect = false;
let cvvCorrect = false;
let cardHolderNameCorrect = false;

const tryToEnableSubmit = () => {
    if (cardNumberCorrect && expiryDateCorrect && cvvCorrect && cardHolderNameCorrect) {
        submitButton.removeAttribute('disabled');
    } else {
        submitButton.setAttribute('disabled', true);
    }
};

const checkCardNumberConstraints = () => {
    const cardNumberField = document.getElementById('card-number');
    const constraintText = document.getElementById('card-number-constraint');
    const cardNumber = cardNumberField.value.trim();

    // Regular expressions for card types
    const visaRegex = /^4[0-9]{15}$/; // Visa: Starts with 4, 16 digits
    const masterCardRegex = /^5[1-5][0-9]{14}$/; // MasterCard: Starts with 51-55, 16 digits
    const ruPayRegex = /^(60[0-9]{14}|6521[0-9]{12}|6522[0-9]{12})$/; // RuPay: Specific BINs, 16 digits

    if (visaRegex.test(cardNumber)) {
        cardNumberField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Valid Visa card number.';
        cardNumberCorrect = true;
    } else if (masterCardRegex.test(cardNumber)) {
        cardNumberField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Valid MasterCard number.';
        cardNumberCorrect = true;
    } else if (ruPayRegex.test(cardNumber)) {
        cardNumberField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Valid RuPay card number.';
        cardNumberCorrect = true;
    } else {
        cardNumberField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Invalid card number. Please enter a valid 16-digit Visa, MasterCard, or RuPay card number.';
        cardNumberCorrect = false;
    }

    tryToEnableSubmit();
};

const checkExpiryDateConstraints = () => {
    const expiryMonthField = document.getElementById('expiry-month');
    const expiryYearField = document.getElementById('expiry-year');
    const constraintTextMonth = document.getElementById('expiry-constraint-month');
	const constraintTextYear = document.getElementById('expiry-constraint-year');

    const currentDate = new Date();
    const currentMonth = currentDate.getMonth() + 1; // Months are 0-indexed in JavaScript
    const currentYear = parseInt(currentDate.getFullYear().toString().slice(2)); // Get last two digits of the year

    const expiryMonth = parseInt(expiryMonthField.value);
    const expiryYear = parseInt(expiryYearField.value);

    if (isNaN(expiryMonth) || isNaN(expiryYear)) {
		expiryMonthField.setAttribute('aria-invalid', 'true');
		expiryYearField.setAttribute('aria-invalid', 'true');
        constraintTextMonth.innerText = 'Please select a valid month and year.';
		constraintTextYear.innerText = 'Please select a valid month and year.';
        expiryDateCorrect = false;
    } else if (expiryYear < currentYear || (expiryYear === currentYear && expiryMonth < currentMonth)) {
		expiryMonthField.setAttribute('aria-invalid', 'true');
		expiryYearField.setAttribute('aria-invalid', 'true');
        constraintTextMonth.innerText = 'Please select a month and year in the future.';
		constraintTextYear.innerText = 'Please select a month and year in the future.';
        expiryDateCorrect = false;
    } else {
		expiryMonthField.setAttribute('aria-invalid', 'false');
		expiryYearField.setAttribute('aria-invalid', 'false');
        constraintTextMonth.innerText = 'Valid expiry date.';
		constraintTextYear.innerText = 'Valid expiry date.';
        expiryDateCorrect = true;
    }

    tryToEnableSubmit();
};

// Attach event listeners for real-time validation
document.getElementById('expiry-month').addEventListener('change', checkExpiryDateConstraints);
document.getElementById('expiry-year').addEventListener('input', checkExpiryDateConstraints);

const checkCvvConstraints = () => {
    const cvvField = document.getElementById('cvv');
    const constraintText = document.getElementById('cvv-constraint');

    let cvv = cvvField.value;

    // Limit input to 3 digits
    if (cvv.length > 3) {
        cvv = cvv.slice(0, 3);
        cvvField.value = cvv;
    }

    // Validate range
    if (cvv >= 100 && cvv <= 999) {
        cvvField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        cvvCorrect = true;
    } else {
        cvvField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'CVV must be exactly 3 digits.';
        cvvCorrect = false;
    }

    tryToEnableSubmit();
};

// Attach event listener
document.getElementById('cvv').addEventListener('input', checkCvvConstraints);

const checkNameIntermediate = (name) => {
	const matchesNameRegex = /^(?!\s*$)[A-Za-z]+(?: [A-Za-z]+)*$/.test(name);
	const isNotTooLong = name.length <= 50;
	const isNotTooShort = name.length >= 3;
	
	return matchesNameRegex && isNotTooLong && isNotTooShort;
}

const checkCardHolderName = () => {
    let nameField = document.getElementById('card-holder-name');
    let constraintText = document.getElementById('name-constraint');

    if (checkNameIntermediate(nameField.value)) {
        nameField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks Good!';
        cardHolderNameCorrect = true;
    } else {
        nameField.setAttribute('aria-invalid', 'true');
        constraintText.innerText = 'Name must be 10-50 characters long and can only contain letters and spaces.';
        cardHolderNameCorrect = false;
    }

    tryToEnableSubmit();
};


const downloadPDF = () => {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    // Get the table element
    const table = document.getElementById('transaction-table');
	const transactionID = document.getElementById('transaction-id').innerText;

    let newTable = []
    let newTableIndex = 0;

    // Convert the table to a string
    let tableString = '';
    for (let row of table.rows) {
        for (let cell of row.cells) {
            if (newTable[newTableIndex] == null) {
                newTable[newTableIndex] = cell.innerText + '\t\t';
            } else {
                newTable[newTableIndex] += cell.innerText + '\n';
            }
            newTableIndex++;
        }
        newTableIndex = 0;
    }

    // Format PDF
	doc.text('==========================================================', 10, 10);
	doc.text('Tata Power', 10, 20);
    doc.text('==========================================================', 10, 30);
    doc.text('Transaction Details', 10, 40);
    doc.text('==========================================================', 10, 50);

    // Add the table to the PDF
    for (let line in newTable) {
        tableString += newTable[line];
    }

    // Add the table string to the PDF
    doc.text(tableString, 10, 60);
    doc.text('==========================================================', 10, 100);
    doc.text('Thank you for using our service.', 10, 110);
    doc.text('==========================================================', 10, 120);

    // Save the PDF
    doc.save(`Transaction_${transactionID}.pdf`);
};

const closeDialog = () => {
	window.location.replace('home');
};