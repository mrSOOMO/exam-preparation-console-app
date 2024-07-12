document.getElementById('standard-test-btn').addEventListener('click', () => {
    console.log('Standard Test button clicked');
});

document.getElementById('demo-test-btn').addEventListener('click', () => {
    console.log('Demo Test button clicked');
});

document.getElementById('full-test-btn').addEventListener('click', () => {
    console.log('Full Test button clicked');
    window.location.href = '/full_test.html';
});

document.getElementById('custom-test-btn').addEventListener('click', () => {
    const questionCount = document.getElementById('custom-question-count').value;
    console.log(`Custom Test button clicked with question count: ${questionCount}`);
});

document.getElementById('all-questions-btn').addEventListener('click', () => {
    console.log('All Questions button clicked');
    window.location.href = '/all_questions.html';
});
