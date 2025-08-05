const billCheckers = document.querySelectorAll('.billchecker');
const payButton = document.getElementById('pay-button');
const totalBillText = document.getElementById('total-bill');
const totalBillHiddenInput = document.getElementById('total-bill-hidden');

let totalAmount = 0;

const calculateTotalBill = (e) => {
    totalAmount = 0;
    // Whenever a checkbox is changed, calculate the total amount
    billCheckers.forEach(check => {
        if (check.checked) {
            totalAmount += parseInt(check.getAttribute('data-amount'));
        }
    });

    totalBillText.innerText = totalAmount.toFixed(2);
	totalBillHiddenInput.value = totalAmount.toFixed(2);

    if (totalAmount > 0) {
        payButton.removeAttribute('disabled');
    } else {
        payButton.setAttribute('disabled', true);
    }
}

billCheckers.forEach(check => {
    check.addEventListener('change', calculateTotalBill);
    
});