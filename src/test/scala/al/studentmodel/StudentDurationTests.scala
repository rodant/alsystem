package al.studentmodel

import al.datatypes.EMail
import al.expertmodel.{Exercise, EmptyContent, LearningMaterial, KnowledgeUnit}
import org.scalatest.{FunSuite, MustMatchers}

import scala.collection.immutable.ListSet
import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 25.07.14
 * Time: 15:35
 */
class StudentDurationTests extends FunSuite with MustMatchers {
  test("duration when no targets defined") {
    estimateDurationToTargetsInWeeks(Student("1", EMail("test@email.com"), StudentModel("1", 60, Set.empty, Set.empty))) must be (0)
  }

  test("duration when one target unit with no recursive requirements") {
    val studentModel: StudentModel = StudentModel(
      uuid = "1",
      weekWorkLoadInMinutes = 120,
      passedUnits = Set.empty,
      targetUnits = Set(
        KnowledgeUnit(
          title = "Unit 1",
          description =  "",
          learningMaterials = ListSet(
            LearningMaterial(
              duration = 8 hours,
              content =  EmptyContent,
              exercises = Array[Exercise](): _*)
          ),
          Set.empty
        )
      )
    )
    estimateDurationToTargetsInWeeks(Student("1", EMail("test@email.com"), studentModel)) must be (4)
  }

  test("duration when one target unit with no recursive requirements and a fractional number of weeks as result") {
    val studentModel: StudentModel = StudentModel(
      uuid = "1",
      weekWorkLoadInMinutes = 120,
      passedUnits = Set.empty,
      targetUnits = Set(
        KnowledgeUnit(
          title = "Unit 1",
          description =  "",
          learningMaterials = ListSet(
            LearningMaterial(
              duration = 8.hours + 20.minutes,
              content =  EmptyContent,
              exercises = Array[Exercise](): _*)
          ),
          Set.empty
        )
      )
    )
    estimateDurationToTargetsInWeeks(Student("1", EMail("test@email.com"), studentModel)) must be (5)
  }
}
