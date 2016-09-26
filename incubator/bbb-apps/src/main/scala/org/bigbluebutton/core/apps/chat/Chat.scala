package org.bigbluebutton.core.apps.chat

import org.bigbluebutton.core.domain._

case class ChatProperties2x(
  fromColor: Color,
  fromTime: FromTime,
  chatType: ChatType,
  toUserID: IntUserId,
  message: ChatMessageText,
  fromUsername: Username,
  fromUserID: IntUserId,
  toUsername: Username,
  fromTimezoneOffset: TimeZoneOffset)
