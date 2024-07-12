package service

import io.circe.{Encoder, Json}
import model.{FileReader, KnowledgeAreaMatcher, Question}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.StdIn.readLine
import scala.util.Random
import java.io.{BufferedWriter, FileWriter}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import io.circe.syntax.*
import io.circe.generic.auto.*
import service.TestManager.knowledgeAreas

object TestManager {
  var questions: List[Question] = _
  var knowledgeAreas: List[model.KnowledgeArea] = _

  def loadQuestionsAndAreas(questionsPath: String, areasPath: String): Unit = {
    questions = FileReader.readQuestions(questionsPath)
    knowledgeAreas = FileReader.readKnowledgeAreas(areasPath)
  }

  def startTest(numQuestions: Int): Unit = {
    val selectedQuestions = Random.shuffle(questions).take(numQuestions)
    var correctAnswers = 0
    var wrongAnswers = 0

    val incorrectQuestions = scala.collection.mutable.ListBuffer[IncorrectQuestion]()

    selectedQuestions.zipWithIndex.foreach { case (question, index) =>
      val questionNumber = index + 1
      val totalQuestions = numQuestions
      val correctPercentage = if (questionNumber > 1) (correctAnswers * 100) / (correctAnswers + wrongAnswers) else 0

      println(f"Question $questionNumber/$totalQuestions    Correct $correctAnswers Wrong $wrongAnswers    Total $correctPercentage%% Correct")
      val shuffledQuestion = question.shuffleOptions
      displayQuestion(shuffledQuestion)
      val userAnswer = readAnswer()
      val correctAnswer = getOptionLetter(shuffledQuestion.correctOption, shuffledQuestion.options)

      if (userAnswer == correctAnswer) {
        println("✅ Correct! ✅")
        correctAnswers += 1
      } else {
        println(s"❌ Incorrect! ❌ The correct answer is $correctAnswer.")
        wrongAnswers += 1
        val incorrectQuestion = IncorrectQuestion(
          section = KnowledgeAreaMatcher.getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection, knowledgeAreas)._1,
          subsection = KnowledgeAreaMatcher.getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection, knowledgeAreas)._2,
          subsubsection = KnowledgeAreaMatcher.getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection, knowledgeAreas)._3,
          question = shuffledQuestion.question,
          options = shuffledQuestion.options,
          correctAnswer = correctAnswer
        )
        incorrectQuestions += incorrectQuestion
      }
      println("=" * 80)
    }

    saveIncorrectQuestions(incorrectQuestions)

    val finalPercentage = if (correctAnswers + wrongAnswers > 0) (correctAnswers * 100) / (correctAnswers + wrongAnswers) else 0
    println(f"Test complete! Correct: $correctAnswers, Wrong: $wrongAnswers, Total: $finalPercentage%% Correct")
  }

  def startTestWithTimer(numQuestions: Int, duration: Duration): Unit = {
    val selectedQuestions = Random.shuffle(questions).take(numQuestions)
    var correctAnswers = 0
    var wrongAnswers = 0
    val endTime = System.currentTimeMillis() + duration.toMillis

    val incorrectQuestions = scala.collection.mutable.ListBuffer[IncorrectQuestion]()

    val testFuture = Future {
      selectedQuestions.zipWithIndex.foreach { case (question, index) =>
        val questionNumber = index + 1
        val totalQuestions = numQuestions
        val correctPercentage = if (questionNumber > 1) (correctAnswers * 100) / (correctAnswers + wrongAnswers) else 0
        val currentTime = System.currentTimeMillis()
        val timeLeftMillis = endTime - currentTime
        val minutesLeft = (timeLeftMillis / 60000).toInt
        val secondsLeft = ((timeLeftMillis % 60000) / 1000).toInt

        println(f"Question $questionNumber/$totalQuestions    Correct $correctAnswers Wrong $wrongAnswers    Total $correctPercentage%% Correct    TIME LEFT  ⏳ $minutesLeft min $secondsLeft sec")
        val shuffledQuestion = question.shuffleOptions
        displayQuestion(shuffledQuestion)
        val userAnswer = readAnswer()
        val correctAnswer = getOptionLetter(shuffledQuestion.correctOption, shuffledQuestion.options)

        if (userAnswer == correctAnswer) {
          println("✅ Correct! ✅")
          correctAnswers += 1
        } else {
          println(s"❌ Incorrect! ❌ The correct answer is $correctAnswer.")
          wrongAnswers += 1
          val incorrectQuestion = IncorrectQuestion(
            section = KnowledgeAreaMatcher.getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection, knowledgeAreas)._1,
            subsection = KnowledgeAreaMatcher.getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection, knowledgeAreas)._2,
            subsubsection = KnowledgeAreaMatcher.getKnowledgeAreaTitle(question.section, question.subsection, question.subsubsection, knowledgeAreas)._3,
            question = shuffledQuestion.question,
            options = shuffledQuestion.options,
            correctAnswer = correctAnswer
          )
          incorrectQuestions += incorrectQuestion
        }
        println("=" * 80)
      }

      saveIncorrectQuestions(incorrectQuestions)

      val finalPercentage = if (correctAnswers + wrongAnswers > 0) (correctAnswers * 100) / (correctAnswers + wrongAnswers) else 0
      println(f"Test complete! Correct: $correctAnswers, Wrong: $wrongAnswers, Total: $finalPercentage%% Correct")
    }
    Await.result(testFuture, duration + Duration.apply(1, "minute"))
  }

  def saveIncorrectQuestions(incorrectQuestions: scala.collection.mutable.ListBuffer[IncorrectQuestion]): Unit = {
    val currentDateTime = LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"))
    val incorrectQuestionsFile = s"incorrect_questions_$currentDateTime.json"
    val writer = new BufferedWriter(new FileWriter(incorrectQuestionsFile, true))
    writer.write(incorrectQuestions.asJson.noSpaces)
    writer.newLine()
    writer.close()
  }

  def displayQuestion(question: Question): Unit = {
    val (sectionTitle, subsectionTitle, subsubsectionTitle) = KnowledgeAreaMatcher.getKnowledgeAreaTitle(
      question.section, question.subsection, question.subsubsection, knowledgeAreas
    )

    println(s"Section: $sectionTitle")
    println(s"Subsection: $subsectionTitle")
    println(s"Subsubsection: $subsubsectionTitle")
    println()
    println( s"❓ ${question.question} ❓")
    println()
    println("Варіанти відповіді:")

    question.options.zipWithIndex.foreach { case (option, index) =>
      val optionLetter = ('A' + index).toChar
      println(s"$optionLetter. $option")
    }
    println()
  }

  def readAnswer(): String = {
    val answer = readLine("Enter your answer (A, B, C, D or 1, 2, 3, 4): ").toUpperCase
    answer match {
      case "1" => "A"
      case "2" => "B"
      case "3" => "C"
      case "4" => "D"
      case other => other
    }
  }

  def getOptionLetter(option: String, options: List[String]): String = {
    val index = options.indexOf(option)
    if (index >= 0 && index < 4) ('A' + index).toChar.toString else ""
  }
}

case class IncorrectQuestion(
                              section: String,
                              subsection: String,
                              subsubsection: String,
                              question: String,
                              options: List[String],
                              correctAnswer: String
                            )

object IncorrectQuestion {
  implicit val encoder: Encoder[IncorrectQuestion] = new Encoder[IncorrectQuestion] {
    final def apply(a: IncorrectQuestion): Json = Json.obj(
      ("section", Json.fromString(a.section)),
      ("subsection", Json.fromString(a.subsection)),
      ("subsubsection", Json.fromString(a.subsubsection)),
      ("question", Json.fromString(a.question)),
      ("options", Json.arr(a.options.map(Json.fromString): _*)),
      ("correctAnswer", Json.fromString(a.correctAnswer))
    )
  }
}
