const reactivateAccount = (customerID) => {
	const url = `api/customer/reactivate?customerID=${customerID}`;
	const options = {
		method: 'GET',
		headers: {
			'Content-Type': 'application/json',
		},
	};
	fetch(url, options)
		.then((response) => response.json())
		.then((data) => {
			if (data.success) {
				alert('Account reactivated successfully');
				window.location.replace('index.jsp');
			} else {
				alert('Failed to reactivate account');
			}
		});
};
		