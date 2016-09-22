package org.bigbluebutton.core.api.json

import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.domain._
import spray.json.DefaultJsonProtocol

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

  implicit val sendWbAnnotationReqInMsg2xFormat = jsonFormat2(SendWbAnnotationReqInMsg2x)

  implicit val sendWbHistoryReqInMsg2xBodyFormat = jsonFormat1(SendWbHistoryReqInMsg2xBody)
  implicit val sendWbHistoryReqInMsg2xFormat = jsonFormat2(SendWbHistoryReqInMsg2x)

  implicit val sendWbHistoryReplyInMsg2xBodyFormat = jsonFormat1(SendWbHistoryReplyInMsg2xBody)
  implicit val sendWbHistoryReplyInMsg2xFormat = jsonFormat2(SendWbHistoryReplyInMsg2x)
}
