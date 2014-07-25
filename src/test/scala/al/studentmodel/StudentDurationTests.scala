package al.studentmodel

import al.datatypes.EMail
import org.scalatest.{FunSuite, MustMatchers}

import scala.concurrent.duration._

/**
 * Created by
 * User: antonio
 * Date: 25.07.14
 * Time: 15:35
 */
class StudentDurationTests extends FunSuite with MustMatchers {
  test("duration when no targets defined") {
    estimateDurationToTargets(Student("1", EMail("test@email.com"), StudentModel("1", 2 hours, Set.empty, Set.empty))) must be (0 hours)
  }
}
