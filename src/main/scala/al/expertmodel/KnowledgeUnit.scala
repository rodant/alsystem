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

  lazy val duration: Duration = KnowledgeUnit.duration(learningMaterials)

  lazy val durationWithOwnRequirements: Duration = durationWithOwnRequirements(_ => true)

  lazy val durationWithAllRequirements: Duration = durationWithRequirementPredicate(_ => true)

  def durationWithOwnRequirements(p: (KnowledgeUnit) => Boolean): Duration = {
    duration + KnowledgeUnit.duration(requirements, p)
  }

  def durationWithRequirementPredicate(predicate: (KnowledgeUnit) => Boolean): Duration = {
    durationWithOwnRequirements(predicate) +
      requirements.flatMap(_.requirements).map(_.durationWithRequirementPredicate(predicate)).fold(0 days)(_ + _)
  }

  def durationWithRequirements(requiredUnits: Set[KnowledgeUnit]): Duration = durationWithRequirementPredicate(requiredUnits.contains)
}

object KnowledgeUnit {
  def duration(lms: Set[LearningMaterial]): Duration = lms.map(_.totalDuration).fold(0 days)(_ + _)
  
  def duration(reqs: Set[KnowledgeUnit], p: (KnowledgeUnit) => Boolean): Duration = reqs.filter(p).map(_.duration).fold(0 days)(_ + _)
}