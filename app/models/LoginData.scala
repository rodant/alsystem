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

class LoginDataTable(tag: Tag) extends Table[LoginData](tag, "LOGINDATA") {

  def userId = column[String]("user_id", O.PrimaryKey)

  def password = column[String]("password", O.NotNull)

  def * : ProvenShape[LoginData] = (userId, password) <>  (LoginData.tupled, LoginData.unapply)
}