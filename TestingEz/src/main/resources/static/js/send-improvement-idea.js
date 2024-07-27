document.addEventListener('DOMContentLoaded', () => {
    const button = document.getElementById('is');
    const inputField = document.getElementById('idea');
    const thanksMessage = document.getElementById('mes');

    button.addEventListener('click', () => {
        const ideaText = inputField.value;

        if (ideaText.trim() === "") {
            alert("Please enter an idea!");
            return;
        }

        // Replace 'YOUR_ENDPOINT_URL' with your actual endpoint URL
        const endpoint = 'http://localhost:8081/ninja/api/improvements/post';

        fetch(endpoint, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                idea: ideaText,
                approved: false,
                disapproved: false
            })
        })
            .then(response => {
                if (response.ok) {
                    // Make the thanks message visible
                    thanksMessage.classList.remove('d-none');
                } else {
                    alert("Failed to send your idea. Please try again.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
                alert("An error occurred. Please try again.");
            });
    });
});
