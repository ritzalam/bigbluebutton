package org.bigbluebutton.core2x.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.SessionCreatedInMsg
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.core2x.models.MeetingStateModel

trait SessionCreatedMessageHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleSessionCreatedMessage(message: SessionCreatedInMsg): Unit = {
    def getClientWithSessionToken(user: User, sessionToken: SessionToken): Client2x = {
      User.findClientWithSessionToken(user.client, sessionToken) match {
        case Some(client) => client

        case None =>
          val client = User.create(new ClientId(sessionToken.value + System.currentTimeMillis()), user.id,
            message.sessionToken, FlashWebUserAgent)
          User.add(user, client)
          client
      }
    }

    def addNewSessionForComponent(client2x: Client2x, componentId: ComponentId, sessionId: SessionId): Client2x = {
      componentId.value match {
        case "apps" =>
          val sessionIds = client2x.appsComponent.sessionIds + sessionId
          val component = new AppsComponent(sessionIds)
          Client2x.save(client2x, component)
        case "voice" =>
          val sessionIds = client2x.voiceComponent.sessionIds + sessionId
          val component = new VoiceComponent(sessionIds)
          Client2x.save(client2x, component)
        case "video" =>
          val sessionIds = client2x.webCamComponent.sessionIds + sessionId
          val component = new WebCamComponent(sessionIds)
          Client2x.save(client2x, component)
        case "screenshare" =>
          val sessionIds = client2x.screenShareComponent.sessionIds + sessionId
          val component = new ScreenShareComponent(sessionIds)
          Client2x.save(client2x, component)
      }
    }

    def send(): Unit = {

    }

    for {
      user <- state.usersModel.findUserWithToken(message.sessionToken)
      client = getClientWithSessionToken(user, message.sessionToken)
      client2 = addNewSessionForComponent(client, message.component, message.sessionId)
      newUser = User.update(client, user, client2)
    } yield send()
  }
}
