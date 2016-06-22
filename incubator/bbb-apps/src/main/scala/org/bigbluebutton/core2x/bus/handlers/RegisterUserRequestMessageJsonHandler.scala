package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.bus.helpers.StringToRoleHelper
import org.bigbluebutton.core2x.bus.{ IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.messages.RegisterUserRequestMessage
import org.bigbluebutton.messages.vo.UserInfoBody

trait RegisterUserRequestMessageJsonHandler extends UnhandledReceivedJsonMessageHandler {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {

    def publish(meetingId: String): Unit = {

    }

    //    def extractRoles(body: UserInfoBody):Set[Role2x] = {
    //      import scala.collection.convert.wrapAsScala._
    //      val r = asScalaBuffer(body.roles).toSet
    //      val roles = r.map ( b => StringToRoleHelper.convert(b))
    //      roles
    //    }

    if (msg.name == RegisterUserRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = RegisterUserRequestMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        userId <- Option(m.body.id)
        extUserId <- Option(m.body.externalId)
        name <- Option(m.body.name)
        authToken <- Option(m.body.authToken)
        avatar <- Option(m.body.avatarUrl)
        logoutUrl <- Option(m.body.logoutUrl)
        welcome <- Option(m.body.welcomeMessage)
        //config <- Set(Option(m.body.config))
        //extData <- Set(Option(m.body.externalData))
        //roles <- : Set[Role2x],

        //dialNumbers: Set[DialNumber],
        //config: Set[String],
        //extData: Set[String]
      } yield publish(meetingId)
    } else {
      super.handleReceivedJsonMessage(msg)
    }
  }

}
