package org.bigbluebutton.common

import org.bigbluebutton.common.message._
import spray.json.DefaultJsonProtocol
import spray.json._
import scala.util.{Failure, Success}
import org.scalatest._

/**
  * Created by ralam on 2/23/2017.
  */
class JsonMsgUnmarshallerTest extends UnitSpec with TestFixtures {
  object JsonProtocol extends DefaultJsonProtocol with HeaderProtocol

  it should "fail to decode the header" in {

    val carJson =
      "{ \"brand\" : \"Mercedes\", \"doors\" : 5," +
        "  \"owners\" : [\"John\", \"Jack\", \"Jill\"]," +
        "  \"nestedObject\" : { \"field\" : \"value\" } }"



    JsonMsgUnmarshaller.decode(carJson)  match {
      case Success(msg) => fail("It should have failed to decode header")
      case Failure(ex) =>
        println(ex.getMessage)
        succeed
    }
  }
}
