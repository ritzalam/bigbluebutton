package org.bigbluebutton.core2x.api

import org.bigbluebutton.core2x.domain._

trait MsgType
object DirectMsgType extends MsgType
object BroadcastMsgType extends MsgType
object SystemMsgType extends MsgType

case class MsgHeader(msgType: MsgType, meetingId: Option[IntMeetingId], senderId: Option[SenderId],
  receiverId: Option[ReceiverId], replyTo: Option[ReplyTo])

