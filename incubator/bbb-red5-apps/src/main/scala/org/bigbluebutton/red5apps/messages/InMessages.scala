package org.bigbluebutton.red5apps.messages

case class UserDisconnected(meetingId: String, userId: String, sessionId: String)
case class UserConnected(meetingId: String, userId: String, muted: java.lang.Boolean, lockSettings:
  java.util.Map[java.lang.String, java.lang.Boolean], sessionId: String)


case class MeetingEnded(meetingId: String)

case class MeetingCreated(meetingId: String, record: Boolean)

