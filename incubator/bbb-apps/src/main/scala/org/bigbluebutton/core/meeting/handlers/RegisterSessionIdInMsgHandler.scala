package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.{ OutMessageGateway, TokenIdFactory }
import org.bigbluebutton.core.api.IncomingMsg.RegisterSessionIdInMsg
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.meeting.models.MeetingStateModel

trait RegisterSessionIdInMsgHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleRegisterSessionIdInMsg(message: RegisterSessionIdInMsg): Unit = {
    def getClientWithSessionToken(user: User, sessionToken: SessionToken): Client = {
      User.findClientWithSessionToken(user.client, sessionToken) match {
        case Some(client) => client

        case None =>
          val clientId = new ClientId(TokenIdFactory.genClientId(sessionToken))
          val client = User.create(clientId, user.id,
            message.sessionToken, FlashWebUserAgent)
          User.add(user, client)
          client
      }
    }

    def addNewSessionForComponent(client2x: Client, componentId: ComponentId, sessionId: SessionId): Client = {
      componentId.value match {
        case "apps" =>
          val sessionIds = client2x.appsComponent.sessionIds + sessionId
          val component = new AppsComponent(sessionIds)
          Client.save(client2x, component)
        case "voice" =>
          val sessionIds = client2x.voiceComponent.sessionIds + sessionId
          val component = new VoiceComponent(sessionIds)
          Client.save(client2x, component)
        case "video" =>
          val sessionIds = client2x.webCamComponent.sessionIds + sessionId
          val component = new WebCamComponent(sessionIds)
          Client.save(client2x, component)
        case "screenshare" =>
          val sessionIds = client2x.screenShareComponent.sessionIds + sessionId
          val component = new ScreenShareComponent(sessionIds)
          Client.save(client2x, component)
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
