package services

import model.SunInfo
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat
import play.api.libs.ws.{WS, WSClient}
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  *
  * SunService class
  * <p/>
  * Description...
  *
  */
class SunService(wSClient: WSClient) {

  def getSunInfo(lat: Double, lon: Double): Future[SunInfo] = {
    val sunriseSunsetUrl = s"http://api.sunrise-sunset.org/json?lat=$lat&lng=$lon&formatted=0"

    val responseF = wSClient.url(sunriseSunsetUrl).get()
    responseF.map { response =>
      val json = response.json
      val sunriseTimeStr = (json \ "results" \ "sunrise").as[String]
      val sunsetTimeStr = (json \ "results" \ "sunset").as[String]
      val sunriseTime = DateTime.parse(sunriseTimeStr)
      val sunsetTime = DateTime.parse(sunsetTimeStr)
      val formatter = DateTimeFormat.forPattern("HH:mm:ss")
        .withZone(DateTimeZone.forID("Asia/Yekaterinburg"))
      SunInfo(formatter.print(sunriseTime), formatter.print(sunsetTime))
    }
  }

}
