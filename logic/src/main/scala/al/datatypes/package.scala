package al

import java.util.Date

/**
 * Created by
 * User: antonio
 * Date: 08.07.14
 * Time: 00:25
 */
package object datatypes {

  sealed abstract class Gender

  case object Male extends Gender

  case object Female extends Gender

  case class EMail(email: String) extends AnyVal

  case class PersonalData(firstName: String, lastName: String, birthday: Date, gender: Gender)

}
