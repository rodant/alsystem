package models

/**
 * Created by
 * User: antonio
 * Date: 11.09.14
 * Time: 23:08
 */
case class Course(id: String, title: String, versions: Int, registrations: Int, tags: Set[String])
