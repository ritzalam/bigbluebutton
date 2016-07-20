package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.UserJoinMeetingInMessage
import org.bigbluebutton.core2x.api.SessionId
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.messages.UserJoinMeetingMessage

trait UserJoinMeetingMessageJsonHandler extends UnhandledReceivedJsonMessageHandler
    with UserJoinMeetingMessageJsonHandlerHelper {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, userID: IntUserId, token: AuthToken, sessionID: SessionId,
      presenceID: PresenceId, userAgent: PresenceUserAgent): Unit = {
      log.debug(s"Publishing ${msg.name} [ $userID ]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new UserJoinMeetingInMessage(meetingId, userID, token, sessionID, presenceID, userAgent)))
      // TODO use senderId or userID?
    }

    if (msg.name == UserJoinMeetingMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = UserJoinMeetingMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        userID <- Option(m.body.userID)
        token <- Option(m.body.token)
        sessionID <- Option(m.body.sessionID)
        presenceID <- Option(m.body.presenceID)
        userAgent = convertPresenceUserAgent(m)
      } yield publish(IntMeetingId(meetingId), IntUserId(userID), AuthToken(token),
        SessionId(sessionID), PresenceId(presenceID), userAgent)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}

trait UserJoinMeetingMessageJsonHandlerHelper {
  def convertPresenceUserAgent(msg: UserJoinMeetingMessage): PresenceUserAgent = {
    // TODO

    new PresenceUserAgent {}
  }
}
