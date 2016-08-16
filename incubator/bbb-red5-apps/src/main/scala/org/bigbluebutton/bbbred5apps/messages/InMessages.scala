package org.bigbluebutton.bbbred5apps.messages

case class UserDisconnected(meetingId: String, userId: String, sessionId: String)
case class UserConnected(meetingId: String, userId: String, sessionId: String)
