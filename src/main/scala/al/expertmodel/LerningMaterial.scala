package al.expertmodel

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:59
 */
case class LearningMaterial(duration: Duration, content: Content, exercises: Exercise*) {
  lazy val totalDuration: Duration = duration + exercisesDuration(this)
}

abstract class Content

case object EmptyContent extends Content
