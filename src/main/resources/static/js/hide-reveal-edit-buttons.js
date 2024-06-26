const editButton = document.getElementById('edit-button');
const editButtonsDiv = document.getElementById('edit-buttons-div');

editButton.addEventListener('click', function () {
    editButtonsDiv.style.display = 'flex';
    this.style.display = 'none';
})
