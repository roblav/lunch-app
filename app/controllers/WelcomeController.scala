package controllers

import play.api.mvc.{Action, Controller}


/**
  * Created by rob on 07/06/17.
  */
class WelcomeController extends Controller{

  def welcome() = Action {
    Ok(views.html.welcome())
  }

}
