let score = 0;
let timeLeft = 10;
let gameActive = false;
const button = document.getElementById('game-button');
const scoreDisplay = document.getElementById('score');
const messageDisplay = document.getElementById('message');

button.addEventListener('click', () => {
    if (!gameActive) {
        gameActive = true;
        messageDisplay.textContent = '';
        score = 0;
        scoreDisplay.textContent = score;
        startGame();
    } else {
        score++;
        scoreDisplay.textContent = score;
    }
});

function startGame() {
    const timer = setInterval(() => {
        timeLeft--;
        if (timeLeft <= 0) {
            clearInterval(timer);
            endGame();
        }
    }, 1000);
}

function endGame() {
    gameActive = false;
    timeLeft = 10;
    messageDisplay.textContent = `Game Over! Your final score is ${score}.`;
}