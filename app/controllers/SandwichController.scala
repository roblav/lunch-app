package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import services.SandwichService

//default execution context https://www.playframework.com/documentation/2.5.x/ScalaAsync
import play.api.libs.concurrent.Execution.Implicits.defaultContext

class SandwichController @Inject() (sandwichService: SandwichService) extends Controller{

  def menu() = Action.async {
    val sandwiches = sandwichService.sandwiches
    sandwiches.map { listOfSarnies =>
      Ok(views.html.menu(listOfSarnies))
    }
  }

}
