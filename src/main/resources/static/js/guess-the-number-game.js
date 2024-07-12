let randomNumber = Math.floor(Math.random() * 100) + 1;
const guessButton = document.getElementById('guess-button');
const guessInput = document.getElementById('guess-input');
const feedback = document.getElementById('feedback');

guessButton.addEventListener('click', () => {
    const userGuess = Number(guessInput.value);
    if (!userGuess || userGuess < 1 || userGuess > 100) {
        feedback.textContent = 'Please enter a number between 1 and 100.';
        return;
    }

    if (userGuess === randomNumber) {
        feedback.textContent = 'Congratulations! You guessed the correct number!';
        resetGame();
    } else if (userGuess > randomNumber) {
        feedback.textContent = 'Too high! Try again.';
    } else {
        feedback.textContent = 'Too low! Try again.';
    }
});

function resetGame() {
    randomNumber = Math.floor(Math.random() * 100) + 1;
    guessInput.value = '';
}