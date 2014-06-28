package al.expertmodel

import org.scalatest.{MustMatchers, FunSuite}
import scala.concurrent.duration._
import scala.collection.immutable.ListSet

/**
 * Created by
 * User: antonio
 * Date: 27.03.14
 * Time: 23:00
 */
class TestScheduleStepTest extends FunSuite with MustMatchers {
  test("compute duration with 0 durations") {
    val exercises = ListSet[Exercise](AutomaticExercise(duration = 0 minutes, "Question", diffDegree = 3))
    val testStep = TestScheduleStep(restTime = 0 minutes, exercises)
    testStep.duration must be (0 minutes)
  }

  test("compute duration with exercise duration") {
    val exercises = ListSet[Exercise](AutomaticExercise(duration = 10 minutes, "Question", diffDegree = 3))
    val testStep = TestScheduleStep(restTime = 0 minutes, exercises)
    testStep.duration must be (10 minutes)
  }
}
