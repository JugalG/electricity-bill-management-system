const dialog = document.querySelector("dialog");
const closeButton = document.querySelector("dialog button");

let submitButton = document.getElementById('complaint-form-submit');

let mobileCorrect = false;
let descriptionCorrect = false;
let landmarkCorrect = false;
let addressCorrect = false;

const tryToEnableSubmit = () => {
    if (mobileCorrect && descriptionCorrect && landmarkCorrect && addressCorrect) {
        submitButton.removeAttribute('disabled');
    }
};

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

const checkDescriptionIntermediate = (description) => {
    const isValidLength = description.length >= 10 && description.length <= 200;
    const hasNoExcessiveWhitespace = !/\s{2,}/.test(description);
    const hasNoSpecialChars = /^(?=(.*[A-Za-z]){5})[A-Za-z0-9\s.,!?'-()<>-]*$/.test(description); 

    return isValidLength && hasNoExcessiveWhitespace && hasNoSpecialChars;
};

const checkDescriptionConstraints = () => {
    let descriptionField = document.getElementById('problem-description');
    let constraintText = document.getElementById('description-constraint');

    if (checkDescriptionIntermediate(descriptionField.value)) {
        descriptionField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Description looks good!';
        descriptionCorrect = true;
        tryToEnableSubmit();
    } else {
        descriptionField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Description must be between 10 and 200 characters, with no consecutive spaces or invalid characters (only alphanumeric, spaces, and common punctuation).';
        descriptionCorrect = false;
        submitButton.setAttribute('disabled', true);
    }
};

const checkLandmarkIntermediate = (landmark) => {
    const isValidLength = landmark.length >= 5 && landmark.length <= 100;
    const hasNoSpecialChars = /^(?=(.*[A-Za-z]){5})[A-Za-z0-9\s.,!?'-()<>-]*$/.test(landmark); // Alphanumeric with spaces and some common punctuation

    return isValidLength && hasNoSpecialChars;
};

const checkLandmarkConstraints = () => {
    let landmarkField = document.getElementById('landmark');
    let constraintText = document.getElementById('landmark-constraint');

    if (checkLandmarkIntermediate(landmarkField.value)) {
        landmarkField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks good!';
        landmarkCorrect = true;
        tryToEnableSubmit();
    } else {
        landmarkField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Landmark must be between 5 and 100 characters and can only contain alphanumeric characters, spaces, and common punctuation like commas and hyphens.';
        landmarkCorrect = false;
        submitButton.setAttribute('disabled', true);
    }
};

const checkAddressIntermediate = (address) => {
    const isValidLength = address.length >= 10 && address.length <= 300; // Address between 10-300 characters
    const hasValidCharacters = /^(?=(.*[A-Za-z]){5})[A-Za-z0-9\s.,!?'-()<>-]*$/.test(address); // Alphanumeric, spaces, commas, periods, slashes, hyphens

    return isValidLength && hasValidCharacters;
};

const checkAddressConstraints = () => {
    let addressField = document.getElementById('address');
    let constraintText = document.getElementById('address-constraint');

    if (checkAddressIntermediate(addressField.value)) {
        addressField.setAttribute('aria-invalid', 'false');
        constraintText.innerText = 'Looks good!';
        addressCorrect = true;
        tryToEnableSubmit();
    } else {
        addressField.setAttribute('aria-invalid', 'true');
        constraintText.innerHTML = 'Address must be between 10 and 300 characters, and can only contain alphanumeric characters, spaces, commas, periods, slashes, and hyphens.';
        addressCorrect = false;
        submitButton.setAttribute('disabled', true);
    }
};

closeButton.addEventListener("click", () => {
    dialog.close();
    window.location.replace('mycomplaints');
});

document.addEventListener("DOMContentLoaded", function () {
  const complaintTypeElement = document.getElementById("complaint-type");
  const complaintCategoryElement = document.getElementById("complaint-category");
  
  // Map of complaint types to their respective categories
  const complaintCategories = {
    billing: ["High Bill", "Low Bill", "No Bill"],
    voltage: ["Voltage Fluctuation", "No Voltage", "Low Voltage", "High Voltage"],
    disruption: ["Frequent Disruption", "No Disruption"],
    streetlight: ["Street Light Not Working", "Street Light Damaged"],
    pole: ["Pole Tilted", "Pole Damaged"],
  };

  // Function to update categories based on the selected complaint type
  function updateComplaintCategories() {
    const selectedType = complaintTypeElement.value;

    // Clear previous options
    complaintCategoryElement.innerHTML = "<option value='' disabled selected>Select Category</option>";

    // Populate new categories
    if (complaintCategories[selectedType]) {
      complaintCategories[selectedType].forEach((category) => {
        const option = document.createElement("option");
        option.value = category.toLowerCase().replace(/\s+/g, "-"); // Convert to lowercase and kebab-case
        option.textContent = category;
        complaintCategoryElement.appendChild(option);
      });
    }
  }

  // Event listener for complaint type change
  complaintTypeElement.addEventListener("change", updateComplaintCategories);

  // Initial population of categories
  updateComplaintCategories();
});