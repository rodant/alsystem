package controllers

import models.{LoginData, SignUpData}
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

  val signUpForm: Form[SignUpData] = Form(
    mapping(
      "firstName" -> nonEmptyText,
      "email" -> email,
      "password" -> nonEmptyText(minLength = 8)
    )(SignUpData.apply)(SignUpData.unapply)
  )

  def home = Action { implicit request =>
    request.session.get("user-id").map { ui =>
      Ok(views.html.home(ui))
    }.getOrElse {
      Redirect("/")
    }
  }

  def login = Action {
    Ok(views.html.login(loginForm, signUpForm))
  }

  def doLogin() = Action { implicit request =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors, signUpForm)),
      loginData => {
        Redirect("/home").withSession("user-id" -> loginData.userId)
      }
    )
  }

  def doSignUp() = Action { implicit request =>
    signUpForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(loginForm, formWithErrors)),
      signUpData => {
        Redirect("/home").withSession("user-id" -> signUpData.firstName)
      }
    )
  }

  def logout = Action {
    Redirect("/").withNewSession
  }

}