package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.api.IncomingMsg.{ CreateMeetingRequestInMsg2x, CreateMeetingRequestInMsgBody }
import org.bigbluebutton.core.api.InMessageHeader
import org.bigbluebutton.core.domain._
import spray.json.{ DefaultJsonProtocol, DeserializationException, JsBoolean, JsValue, JsonFormat }

trait InJsonMsgProtocol extends AnyValTypeProtocol {
  this: DefaultJsonProtocol =>

  implicit val meetingExtensionPropFormat = jsonFormat3(MeetingExtensionProp2x)
  implicit val meetingRecordingPropFormat = jsonFormat3(MeetingRecordingProp)
  implicit val meetingPropertiesFormat = jsonFormat10(MeetingProperties2x)
  implicit val inMessageHeaderFormat = jsonFormat4(InMessageHeader)
  implicit val createMeetingRequestInMsgBodyFormat = jsonFormat1(CreateMeetingRequestInMsgBody)
  implicit val createMeetingRequestInMsg2xFormat = jsonFormat2(CreateMeetingRequestInMsg2x)
}
