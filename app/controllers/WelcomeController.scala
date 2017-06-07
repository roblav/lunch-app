package controllers

import play.api.mvc._


/**
  * Created by rob on 07/06/17.
  */
class WelcomeController extends Controller{

  def welcome = Action {
    Ok("Hello World")
  }

}
