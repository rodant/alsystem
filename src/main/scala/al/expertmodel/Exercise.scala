package al.expertmodel

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:34
 */

abstract class Exercise(val duration: Duration,
                        val question: String,
                        val difficultyDegree: Int)

case class AutomaticExercise(override val duration: Duration,
                             override val question: String,
                             override val difficultyDegree: Int) extends Exercise(duration, question, difficultyDegree)

case class ManualExercise(override val duration: Duration,
                          override val question: String,
                          override val difficultyDegree: Int) extends Exercise(duration, question, difficultyDegree)
