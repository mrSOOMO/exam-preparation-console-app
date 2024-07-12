// public/all_questions.js
document.addEventListener('DOMContentLoaded', async () => {
    const questionsContainer = document.getElementById('questions-container');
    let knowledgeAreas = {};

    // Загрузка структуры данных
    const loadKnowledgeAreas = async () => {
        try {
            const response = await fetch('/structure');
            knowledgeAreas = await response.json();
        } catch (error) {
            console.error('Error loading knowledge areas:', error);
        }
    };

    // Получение названия раздела, подраздела и подподраздела
    const getKnowledgeAreaTitle = (sectionNum, subsectionNum, subsubsectionNum) => {
        const section = knowledgeAreas.find(sec => sec.section.num === sectionNum.toString());
        if (!section) return { sectionTitle: 'N/A', subsectionTitle: 'N/A', subsubsectionTitle: 'N/A' };

        const subsection = section.section.subsections.find(sub => sub.num === `${sectionNum}.${subsectionNum}`);
        const subsubsection = subsection ? subsection.subsubsections.find(subsub => subsub.num === `${sectionNum}.${subsectionNum}.${subsubsectionNum}`) : null;

        return {
            sectionTitle: section.section.title,
            subsectionTitle: subsection ? subsection.title : 'N/A',
            subsubsectionTitle: subsubsection ? subsubsection.title : 'N/A'
        };
    };

    try {
        await loadKnowledgeAreas();

        const response = await fetch('/questions');
        const questions = await response.json();
        questionsContainer.innerHTML = '';

        questions.forEach(question => {
            const questionElement = document.createElement('div');
            questionElement.classList.add('question');

            const titles = getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection);

            // Добавляем информацию о разделе
            const sectionInfo = document.createElement('p');
            sectionInfo.innerHTML = `<strong>Section:</strong> ${titles.sectionTitle} <br>
                                     <strong>Subsection:</strong> ${titles.subsectionTitle} <br>
                                     <strong>Subsubsection:</strong> ${titles.subsubsectionTitle}`;
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
