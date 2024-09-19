document.addEventListener('DOMContentLoaded', function () {
    const inputField = document.getElementById('confirmation');
    const confirmButton = document.getElementById('confirmBtn');

    function validateInput() {
        const requiredText = "I DEFINITELY want to DELETE this ACCOUNT.";
        if (inputField.value === requiredText) {
            confirmButton.removeAttribute('style')  // Show the button
        } else {
            confirmButton.style.display = 'none';  // Hide the button
        }
    }

    inputField.addEventListener('input', validateInput);
});
