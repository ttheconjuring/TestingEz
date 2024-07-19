document.addEventListener('DOMContentLoaded', function () {
    // Get the response time from the HTML element
    const responseTime = parseInt(document.getElementById('responseTime').value);

    // Calculate the timeout in milliseconds
    const timeout = responseTime * 60 * 1000;

    // Set a timeout to trigger the submit button after the specified time
    setTimeout(function () {
        // Find and trigger the submit button
        document.querySelector('form').submit();
    }, timeout);

    // Optionally, you can also display the countdown to the user
    const countdownElement = document.getElementById('countdown');
    let timeRemaining = timeout / 1000;

    const countdownInterval = setInterval(function () {
        if (timeRemaining <= 0) {
            clearInterval(countdownInterval);
            return;
        }

        timeRemaining--;

        const minutes = Math.floor(timeRemaining / 60);
        const seconds = timeRemaining % 60;

        countdownElement.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
    }, 1000);
});
