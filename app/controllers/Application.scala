package controllers

import java.util.concurrent.TimeUnit

import actors.StatsActor
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import model.CombinedData
import play.api.libs.json.Json
import play.api.mvc._
import services.{SunService, WeatherService}

import scala.concurrent.ExecutionContext.Implicits.global

class Application(actorSystem: ActorSystem, sunService: SunService, weatherService: WeatherService)
  extends Controller {

  def index = Action {
    Ok(views.html.index())
  }
  
  def data = Action.async { request =>
    implicit val timeout = Timeout(5, TimeUnit.SECONDS)
    val requestF = (actorSystem.actorSelection(StatsActor.path) ? StatsActor.GetStats).mapTo[Int]
    val lat = 56.8575
    val long = 60.6125
    val sunInfoF = sunService.getSunInfo(lat, long)
    val weatherF = weatherService.getTemperature(lat, long)
    for {
      requests <- requestF
      sunInfo <- sunInfoF
      temperature <- weatherF
    } yield {
      Ok(Json.toJson(CombinedData(sunInfo, temperature, requests)))
    }
  }
}
