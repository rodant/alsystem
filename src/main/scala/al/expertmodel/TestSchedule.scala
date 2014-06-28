package al.expertmodel

import scala.concurrent.duration.Duration

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:58
 */
case class TestSchedule(required: Boolean, steps: List[TestScheduleStep]) {
  def duration: Duration = ???
}
