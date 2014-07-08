package al.studentmodel

import al.AlEntity
import al.datatypes.{EMail, PersonalData}
import al.expertmodel.KnowledgeUnit

/**
 * Created by
 * User: antonio
 * Date: 07.07.14
 * Time: 23:45
 */
abstract class Person(override val uuid: String, val personalData: PersonalData) extends AlEntity(uuid)

case class StudentModel(weekLoadInHours: Int, passedUnits: Set[KnowledgeUnit], targetUnits: Set[KnowledgeUnit])

case class Student(override val uuid: String, email: EMail, override val personalData: PersonalData, sm: StudentModel) extends Person(uuid, personalData)
