package al.expertmodel

import scala.collection.immutable.ListSet

/**
 * Created by
 * User: antonio
 * Date: 26.03.14
 * Time: 22:29
 */
case class KnowledgeUnit(title: String,
                         description: String,
                         learningMaterials: ListSet[LearningMaterial],
                         testSchedules: ListSet[TestSchedule],
                         requirements: Set[KnowledgeUnit])
