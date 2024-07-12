document.addEventListener('DOMContentLoaded', () => {
    const allQuestionsBtn = document.getElementById('all-questions-btn');
    const questionsContainer = document.getElementById('questions-container');

    if (allQuestionsBtn) {
        allQuestionsBtn.addEventListener('click', async () => {
            try {
                const response = await fetch('/questions');
                const questions = await response.json();
                questionsContainer.innerHTML = '';

                questions.forEach(question => {
                    const questionElement = document.createElement('div');
                    questionElement.classList.add('question');

                    // Добавляем информацию о разделе
                    const sectionInfo = document.createElement('p');
                    sectionInfo.innerHTML = `<strong>Section:</strong> ${question.section || 'N/A'} <strong>Subsection:</strong> ${question.subsection || 'N/A'} <strong>Subsubsection:</strong> ${question.subsubsection || 'N/A'}`;
                    questionElement.appendChild(sectionInfo);

                    // Добавляем сам вопрос
                    const questionText = document.createElement('p');
                    questionText.innerHTML = `<strong>${question.question}</strong>`;
                    questionElement.appendChild(questionText);

                    // Добавляем заголовок перед вариантами ответов
                    const optionsHeader = document.createElement('p');
                    optionsHeader.innerHTML = `<strong>Варіанти відповіді:</strong>`;
                    questionElement.appendChild(optionsHeader);

                    // Добавляем варианты ответов
                    const optionsList = document.createElement('ul');
                    question.options.forEach(option => {
                        const optionItem = document.createElement('li');
                        if (option === question.correctOption) {
                            optionItem.innerHTML = `<strong>${option}</strong>`;
                        } else {
                            optionItem.innerHTML = option;
                        }
                        optionsList.appendChild(optionItem);
                    });
                    questionElement.appendChild(optionsList);

                    questionsContainer.appendChild(questionElement);
                });
            } catch (error) {
                console.error('Error fetching questions:', error);
            }
        });
    }
});
