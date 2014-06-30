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

  def durationWithRequirements: Duration = duration + requirements.map(_.durationWithRequirements).fold(0 days)(_ + _)

  def durationWithRequirements(requiredUnits: Set[KnowledgeUnit]): Duration =
    duration + requirements.filter(requiredUnits.contains(_)).map(_.durationWithRequirements(requiredUnits)).fold(0 days)(_ + _) +
      requirements.flatMap(_.requirements).map(_.durationWithRequirements(requiredUnits)).fold(0 days)(_ + _)
}
