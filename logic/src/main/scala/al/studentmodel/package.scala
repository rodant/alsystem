package al

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 25.07.14
 * Time: 15:26
 */
package object studentmodel {
  def estimateDurationToTargetsInWeeks(student: Student): Int = {
    val duration = student.model.targetUnits.map(_.durationWithAllRequirements).foldLeft[Duration](0 hours)(_ + _)
    val lowerResult = (duration.toMinutes / student.model.weekWorkLoadInMinutes).toInt
    if (duration.toMinutes % student.model.weekWorkLoadInMinutes == 0) lowerResult
    else lowerResult + 1
  }
}
