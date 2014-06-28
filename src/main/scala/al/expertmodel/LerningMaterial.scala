package al.expertmodel

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:59
 */
case class LearningMaterial(duration: Duration, content: Content, exercises: Exercise*) {
  def exercisesDuration: Duration = exercises.foldLeft(0.minutes: Duration)((acc, e) => acc + e.duration)
  def totalDuration: Duration = duration + exercisesDuration
}

abstract class Content
case object EmptyContent extends Content
