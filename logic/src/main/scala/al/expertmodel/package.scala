package al

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 07.07.14
 * Time: 23:14
 */
package object expertmodel {
  def exercisesDuration(m: LearningMaterial): Duration = m.exercises.foldLeft(0.minutes: Duration)((acc, e) => acc + e.duration)

  def learningMaterialsDuration(lms: Set[LearningMaterial]): Duration = lms.map(_.totalDuration).fold(0 days)(_ + _)

  def requirementsDuration(rs: Set[KnowledgeUnit], p: (KnowledgeUnit) => Boolean): Duration = rs.filter(p).map(_.duration).fold(0 days)(_ + _)

  def unitDurationWithOwnRequirements(u: KnowledgeUnit, p: (KnowledgeUnit) => Boolean): Duration = u.duration + requirementsDuration(u.requirements, p)

  def unitDurationWithRequirementPredicate(u: KnowledgeUnit, predicate: (KnowledgeUnit) => Boolean): Duration = unitDurationWithOwnRequirements(u, predicate) +
    u.requirements.flatMap(_.requirements).map(unitDurationWithRequirementPredicate(_, predicate)).fold(0 days)(_ + _)

  def unitDurationWithRequirements(u: KnowledgeUnit, requiredUnits: Set[KnowledgeUnit]): Duration = unitDurationWithRequirementPredicate(u, requiredUnits.contains)
}
