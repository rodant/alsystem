package controllers

import java.security.MessageDigest

import org.joda.time.format.DateTimeFormat
import org.joda.time.{DateTime, DateTimeZone}
import play.api.libs.ws._

import scala.util.{Sorting, Try}

/**
 * Created by
 * User: antonio
 * Date: 10.09.14
 * Time: 12:15
 */

object Utils {

  //val APP_ID = "B9EPZP4CAQ" default APP-ID
  val APP_ID = "D1NQNHTHZ4"
  val SCORM_API_ORIGIN = "sparkmind.dashboard.0.1-beta"
  val SECRET_KEY = "b4f0oHzAUHK0xnlLDbVr3iue0a9NWTsDcAfDAZ8k"

  def secureWsUrl(endPoint: String, queryString: (String, String)*): String = {
    import play.api.Play._
    import play.api.libs.ws._

    val afterAppStart = (u: Unit) => Try {
      val req = WS.url(endPoint).withQueryString(queryString: _*)
      val sr = withSecureSCORMApiParams(req)
      mkQueryString(sr)
    }

    Try(startDefaultApp()).transform(afterAppStart, e => afterAppStart()).get
  }

  def withSecureSCORMApiParams(req: WSRequestHolder): WSRequestHolder = {
    val augmentedReq: WSRequestHolder = req.withQueryString(
      "appid" -> APP_ID,
      "origin" -> SCORM_API_ORIGIN,
      "ts" -> currentSCORMApiTStamp)
    augmentedReq.withQueryString("sig" -> signature(augmentedReq.queryString))
  }

  def signature(parameters: Map[String, Seq[String]]): String = {
    val s = SECRET_KEY + trimQueryParamsForSig(parameters)
    val md = MessageDigest.getInstance("MD5")
    val digest: Array[Byte] = md.digest(s.getBytes)

    (BigInt(digest) &~ (BigInt(-1) << (8 * digest.size))).toString(16)
  }

  def trimQueryParamsForSig(parameters: Map[String, Seq[String]]): String = {
    val ka = parameters.keys.toArray
    Sorting.quickSort(ka)(Ordering.by(_.toLowerCase))
    ka.map(k => k + parameters(k).sorted.mkString).mkString
  }

  def currentSCORMApiTStamp: String = DateTimeFormat.forPattern("yyyyMMddHHmmss").print(DateTime.now(DateTimeZone.UTC))

  def startDefaultApp(): Unit = {
    import java.io.File
    import play.api._

    val application = new DefaultApplication(new File("."), this.getClass.getClassLoader, None, Mode.Dev)
    Play.start(application)
  }

  def mkQueryString(req: WSRequestHolder): String = {
    val queryString: Map[String, Seq[String]] = req.queryString
    req.url + queryString.keys.map(k => queryString(k).foldLeft("")(_ + k + "=" + _ + "&"))
      .mkString("?", "&", "").replace("&&", "&").dropRight(1)
  }
}
