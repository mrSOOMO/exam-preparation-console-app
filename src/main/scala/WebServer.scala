import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.*
import akka.stream.ActorMaterializer
import model.CirceSupport.*
import model.config.ConfigLoader
import model.{FileReader, Question}

import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn
import scala.util.Random

object WebServer {
  implicit val system: ActorSystem = ActorSystem("my-system")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  val questionsDirectory = ConfigLoader.App.Data.questionsDirectory
  val structureFile = ConfigLoader.App.Data.structureFile

  val questions = FileReader.readQuestions(questionsDirectory)
  val knowledgeAreas = FileReader.readKnowledgeAreas(structureFile)

  def main(args: Array[String]): Unit = {
    val route =
      pathEndOrSingleSlash {
        getFromResource("public/index.html")
      } ~
        pathPrefix("public") {
          getFromResourceDirectory("public")
        } ~
        path("question") {
          get {
            if (questions.nonEmpty) {
              val randomQuestion = questions(Random.nextInt(questions.length))
              complete(randomQuestion)
            } else {
              complete(StatusCodes.NotFound, "No questions found.")
            }
          }
        } ~
        path("questions") {
          get {
            complete(questions)
          }
        } ~
        path("structure") {
          get {
            complete(knowledgeAreas)
          }
        } ~
        path("all-questions") {
          getFromResource("public/all_questions.html")
        } ~
        path("full-test") {
          getFromResource("public/full_test.html")
        } ~
        path("submit") {
          post {
            entity(as[Map[String, String]]) { answers =>
              complete(StatusCodes.OK, "Answers received.")
            }
          }
        }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine()
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
