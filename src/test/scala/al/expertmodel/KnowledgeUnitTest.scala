package al.expertmodel

import org.scalatest.{FunSuite, MustMatchers}

import scala.collection.immutable.ListSet
import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 28.06.14
 * Time: 16:52
 */
class KnowledgeUnitTest extends FunSuite with MustMatchers {
  test("compute the duration, empty unit") {
    val unit = KnowledgeUnit("Foo", "Bar", ListSet.empty, Set.empty)
    unit.duration must be(0 days)
  }

  test("duration with learning materials") {
    val unit = KnowledgeUnit("Foo", "Bar",
      ListSet(LearningMaterial(1 day, EmptyContent), LearningMaterial(5 day, EmptyContent, ManualExercise(5 minutes, "question", 2))),
      Set.empty)
    unit.duration must be(6.day + 5.minutes)
  }

  test("duration with requirements") {
    testKnowledgeUnitWithRequirements.durationWithOwnRequirements must be(3 days)
  }

  test("duration with partial requirements") {
    unitDurationWithRequirements(testKnowledgeUnitWithRequirements, bottomRequirements) must be(3 days)
    testKnowledgeUnitWithRequirements.durationWithAllRequirements must be(5 days)
  }

  lazy val bottomRequirements: Set[KnowledgeUnit] =
    Set(
      KnowledgeUnit(
        title = "Foo",
        description = "Bar",
        learningMaterials =
          ListSet(
            LearningMaterial(
              duration = 2 days,
              content = EmptyContent
            )
          ),
        requirements = Set.empty
      )
    )

  lazy val testKnowledgeUnitWithRequirements =
    KnowledgeUnit(
      title = "Foo",
      description = "Bar",
      learningMaterials =
        ListSet(
          LearningMaterial(
            duration = 1 day,
            content = EmptyContent
          )
        ),
      requirements =
        Set(
          KnowledgeUnit(
            title = "Foo",
            description = "Bar",
            learningMaterials =
              ListSet(
                LearningMaterial(
                  duration = 2 days,
                  content = EmptyContent
                )
              ),
            requirements = bottomRequirements
          )
        )
    )
}
