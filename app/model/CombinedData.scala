package model

import play.api.libs.json.Json

/**
  *
  * CombinedData class
  * <p/>
  * Description...
  *
  */
case class CombinedData(sunInfo:SunInfo, temperature: Double, requests: Int)

object CombinedData {
  implicit val writes = Json.writes[CombinedData]
}
