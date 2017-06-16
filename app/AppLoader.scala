import actors.StatsActor
import actors.StatsActor.Ping
import akka.actor.Props
import controllers.{Assets, Application => ApplicationController}
import play.api._
import play.api.routing.Router
import router.Routes
import com.softwaremill.macwire._
import filters.StatsFilter
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.mvc.Filter
import services.{SunService, WeatherService}

import scala.concurrent.Future

/**
  *
  * AppApplicationLoader class
  * <p/>
  * Description...
  *
  */
class AppApplicationLoader extends ApplicationLoader {
  override def load(context: ApplicationLoader.Context): Application = {
    LoggerConfigurator(context.environment.classLoader).foreach {
      configuration =>
        configuration.configure(context.environment)
    }
    (new BuiltInComponentsFromContext(context) with AppComponents).application
  }
}

trait AppComponents extends BuiltInComponents with AhcWSComponents {
  lazy val assets: Assets = wire[Assets]
  lazy val prefix: String = "/"
  lazy val router: Router = wire[Routes]
  lazy val applicationController: ApplicationController = wire[ApplicationController]
  lazy val sunService: SunService = wire[SunService]
  lazy val weatherService: WeatherService = wire[WeatherService]
  lazy val statsFilter: Filter = wire[StatsFilter]
  override lazy val httpFilters = Seq(statsFilter)
  lazy val statsActor = actorSystem.actorOf(Props(wire[StatsActor]), StatsActor.name)

  val onStart: Unit = {
    Logger.info("The app about to start")
    statsActor ! Ping
  }

  applicationLifecycle.addStopHook {
    () =>
      Logger.info("The app about to stop")
      Future.successful(Unit)
  }
}
