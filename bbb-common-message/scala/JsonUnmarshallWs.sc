import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.databind.{DeserializationFeature, JsonNode, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import org.bigbluebutton.common.JsonUtil
import org.bigbluebutton.common.message.{CreateMeetingRequestMessage2x, CreateMeetingRequestMessageBody, Header}

import scala.util.Try

val createMeetingHeader: Header = Header(CreateMeetingRequestMessage2x.NAME, CreateMeetingRequestMessage2x.CHANNEL)
val createMeetingPayload: CreateMeetingRequestMessageBody =
  new CreateMeetingRequestMessageBody("id", "externalId", "parentId", "name",
    record = true, voiceConfId = "85115", duration = 600,
    autoStartRecording = true, allowStartStopRecording = true,
    webcamsOnlyForModerator = true, moderatorPass = "MP",
    viewerPass = "AP", createTime = 10000L, createDate = "Now",
    isBreakout = false, sequence = 0)

val createMeetingRequestMsg = CreateMeetingRequestMessage2x(createMeetingHeader, createMeetingPayload)

val mapper = new ObjectMapper() with ScalaObjectMapper
mapper.registerModule(DefaultScalaModule)
mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

val jsonMsg  = JsonUtil.toJson(createMeetingRequestMsg)

val node: JsonNode = mapper.readValue[JsonNode](jsonMsg)
val header: JsonNode = node.get("header")
val foo = Option(node.get("foo"))
val payload = Option(node.get("payload"))
val headerString = header.toString

val h = mapper.readValue[Header](headerString)

val carJson =
  "{ \"brand\" : \"Mercedes\", \"doors\" : 5," +
    "  \"owners\" : [\"John\", \"Jack\", \"Jill\"]," +
    "  \"nestedObject\" : { \"field\" : \"value\" } }"

try {
  val car = mapper.readValue[CreateMeetingRequestMessage2x](carJson)
  println(car)
} catch {
  case ex: JsonParseException =>  println("EXception")
  case _: Throwable => println("Got some other kind of exception")

}
