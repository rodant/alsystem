package al.expertmodel

import scala.collection.immutable.ListSet
import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:29
 */
case class KnowledgeUnit(title: String,
                         description: String,
                         learningMaterials: ListSet[LearningMaterial],
                         requirements: Set[KnowledgeUnit]) {

  def duration: Duration = learningMaterials.map(_.totalDuration).fold[Duration](0 days)(_ + _)

  def durationWithRequirements: Duration = ???

  def durationWithRequirements(knownUnits: Set[KnowledgeUnit]): Duration = ???
}
