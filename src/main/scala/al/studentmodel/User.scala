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
abstract class User(override val uuid: String, val personalData: PersonalData, roles: Set[Role[_]]) extends AlEntity(uuid)

abstract class Model(override val uuid: String) extends AlEntity(uuid)

case class StudentModel(override val uuid: String, weekLoadInHours: Int, passedUnits: Set[KnowledgeUnit], targetUnits: Set[KnowledgeUnit]) extends Model(uuid)

abstract class Role[M <: Model](override val uuid: String, name: String, email: EMail, model: M) extends AlEntity(uuid)

case class Student(override val uuid: String, name: String, email: EMail, model: StudentModel) extends Role[StudentModel](uuid, name, email, model)