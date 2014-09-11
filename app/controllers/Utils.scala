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

  implicit class SCORMWSRequestHolder(req: WSRequestHolder) {
    // TODO: pack this stuff in a config file
    //val APP_ID = "B9EPZP4CAQ" default APP-ID
    val APP_ID = "D1NQNHTHZ4"
    val SCORM_API_ORIGIN = "sparkmind.dashboard.0.1-beta"
    val SECRET_KEY = "b4f0oHzAUHK0xnlLDbVr3iue0a9NWTsDcAfDAZ8k"

    def withSecureSCORMApiParams: WSRequestHolder = {
      val augmentedReq = req.withQueryString(
      "appid" -> APP_ID,
      "origin" -> SCORM_API_ORIGIN,
      "ts" -> generateSCORMApiTimestamp())
      augmentedReq.withQueryString("sig" -> generateSignature(augmentedReq.queryString))
    }

    private def generateSignature(parameters: Map[String, Seq[String]]): String = {
      val s = SECRET_KEY + trimQueryParamsForSig(parameters)
      val md = MessageDigest.getInstance("MD5")
      val digest: Array[Byte] = md.digest(s.getBytes)

      (BigInt(digest) &~ (BigInt(-1) << (8 * digest.size))).toString(16)
    }

    private def trimQueryParamsForSig(parameters: Map[String, Seq[String]]): String = {
      val ka = parameters.keys.toArray
      Sorting.quickSort(ka)(Ordering.by(_.toLowerCase))
      ka.map(k => k + parameters(k).sorted.mkString).mkString
    }

    private def generateSCORMApiTimestamp(): String = DateTimeFormat.forPattern("yyyyMMddHHmmss").print(DateTime.now(DateTimeZone.UTC))
  }

  def secureWsUrl(endPointUrl: String, queryString: (String, String)*): String = {
    import play.api.Play._
    import play.api.libs.ws._

    val afterAppStart = () => Try {
      val req = WS.url(endPointUrl).withQueryString(queryString: _*)
      val sr = req.withSecureSCORMApiParams
      mkQueryString(sr)
    }

    Try(startDefaultApp()).transform(_ => afterAppStart(), _ => afterAppStart()).get
  }

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
