package al.expertmodel

import scala.concurrent.duration.Duration

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:34
 */
abstract class Exercise(val duration: Duration, question: String, difficultyDegree: Int, askedKnowledge: KnowledgeUnit*)

case class AutomaticExercise(override val duration: Duration, q: String, diffDegree: Int, knowledge: KnowledgeUnit*) extends Exercise(duration, q, diffDegree) {
  def withKnowledgeUnits(kus: KnowledgeUnit*) = AutomaticExercise(duration, q, diffDegree, knowledge ++ kus: _*)
}

case class ManualExercise(override val duration: Duration, q: String, diffDegree: Int, knowledge: KnowledgeUnit*) extends Exercise(duration, q, diffDegree)
