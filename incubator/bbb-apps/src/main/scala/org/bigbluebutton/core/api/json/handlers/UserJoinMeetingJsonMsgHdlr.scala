package org.bigbluebutton.core.api.json.handlers

import org.bigbluebutton.core.api.IncomingMsg.UserJoinMeetingInMessage
import org.bigbluebutton.core.api.RedisMsgHdlrActor
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, InHeaderAndJsonBody, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.client.ClientUserAgent
import org.bigbluebutton.messages.UserJoinMeetingMessage

trait UserJoinMeetingJsonMsgHdlr extends UnhandledJsonMsgHdlr {
  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: InHeaderAndJsonBody): Unit = {
    def publish(meetingId: IntMeetingId, userID: IntUserId, token: SessionToken, sessionID: SessionId,
      presenceID: ClientId, userAgent: ClientUserAgent): Unit = {
      log.debug(s"Publishing ${msg.header.name} [ $userID ]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new UserJoinMeetingInMessage(meetingId, userID, token, sessionID, presenceID, userAgent)))
      // TODO use senderId or userID?
    }

    if (msg.header.name == UserJoinMeetingMessage.NAME) {
      log.debug("Received JSON message [" + msg.header.name + "]")
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

