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

  def duration: Duration = learningMaterials.map(_.totalDuration).fold(0 days)(_ + _)

  def durationWithOwnRequirements(predicate: (KnowledgeUnit) => Boolean): Duration = {
    duration + requirements.filter(predicate).map(_.duration).fold(0 days)(_ + _)
  }

  def durationWithOwnRequirements: Duration = durationWithOwnRequirements(_ => true)

  def durationWithRequirementPredicate(predicate: (KnowledgeUnit) => Boolean): Duration = {
    durationWithOwnRequirements(predicate) +
      requirements.flatMap(_.requirements).map(_.durationWithRequirementPredicate(predicate)).fold(0 days)(_ + _)
  }

  def durationWithRequirements(requiredUnits: Set[KnowledgeUnit]): Duration = durationWithRequirementPredicate(requiredUnits.contains)

  def durationWithAllRequirements: Duration = durationWithRequirementPredicate(_ => true)
}
