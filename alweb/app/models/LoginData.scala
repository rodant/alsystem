package models

import play.api.db.slick.Config.driver.simple._

import scala.slick.lifted.ProvenShape

/**
 * Created by
 * User: antonio
 * Date: 05.09.14
 * Time: 12:16
 */
case class LoginData(userId: String, password: String)

case class SignUpData(firstName: String, email: String, password: String)

class SignUpDataTable(tag: Tag) extends Table[SignUpData](tag, "SIGNUPDATA") {

  def firstName = column[String]("first_name", O.PrimaryKey)

  def email = column[String]("email", O.NotNull)

  def password = column[String]("password", O.NotNull)

  def * : ProvenShape[SignUpData] = (firstName, email, password) <>(SignUpData.tupled, SignUpData.unapply)
}