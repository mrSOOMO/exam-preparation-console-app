package model

import io.circe.generic.semiauto.*
import io.circe.parser.*
import io.circe.{Decoder, Encoder}

import scala.io.Source
import scala.util.{Random, Using}

case class SubSubSection(num: String, title: String)
case class SubSection(num: String, title: String, subsubsections: Option[List[SubSubSection]])
case class Section(num: String, title: String, subsections: List[SubSection])

case class KnowledgeArea(section: Section)

object KnowledgeArea {
  implicit val subSubSectionDecoder: Decoder[SubSubSection] = deriveDecoder[SubSubSection]
  implicit val subSubSectionEncoder: Encoder[SubSubSection] = deriveEncoder[SubSubSection]
  implicit val subSectionDecoder: Decoder[SubSection]       = deriveDecoder[SubSection]
  implicit val subSectionEncoder: Encoder[SubSection]       = deriveEncoder[SubSection]
  implicit val sectionDecoder: Decoder[Section]             = deriveDecoder[Section]
  implicit val sectionEncoder: Encoder[Section]             = deriveEncoder[Section]
  implicit val knowledgeAreaDecoder: Decoder[KnowledgeArea] = deriveDecoder[KnowledgeArea]
  implicit val knowledgeAreaEncoder: Encoder[KnowledgeArea] = deriveEncoder[KnowledgeArea]
}

case class Question(
    section: Int,
    subsection: Option[Int],
    subsubsection: Option[Int],
    question: String,
    options: List[String],
    correctOption: String
){
  def shuffleOptions: Question = {
    val shuffledOptions = Random.shuffle(options)
    val newCorrectOption = shuffledOptions.find(_ == correctOption).getOrElse(correctOption)
    this.copy(options = shuffledOptions, correctOption = newCorrectOption)
  }
}

object Question {
  implicit val decoder: Decoder[Question] = deriveDecoder[Question]
  implicit val encoder: Encoder[Question] = deriveEncoder[Question]
}

case class QuestionsList(questions: List[Question])

object QuestionsList {
  implicit val decoder: Decoder[QuestionsList] = deriveDecoder[QuestionsList]
  implicit val encoder: Encoder[QuestionsList] = deriveEncoder[QuestionsList]
}

object FileReader {
  def readFile(filePath: String): String = {
    println(s"Reading file: $filePath")
    val content = Using(Source.fromFile(filePath))(_.mkString)
    content match {
      case scala.util.Success(value) =>
        println(s"Successfully read file: $filePath")
        value
      case scala.util.Failure(exception) =>
        println(s"Failed to read file: $filePath. Error: ${exception.getMessage}")
        ""
    }
  }

  def readQuestions(directoryPath: String): List[Question] = {
    println(s"Reading questions from directory: $directoryPath")
    val files = new java.io.File(directoryPath).listFiles.filter(_.isFile).toList
    println(s"Found ${files.length} files in directory: $directoryPath")
    val allQuestions = files.flatMap { file =>
      println(s"Reading questions from file: ${file.getPath}")
      val fileContent = readFile(file.getPath)
      println(s"File content: $fileContent")
      decode[QuestionsList](fileContent) match {
        case Right(questionsList) =>
          println(s"Successfully read ${questionsList.questions.length} questions from file: ${file.getPath}")
          if (questionsList.questions.length != 10) {
            println(
              s"Warning: File ${file.getPath} contains ${questionsList.questions.length} questions instead of 10."
            )
          }
          questionsList.questions
        case Left(error) =>
          println(s"Failed to decode questions from file: ${file.getPath}. Error: ${error.getMessage}")
          List.empty
      }
    }
    println(s"Total questions loaded: ${allQuestions.length}")
    allQuestions
  }

  def readKnowledgeAreas(filePath: String): List[KnowledgeArea] = {
    println(s"Reading knowledge areas from file: $filePath")
    decode[List[KnowledgeArea]](readFile(filePath)) match {
      case Right(areas) =>
        println(s"Successfully read ${areas.length} knowledge areas from file: $filePath")
        areas
      case Left(error) =>
        println(s"Failed to decode knowledge areas from file: $filePath. Error: ${error.getMessage}")
        List.empty
    }
  }
}

object KnowledgeAreaMatcher {
  def getKnowledgeAreaTitle(sectionNum: Int, subsectionNum: Option[Int], subsubsectionNum: Option[Int], knowledgeAreas: List[KnowledgeArea]): (String, String, String) = {
    val sectionOpt = knowledgeAreas.find(_.section.num == sectionNum.toString)

    val sectionTitle = sectionOpt.map(_.section.title).getOrElse("N/A")

    val subsectionTitle = sectionOpt.flatMap { section =>
      subsectionNum.flatMap { subNum =>
        section.section.subsections.find(_.num == s"${sectionNum}.${subNum}").map(_.title)
      }
    }.getOrElse("N/A")

    val subsubsectionTitle = sectionOpt.flatMap { section =>
      subsectionNum.flatMap { subNum =>
        section.section.subsections.find(_.num == s"${sectionNum}.${subNum}").flatMap { subsection =>
          subsubsectionNum.flatMap { subsubNum =>
            subsection.subsubsections.flatMap(_.find(_.num == s"${sectionNum}.${subNum}.${subsubNum}")).map(_.title)
          }
        }
      }
    }.getOrElse("N/A")

    (sectionTitle, subsectionTitle, subsubsectionTitle)
  }
}

