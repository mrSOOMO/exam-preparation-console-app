document.addEventListener("DOMContentLoaded", function() {
    fetch('/full-test-questions')
        .then(response => response.json())
        .then(questions => {
            const container = document.getElementById('questions-container');
            questions.forEach((question, index) => {
                const questionDiv = document.createElement('div');
                questionDiv.classList.add('question');
                questionDiv.innerHTML = `
                    <h2>Section: ${question.sectionTitle}</h2>
                    <h3>Subsection: ${question.subsectionTitle || 'N/A'}</h3>
                    <h4>Subsubsection: ${question.subsubsectionTitle || 'N/A'}</h4>
                    <p>${question.question}</p>
                    <p>Варіанти відповіді:</p>
                    <ul>
                        ${question.options.map((option, i) => `<li>${String.fromCharCode(65 + i)}: ${option}</li>`).join('')}
                    </ul>
                `;
                container.appendChild(questionDiv);
            });
        })
        .catch(error => {
            console.error('Error fetching questions:', error);
        });
});
