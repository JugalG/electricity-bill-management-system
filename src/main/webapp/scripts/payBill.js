let totalBill = document.getElementById('bill-amount');
let pgCharge = document.getElementById('pg-charge');
let totalPayable = document.getElementById('total-payable');
let totalPayableHidden = document.getElementById('total-payable-hidden');


// When document loads, calculate pg charge as 2% of total bill

document.addEventListener('DOMContentLoaded', () => {
	pgCharge.innerHTML = (totalBill.innerHTML * 0.02).toFixed(2);
	totalPayable.innerHTML = (parseFloat(pgCharge.innerHTML) + parseFloat(totalBill.innerHTML)).toFixed(2);
	totalPayableHidden.value = totalPayable.innerHTML;
});