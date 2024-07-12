let currentQuestionIndex = 0;
let questions = [];

async function fetchQuestions() {
    const response = await fetch('/questions');
    questions = await response.json();
    renderQuestionsNav();
    renderQuestion(currentQuestionIndex);
}

function renderQuestionsNav() {
    const nav = document.getElementById('questions-nav');
    nav.innerHTML = '';
    questions.forEach((question, index) => {
        const button = document.createElement('button');
        button.innerText = index + 1;
        button.onclick = () => renderQuestion(index);
        if (index === currentQuestionIndex) {
            button.classList.add('active');
        }
        nav.appendChild(button);
    });
}

function renderQuestion(index) {
    currentQuestionIndex = index;
    const question = questions[index];

    document.getElementById('current-question-number').innerText = `Завдання ${index + 1} з ${questions.length}`;
    document.getElementById('question-title').innerText = question.title || '';
    document.getElementById('question-subtitle').innerText = question.subtitle || '';
    document.getElementById('question-subsubtitle').innerText = question.subsubtitle || '';
    document.getElementById('question-text').innerText = question.question;

    const optionsList = document.getElementById('options');
    optionsList.innerHTML = '';
    question.options.forEach((option, i) => {
        const li = document.createElement('li');
        li.innerHTML = `<label><input type="radio" name="option" value="${option}"> ${option}</label>`;
        optionsList.appendChild(li);
    });

    renderQuestionsNav();
}

function checkAnswer() {
    const selectedOption = document.querySelector('input[name="option"]:checked');
    if (selectedOption) {
        const question = questions[currentQuestionIndex];
        if (selectedOption.value === question.correctOption) {
            alert('Правильна відповідь!');
        } else {
            alert('Неправильна відповідь.');
        }
    } else {
        alert('Виберіть відповідь.');
    }
}

function nextQuestion() {
    if (currentQuestionIndex < questions.length - 1) {
        renderQuestion(currentQuestionIndex + 1);
    } else {
        alert('Це останнє питання.');
    }
}

window.onload = fetchQuestions;
