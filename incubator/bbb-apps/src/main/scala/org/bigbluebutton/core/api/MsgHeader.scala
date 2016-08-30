package org.bigbluebutton.core.api

import org.bigbluebutton.core.domain.{ IntMeetingId, ReceiverId, ReplyTo, SenderId }

trait MsgType
object DirectMsgType extends MsgType
object BroadcastMsgType extends MsgType
object SystemMsgType extends MsgType

case class MsgHeader(msgType: MsgType, meetingId: Option[IntMeetingId], senderId: Option[SenderId],
  receiverId: Option[ReceiverId], replyTo: Option[ReplyTo])

