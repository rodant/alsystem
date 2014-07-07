package al.expertmodel

import org.scalatest.{FunSuite, MustMatchers}

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 27.03.14
 * Time: 23:00
 */
class LearningMaterialDurationTest extends FunSuite with MustMatchers {
  test("compute duration with 0 durations, no exercises") {
    val learningMaterial = LearningMaterial(0 days, EmptyContent)
    learningMaterial.duration must be(0 days)
    exercisesDuration(learningMaterial) must be(0 minutes)
    learningMaterial.totalDuration must be(0 days)
  }

  test("compute duration with exercise duration") {
    val exercises = Array[Exercise](AutomaticExercise(duration = 10 minutes, "Question", difficultyDegree = 3))
    val learningMaterial = LearningMaterial(0 days, EmptyContent, exercises: _*)
    exercisesDuration(learningMaterial) must be(10 minutes)
    learningMaterial.totalDuration must be(10 minutes)
  }

  test("compute duration with own and exercise duration") {
    val exercises = Array[Exercise](AutomaticExercise(duration = 10 minutes, "Question", difficultyDegree = 3))
    val learningMaterial = LearningMaterial(5 days, EmptyContent, exercises: _*)
    exercisesDuration(learningMaterial) must be(10 minutes)
    learningMaterial.totalDuration must be(5.days + 10.minutes)
  }

  test("compute duration with many exercises") {
    val exercises = Array[Exercise](
      AutomaticExercise(duration = 10 minutes, "Question", difficultyDegree = 3),
      AutomaticExercise(duration = 30 minutes, "Question", difficultyDegree = 3))
    val learningMaterial = LearningMaterial(3 days, EmptyContent, exercises: _*)
    exercisesDuration(learningMaterial) must be(40 minutes)
    learningMaterial.totalDuration must be(3.days + 40.minutes)
  }
}
