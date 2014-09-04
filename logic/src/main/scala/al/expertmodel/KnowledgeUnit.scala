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
  lazy val duration: Duration = learningMaterialsDuration(learningMaterials)

  lazy val durationWithOwnRequirements: Duration = unitDurationWithOwnRequirements(this, _ => true)

  lazy val durationWithAllRequirements: Duration = unitDurationWithRequirementPredicate(this, _ => true)
}
