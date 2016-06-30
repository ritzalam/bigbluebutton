package org.bigbluebutton.core2x.api

import org.bigbluebutton.core2x.domain.{ IntMeetingId, IntUserId }

trait MsgType
object DirectMsgType extends MsgType
object BroadcastMsgType extends MsgType
object SystemMsgType extends MsgType

case class MsgHeader(meetingId: IntMeetingId, msgType: MsgType, senderId: Option[SenderId],
  receiverId: Option[ReceiverId], replyTo: Option[ReplyTo])

case class ReplyTo(value: String) extends AnyVal
case class SessionId(value: String) extends AnyVal
case class SenderId(value: String) extends AnyVal
case class ReceiverId(value: String) extends AnyVal