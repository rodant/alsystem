package controllers

import models.{Course, LoginData, SignUpData, SignUpDataTable}
import play.api.Logger
import play.api.data.Form
import play.api.data.Forms._
import play.api.db.slick._
import play.api.mvc._

import scala.concurrent.Future
import scala.slick.driver.H2Driver.simple._
import scala.slick.lifted.TableQuery
import scala.xml.{Node, NodeSeq}

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

  val signUpData = TableQuery[SignUpDataTable]
  val logger = Logger.logger

  def home = Action { implicit request =>
    request.session.get("user-id").map { ui =>
      Ok(views.html.home(ui))
    }.getOrElse {
      Redirect("/")
    }
  }

  def courses = Action async { implicit request =>

    import controllers.Utils.SCORMWSRequestHolder
    import play.api.libs.concurrent.Execution.Implicits.defaultContext

    def mapCoursesXML(coursesXml: NodeSeq): Seq[Course] = {
      (coursesXml \\ "course").map(node =>
        Course(
          (node \ "@id").toString(),
          (node \ "@title").toString(),
          (node \ "@versions").toString().toInt,
          (node \ "@registrations").toString().toInt,
          (node \ "tags" \ "tag").toSet[Node].map(_.text)))
    }

    request.session.get("user-id").map { username =>
      SCORMWSRequestHolder()
        .withQueryString("method" -> "rustici.course.getCourseList")
        .withSecureSCORMApiParams.get()
        .map(res => Ok(views.html.courses(username, mapCoursesXML(res.xml))))
    }.getOrElse {
      Future.successful(Redirect("/"))
    }
  }

  def login = Action {
    Ok(views.html.login(loginForm, signUpForm))
  }

  def doLogin() = DBAction { implicit rs =>
    loginForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(formWithErrors, signUpForm)),
      ld => {
        val query = signUpData.filter(e => (e.firstName === ld.userId) || (e.email === ld.userId))
        query.firstOption match {
          case Some(SignUpData(fn, _, ld.password)) => Redirect("/home").withSession("user-id" -> fn)
          case _ => Unauthorized(views.html.login(loginForm, signUpForm))
        }
      }
    )
  }

  def doSignUp() = DBAction { implicit rs =>
    signUpForm.bindFromRequest().fold(
      formWithErrors => BadRequest(views.html.login(loginForm, formWithErrors)),
      sud => {
        val query = signUpData.filter(e => (e.firstName === sud.firstName) || (e.email === sud.email))
        query.firstOption match {
          case Some(_) => Conflict(views.html.login(loginForm, signUpForm))
          case None => {
            signUpData += sud
            Redirect("/home").withSession("user-id" -> sud.firstName)
          }
        }
      }
    )
  }

  def logout = Action {
    Redirect("/").withNewSession
  }

}