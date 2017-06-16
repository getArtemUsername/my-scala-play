package actors

import actors.StatsActor.{GetStats, Ping, RequestReceived}
import akka.actor.Actor
import play.api.Logger

/**
  *
  * StatsActor class
  * <p/>
  * Description...
  *
  */
class StatsActor extends Actor {
  var counter = 0

  override def receive: Receive = {
    case Ping => Logger.info("Actor ping")
    case RequestReceived => counter += 1
    case GetStats => sender() ! counter
  }
}

object StatsActor {
  val name = "statsActor"
  val path = s"/user/$name"
  
  case object Ping
  case object RequestReceived
  case object GetStats
}
