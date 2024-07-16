document.addEventListener('DOMContentLoaded', function () {
    const buttons = document.querySelectorAll('.check-btn');

    buttons.forEach(button => {
        button.addEventListener('click', function () {
            const index = this.getAttribute('data-index');
            const input = document.querySelector(`#answer-${index}`);
            input.value = input.getAttribute('data-answer');
        });
    });
});