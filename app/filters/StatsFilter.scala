package filters

import actors.StatsActor
import akka.actor.ActorSystem
import akka.stream.Materializer
import play.api.Logger
import play.api.mvc.{Filter, RequestHeader, Result}

import scala.concurrent.Future

/**
  *
  * StatsFilter class
  * <p/>
  * Description...
  *
  */
class StatsFilter(actorSystem: ActorSystem,  val mat: Materializer) extends Filter {
  override def apply(f: (RequestHeader) => Future[Result])(rh: RequestHeader): Future[Result] = {
    Logger.info(s"Serving another request: ${rh.path}")
    actorSystem.actorSelection(StatsActor.path) ! StatsActor.RequestReceived
    f(rh)
  }
}
