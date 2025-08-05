document.addEventListener("DOMContentLoaded", function () {
    const complaintTypeElement = document.getElementById("complaint-type");
    const complaintCategoryElement = document.getElementById("complaint-category");

    // Map of complaint types to their respective categories
    const complaintCategories = {
		all: ["All"],
        billing: ["All", "High Bill", "Low Bill", "No Bill"],
        voltage: ["All", "Voltage Fluctuation", "No Voltage", "Low Voltage", "High Voltage"],
        disruption: ["All", "Frequent Disruption", "No Disruption"],
        streetlight: ["All", "Street Light Not Working", "Street Light Damaged"],
        pole: ["All", "Pole Tilted", "Pole Damaged"],
    };

    // Retrieve the selected category from JSP
    const selectedCategory = `<%= selectedComplaintCategory != null ? selectedComplaintCategory : "" %>`;

    // Function to update categories based on the selected complaint type
    function updateComplaintCategories() {
        const selectedType = complaintTypeElement.value;

        // Clear previous options
        complaintCategoryElement.innerHTML = "<option value='' disabled>Select Category</option>";

        // Populate new categories
        if (complaintCategories[selectedType]) {
            complaintCategories[selectedType].forEach((category) => {
                const option = document.createElement("option");
                option.value = category.toLowerCase().replace(/\s+/g, "-");
                option.textContent = category;

                // Set the option as selected if it matches the selected category
                if (option.value === selectedCategory) {
                    option.selected = true;
                }

                complaintCategoryElement.appendChild(option);
            });
        }
    }

    // Event listener for complaint type change
    complaintTypeElement.addEventListener("change", updateComplaintCategories);

    // Initial population of categories
    updateComplaintCategories();
});

const changeComplaintStatus = (complaintID) => {
	const url = `api/complaint/status?complaintID=${complaintID}`;
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
				alert('Complaint status changed successfully');
				window.location.replace('complaints');
			} else {
				alert('Failed to change complaint status');
			}
		});
}
