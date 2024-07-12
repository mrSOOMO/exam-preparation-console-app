const { chromium } = require('playwright');

(async () => {
    // Запуск сервера
    const serverProcess = require('child_process').spawn('sbt', ['run']);

    serverProcess.stdout.on('data', (data) => {
        console.log(`stdout: ${data}`);
    });

    serverProcess.stderr.on('data', (data) => {
        console.error(`stderr: ${data}`);
    });

    // Ожидание запуска сервера
    await new Promise(resolve => setTimeout(resolve, 5000));

    // Запуск браузера
    const browser = await chromium.launch();
    const context = await browser.newContext();
    const page = await context.newPage();

    // Переход на главную страницу и проверка
    await page.goto('http://localhost:8080');
    await page.screenshot({ path: 'homepage.png' });

    // Переход на страницу всех вопросов и проверка
    await page.click('#all-questions-btn');
    await page.waitForSelector('#questions-container');
    await page.screenshot({ path: 'all_questions.png' });

    // Переход на страницу полного теста и проверка
    await page.goto('http://localhost:8080/full_test.html');
    await page.waitForSelector('#questions-container');
    await page.screenshot({ path: 'full_test.png' });

    // Закрытие браузера и сервера
    await browser.close();
    serverProcess.kill();
})();
