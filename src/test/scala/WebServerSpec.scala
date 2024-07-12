import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import akka.http.scaladsl.server.Directives.*
import akka.http.scaladsl.server.Route

class WebServerSpec extends AnyWordSpec with Matchers with ScalatestRouteTest {

  val routes: Route = pathEndOrSingleSlash {
    getFromResource("public/index.html")
  } ~
    pathPrefix("public") {
      getFromResourceDirectory("public")
    } ~
    path("question") {
      get {
        complete(StatusCodes.OK)
      }
    } ~
    path("questions") {
      get {
        complete(StatusCodes.OK)
      }
    } ~
    path("structure") {
      get {
        complete(StatusCodes.OK)
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
        complete(StatusCodes.OK)
      }
    }

  "WebServer" should {
    "return the index.html file on the root path" in {
      Get("/") ~> routes ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "return the all_questions.html file on the /all-questions path" in {
      Get("/all-questions") ~> routes ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "return the full_test.html file on the /full-test path" in {
      Get("/full-test") ~> routes ~> check {
        status shouldEqual StatusCodes.OK
      }
    }

    "return OK for /question path" in {
      Get("/question") ~> routes ~> check {
        status shouldEqual StatusCodes.OK
      }
    }
  }
}
