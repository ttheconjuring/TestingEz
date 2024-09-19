document.addEventListener('DOMContentLoaded', function () {
    // Get the response time and start time from the HTML elements
    const responseTime = parseInt(document.getElementById('responseTime').value); // in minutes
    const startTimeStr = document.getElementById('startTime').value; // Assuming startTime is a string in ISO format

    // Convert response time to milliseconds
    const responseTimeMs = responseTime * 60 * 1000;

    // Parse the start time
    const startTime = new Date(startTimeStr);

    // Get the current time
    const currentTime = new Date();

    // Calculate the end time
    const endTime = new Date(startTime.getTime() + responseTimeMs);

    // Calculate the time remaining
    const timeRemaining = endTime - currentTime;

    if (timeRemaining <= 0) {
        // If time has already expired, submit the form immediately
        document.querySelector('form').submit();
    } else {
        // Set a timeout to trigger the submit button after the remaining time
        setTimeout(function () {
            document.querySelector('form').submit();
        }, timeRemaining);

        // Optionally, display the countdown to the user
        const countdownElement = document.getElementById('countdown');
        let remainingSeconds = Math.floor(timeRemaining / 1000);

        const countdownInterval = setInterval(function () {
            if (remainingSeconds <= 0) {
                clearInterval(countdownInterval);
                countdownElement.textContent = '00:00';
                return;
            }

            remainingSeconds--;

            const minutes = Math.floor(remainingSeconds / 60);
            const seconds = remainingSeconds % 60;

            countdownElement.textContent = `${minutes}:${seconds < 10 ? '0' : ''}${seconds}`;
        }, 1000);
    }
});
