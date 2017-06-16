import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.mockito.Mockito
import org.mockito.Mockito.when
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import org.scalatestplus.play.PlaySpec
import play.api.libs.json.Json
import play.api.libs.ws.{WSClient, WSRequest, WSResponse}
import services.SunService

import scala.concurrent.Future

/**
  *
  * ApplicationSpec class
  * <p/>
  * Description...
  *
  */
class ApplicationSpec extends PlaySpec with MockitoSugar with ScalaFutures {
  "DateTimeFormat" must {
    "return 1970 as th beginning of epoch" in {
      val beginning = new DateTime(0)
      val formattedYear = DateTimeFormat.forPattern("YYYY").print(beginning)
      formattedYear mustBe "1970"
    }
  }
  
  "SunService" must {
    "retrieve correct sunset and sunrise information" in {
      val wsClientStub = mock[WSClient]
      val wsRequestStub = mock[WSRequest]
      val wsResponseStub = mock[WSResponse]
      val expectedResponse = 
        """{
          |  "results":  {
          |    "sunrise": "2016-04-14T20:18:12+00:00",
          |    "sunset": "2016-04-15T07:31:52+00:00"
          |  }
          |}""".stripMargin
      val jsResult = Json.parse(expectedResponse)
      val lat = -33.8830
      val lon = 151.2167
      val url = "http://api.sunrise-sunset.org/" + s"json?lat=$lat&lng=$lon&formatted=0"
      when(wsResponseStub.json).thenReturn(jsResult)
      when(wsRequestStub.get()).thenReturn(Future.successful(wsResponseStub))
      when(wsClientStub.url(url)).thenReturn(wsRequestStub)
      
      val sunService = new SunService(wsClientStub)
      val resultF = sunService.getSunInfo(lat, lon)
      
      whenReady(resultF) {
        res =>
          res.sunrise mustBe  "01:18:12"
          res.sunset mustBe  "12:31:52"
      }
    }
  }

}
