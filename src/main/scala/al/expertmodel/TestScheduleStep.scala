package al.expertmodel

import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.collection.immutable.ListSet

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:54
 */
case class TestScheduleStep(restTime: Duration, exercises: ListSet[Exercise]) {
  require(restTime != null && exercises.nonEmpty)

  def duration: Duration = exercises.foldLeft(restTime)((acc, e2) => acc + e2.duration)
}
