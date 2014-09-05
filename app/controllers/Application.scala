package controllers

import models.LoginData
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc._

object Application extends Controller {

  val loginForm: Form[LoginData] = Form(
    mapping(
      "userId" -> nonEmptyText,
      "password" -> nonEmptyText(minLength = 8)
    )(LoginData.apply)(LoginData.unapply)
  )

  def home = Action {
    Ok(views.html.home("Your new application is ready."))
  }

  def login = Action {
    Ok(views.html.login(loginForm))
  }

  def doLogin = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors)),
      loginData => Ok(views.html.home(loginData.userId))
    )
  }

}