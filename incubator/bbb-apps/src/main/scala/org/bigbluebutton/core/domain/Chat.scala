package org.bigbluebutton.core.domain

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
