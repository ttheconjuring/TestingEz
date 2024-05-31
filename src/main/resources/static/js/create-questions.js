document.addEventListener("DOMContentLoaded", function () {
  const questionHeader = document.getElementById("questionHeader");
  const questionContent = document.getElementById("questionContent");
  let questionCounter = 0;

  function createQuestion() {
    questionCounter++;
    const questionNumber = questionCounter;
    const questionElement = document.createElement("div");
    questionElement.classList.add(
      "mx-2",
      "px-3",
      "py-1",
      "bg-light",
      "text-black",
      "rounded",
      "cursor-pointer",
      "shadow"
    );
    questionElement.innerText = questionNumber;
    questionElement.addEventListener("click", function () {
      displayQuestion(questionNumber);
    });
    questionHeader.insertBefore(questionElement, questionHeader.lastChild);

    // Automatically display content of the first question
    if (questionCounter === 1) {
      displayQuestion(questionNumber);
    }
  }

  function displayQuestion(questionNumber) {
    questionContent.innerText = "Question " + questionNumber; // Replace this with your question content
  }

  // Initial question creation
  createQuestion();

  // Button to create new question
  const addButton = document.createElement("button");
  addButton.classList.add("btn", "btn-light", "shadow", "mx-2");
  addButton.innerText = "+";
  addButton.addEventListener("click", createQuestion);
  questionHeader.appendChild(addButton);
});
