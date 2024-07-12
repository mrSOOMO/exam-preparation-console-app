import service.TestManager
import scala.io.StdIn.readLine
import scala.util.Try
import scala.concurrent.duration._

object ConsoleApp extends App {
  val questionsDirectory = "src/main/resources/information_technology/questions"
  val structureFile = "src/main/resources/information_technology/structure/structure.json"
  TestManager.loadQuestionsAndAreas(questionsDirectory, structureFile)

  def mainMenu(): Unit = {
    println("Welcome to the Entrance Exam Test")
    println("Select a test mode:")
    println("1. Standard Test (140 questions, 180 minutes)")
    println("2. Demo Test (31 questions, 40 minutes)")
    println("3. Full Test (all questions)")
    println("4. Custom Test (choose number of questions)")
    println("5. All Questions from the Subject")
    println("6. Exit")
    val choice = Try(readLine("Enter your choice (1-6): ").toInt).getOrElse(0)

    choice match {
      case 1 => TestManager.startTestWithTimer(140, 180.minutes)
      case 2 => TestManager.startTestWithTimer(31, 40.minutes)
      case 3 => TestManager.startTest(TestManager.questions.length)
      case 4 =>
        val numQuestions = Try(readLine("Enter number of questions: ").toInt).getOrElse(0)
        TestManager.startTest(numQuestions)
      case 5 => TestManager.startTest(TestManager.questions.length)
      case 6 => System.exit(0)
      case _ =>
        println("Invalid choice, please try again.")
        mainMenu()
    }
  }

  mainMenu()
}
