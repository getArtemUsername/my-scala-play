package services

import play.api.libs.ws.{WS, WSClient}
import play.api.Play.current

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  *
  * WeatherService class
  * <p/>
  * Description...
  */
class WeatherService(wSClient: WSClient) {


  def getTemperature(lat: Double, lon: Double): Future[Double] = {
    val weatherUrl = s"http://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&units=metric&appid=09dec3bac6cd14d985d2fbae0de6396c"
    val weatherResponseF = wSClient.url(weatherUrl).get()
    weatherResponseF.map { weatherResponse =>
      val weatherJson = weatherResponse.json
      val temperature = (weatherJson \ "main" \ "temp").as[Double]
      temperature
    }
  }
}
