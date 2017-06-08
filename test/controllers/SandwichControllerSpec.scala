package controllers

import models.Sandwich
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.inject.bind
import play.api.test.FakeRequest
import play.api.test.Helpers._
import services.SandwichService

import scala.concurrent.Future

//default execution context https://www.playframework.com/documentation/2.5.x/ScalaAsync
import play.api.libs.concurrent.Execution.Implicits.defaultContext


class SandwichControllerSpec extends PlaySpec with GuiceOneAppPerTest{

  val application = new GuiceApplicationBuilder().
    overrides(bind[SandwichService].to[IntegrationSandwichService]).
    build

  // Need to specify Host header to get through AllowedHostsFilter
  val request = FakeRequest(GET, "/menu").withHeaders("Host" -> "localhost")
  val home = route(application, request).get

  "SandwichController GET" should {
    "return a successful response" in {
      val controller = new SandwichController(FakeNoSandwichService)
      val result = controller.menu().apply(FakeRequest(GET, "/foo"))
      status(result) mustBe OK
    }
  }

  "tell us when there are no sandwiches" in {
    val controller = new SandwichController(FakeNoSandwichService)
    val result = controller.menu().apply(FakeRequest(GET, "/foo"))
    contentAsString(result) must include ("<h1>Sandwich Menu</h1>")
    contentAsString(result) must include ("<p>Too Late! There are no sandwiches left</p>")
  }

  "tell us when there is 1 sandwich" in {

    val controller = new SandwichController(FakeSingleSandwichService)
    val result = controller.menu().apply(FakeRequest(GET, "/foo"))
    contentAsString(result) must include ("<h1>Sandwich Menu</h1>")
    contentAsString(result) mustNot include ("<p>Too Late! There are no sandwiches left</p>")
    contentAsString(result) must include ("Ham and Cheese")
    contentAsString(result) must include ("£2.99")
    contentAsString(result) must include ("Tuna")
    contentAsString(result) must include ("£2.11")
  }


}

class IntegrationSandwichService extends SandwichService {
  override def sandwiches(): Future[List[Sandwich]] = Future(List())
}

object FakeNoSandwichService extends SandwichService {
  override def sandwiches(): Future[List[Sandwich]] = Future(List())
}

object FakeSingleSandwichService extends SandwichService {
  val ham = Sandwich("Ham and Cheese", 2.99, "Ummm...cheesy")
  val tuna = Sandwich("Tuna", 2.11, "Ummm...fishy")
  override def sandwiches(): Future[List[Sandwich]] = Future(List(ham,tuna))
}
