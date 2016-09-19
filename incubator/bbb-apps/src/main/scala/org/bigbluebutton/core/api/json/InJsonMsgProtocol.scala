package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.domain._
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsBoolean, JsValue, JsonFormat }

trait InJsonMsgProtocol extends AnyValTypeProtocol {
  this: DefaultJsonProtocol =>

  implicit val meetingExtensionPropFormat = jsonFormat3(MeetingExtensionProp2x)
  implicit val meetingRecordingPropFormat = jsonFormat3(MeetingRecordingProp)
  implicit val meetingPropertiesFormat = jsonFormat10(MeetingProperties2x)

  implicit val createMeetingRequestInMsgBodyFormat = jsonFormat1(CreateMeetingRequestInMsgBody)
  implicit val createMeetingRequestInMsg2xFormat = jsonFormat2(CreateMeetingRequestInMsg2x)

  implicit val chatProperties2xFormat = jsonFormat9(ChatProperties2x)
  implicit val sendPublicChatInMsgBodyFormat = jsonFormat1(SendPublicChatInMsgBody)
  implicit val sendPublicChatInMsg2xFormat = jsonFormat2(SendPublicChatInMsg2x)

  //  case class SchemaMap(annotation: scala.collection.immutable.HashMap[String, String])
  //  val schemaMapMarshall = jsonFormat1(SchemaMap.apply)
  //  implicit val annotationFormat = jsonFormat1(SchemaMap.apply)
  implicit val sendWbAnnotationReqInMsg2xFormat = jsonFormat2(SendWbAnnotationReqInMsg2x)
  implicit val sendWbAnnotationReqInMsgBodyFormat = jsonFormat1(SendWbAnnotationReqInMsgBody)
  //  implicit val whiteboardProperties2xFormat = jsonFormat2(WhiteboardProperties2x)
  //  implicit val whiteboardProperties2xFormat = jsonFormat3(WhiteboardProperties2x)
}
