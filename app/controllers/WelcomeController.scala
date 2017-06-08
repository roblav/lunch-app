package controllers

import javax.inject.Inject

import play.api.mvc.{Action, Controller}
import services.GreetingService


class WelcomeController @Inject() (greeter: GreetingService) extends Controller{

  def welcome() = Action {
    var greeting = greeter.greeting
    Ok(views.html.welcome(greeting))
  }

}
