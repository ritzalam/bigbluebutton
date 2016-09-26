package org.bigbluebutton.core.api

import org.bigbluebutton.core.domain._

trait MsgType
object DirectMsgType extends MsgType
object BroadcastMsgType extends MsgType
object SystemMsgType extends MsgType

case class MsgHeader(name: String, msgType: MsgType, dest: String, src: String, replyTo: Option[ReplyTo])

