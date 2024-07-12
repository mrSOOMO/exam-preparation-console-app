
```markdown
# Exam Preparation Console Application

This is a simple console application built with Scala, designed to help students prepare for exams by providing multiple-choice questions and immediate feedback.

## Features

- Multiple-choice questions
- Immediate feedback on answers
- Scoring system
- Easy-to-use menu

## Getting Started

### Prerequisites

- JDK (Java Development Kit) 8 or higher
- Scala 2.13 or higher
- SBT (Scala Build Tool)
- IntelliJ IDEA (recommended)

### Installation and Running

1. **Clone the repository using IntelliJ IDEA:**
   - Open IntelliJ IDEA.
   - Click on `File` -> `New` -> `Project from Version Control...`.
   - In the dialog that appears, enter the URL of the GitHub repository:
     ```plaintext
     https://github.com/yourusername/exam-preparation-console-app.git
     ```
   - Click `Clone`.

2. **Open the project in IntelliJ IDEA:**
   - IntelliJ will automatically detect the SBT project and start importing it.
   - Wait for the project to be fully imported and dependencies to be resolved.

3. **Run the application:**
   - In the Project tool window, navigate to `src/main/scala`.
   - Right-click on `ConsoleApp.scala`.
   - Select `Run 'ConsoleApp'`.

### Usage

Once the application is running, you will see a menu with several options:

```plaintext
Welcome to the Entrance Exam Test
Select a test mode:
1. Standard Test (140 questions, 180 minutes)
2. Demo Test (31 questions, 40 minutes)
3. Full Test (all questions)
4. Custom Test (choose number of questions)
5. All Questions from the Subject
6. Exit
Enter your choice (1-6):
```

#### Menu Options

1. **Standard Test (140 questions, 180 minutes):**

   Select this option to start a standard test with 140 questions and a 180-minute time limit.

2. **Demo Test (31 questions, 40 minutes):**

   Select this option to start a demo test with 31 questions and a 40-minute time limit.

3. **Full Test (all questions):**

   Select this option to take a test with all available questions.

4. **Custom Test (choose number of questions):**

   Select this option to specify the number of questions for your custom test.

5. **All Questions from the Subject:**

   Select this option to go through all the questions from the selected subject.

6. **Exit:**

   Select this option to exit the application.

### Example Session

Here's what an example session might look like:

```plaintext
Welcome to the Entrance Exam Test
Select a test mode:
1. Standard Test (140 questions, 180 minutes)
2. Demo Test (31 questions, 40 minutes)
3. Full Test (all questions)
4. Custom Test (choose number of questions)
5. All Questions from the Subject
6. Exit
Enter your choice (1-6): 1
Question 1/140    Correct 0 Wrong 0    Total 0% Correct    TIME LEFT  ⏳ 179 min 59 sec
Section: МАТЕМАТИКА В ІТ
Subsection: Дискретна математика
Subsubsection: Маршрути, ланцюги, цикли та їх різновиди у графах

❓ Що таке маршрут у графі? ❓

Варіанти відповіді:
A. Послідовність циклів, що з'єднують дві вершини
B. Послідовність вершин, що з'єднують дві вершини
C. Послідовність ребер, що з'єднують дві вершини
D. Послідовність вершин та ребер, що з'єднують дві вершини

Enter your answer (A, B, C, D or 1, 2, 3, 4): 2
❌ Incorrect! ❌ The correct answer is D.
================================================================================
Question 2/140    Correct 0 Wrong 1    Total 0% Correct    TIME LEFT  ⏳ 179 min 58 sec
Section: АРХІТЕКТУРА ОБЧИСЛЮВАЛЬНИХ СИСТЕМ
Subsection: Функціональна організація обчислювальних систем
Subsubsection: Ієрархічний принцип побудови пам'яті - регістрова, кеш, оперативна пам'ять, зовнішня пам'ять. CPU

❓ Що таке регістрова пам'ять? ❓

Варіанти відповіді:
A. Найшвидший тип пам'яті, розташований безпосередньо в процесорі
B. Тип пам'яті, що використовується для зберігання операційної системи
C. Повільний тип пам'яті, розташований на жорсткому диску
D. Тип пам'яті, що використовується для зберігання великих обсягів даних

Enter your answer (A, B, C, D or 1, 2, 3, 4): 1
✅ Correct! ✅
================================================================================
Question 3/140    Correct 1 Wrong 1    Total 50% Correct    TIME LEFT  ⏳ 179 min 57 sec
Section: ІНЖЕНЕРІЯ СИСТЕМ І ПРОГРАМНОГО ЗАБЕЗПЕЧЕННЯ
Subsection: Командна робота, підходи до розробки програмного забезпечення (ПЗ)
Subsubsection: Ролі та обов'язки у команді проекту, переваги командної роботи, ризики та складність такої співпраці

❓ Яка роль проектного менеджера у команді розробки програмного забезпечення? ❓

Варіанти відповіді:
A. Координація команди, планування проекту, управління ресурсами та ризиками
B. Розробка баз даних
C. Створення графічного дизайну
D. Написання коду та тестування програмного забезпечення

Enter your answer (A, B, C, D or 1, 2, 3, 4):
```

### License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

### Contributing

If you have suggestions for improving the application, feel free to open an issue or submit a pull request.

### Contact

If you have any questions, feel free to contact me at [your-email@example.com].

---

Happy studying!

