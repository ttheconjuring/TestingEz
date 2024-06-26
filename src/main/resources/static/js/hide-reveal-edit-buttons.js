const editProfileButton = document.getElementById('edit-profile-button');
const editProfileButtonsDiv = document.getElementById('edit-profile-buttons-div');
const inputFields = document.querySelectorAll('input');

editProfileButton.addEventListener('click', function () {
    editProfileButtonsDiv.style.display = 'flex';
    this.style.display = 'none';
    inputFields.forEach(function (input) {
        input.disabled = false;
        input.readOnly = false;

        const fieldValue = input.value;
        input.value = fieldValue.split(': ')[1];
    })
})
