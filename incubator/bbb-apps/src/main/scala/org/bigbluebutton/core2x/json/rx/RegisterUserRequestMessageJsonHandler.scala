package org.bigbluebutton.core2x.json.rx

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.RegisterUserInMessage
import org.bigbluebutton.core2x.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.messages.RegisterUserRequestMessage
import org.bigbluebutton.messages.vo.UserInfoBody

trait RegisterUserRequestMessageJsonHandler extends UnhandledReceivedJsonMessageHandler
    with RegisterUserRequestMessageJsonHandlerHelper {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {

    def publish(meetingId: IntMeetingId, msg: RegisterUserInMessage): Unit = {
      log.debug("publishing message [RegisterUserRequestInMessage]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value, msg))
    }

    if (msg.name == RegisterUserRequestMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = RegisterUserRequestMessage.fromJson(msg.data)
      for {
        m2 <- convertMessage(m)
      } yield publish(m2.meetingId, m2)
    } else {
      super.handleReceivedJsonMessage(msg)
    }
  }

}

trait RegisterUserRequestMessageJsonHandlerHelper {

  def convertMessage(msg: RegisterUserRequestMessage): Option[RegisterUserInMessage] = {
    for {
      header <- Option(msg.header)
      meetingId <- Option(header.meetingId)
      body <- Option(msg.body)
      userId <- Option(body.id)
      extUserId <- Option(body.externalId)
      name <- Option(body.name)
      authToken <- Option(body.authToken)
      avatar <- Option(body.avatarUrl)
      logoutUrl <- Option(body.logoutUrl)
      welcome <- Option(body.welcomeMessage)
      roles = extractRoles(body)
      dialNumbers = extractDialInNumbers(body)
      config <- Option(body.config)
      extData <- Option(body.externalData)
    } yield new RegisterUserInMessage(
      IntMeetingId(meetingId), IntUserId(userId),
      Name(name), roles, ExtUserId(extUserId),
      SessionToken(authToken), Avatar(avatar),
      LogoutUrl(logoutUrl), Welcome(welcome),
      dialNumbers, config, extData)
  }

  def extractRoles(body: UserInfoBody): Set[Role2x] = {
    import scala.collection.convert.wrapAsScala._
    // convert the list to a set
    val r = asScalaBuffer(body.roles).toSet
    // convert the roles from string to role. This results into
    // Option(Role), Option(Role), None
    val x = r.map(b => ConvertRoleHelper.convert(b))
    // Flatten to remove Option and None from above
    // http://stackoverflow.com/questions/10104558/how-to-filter-nones-out-of-listoption
    x.flatten
  }

  def extractDialInNumbers(body: UserInfoBody): Set[DialNumber] = {
    import scala.collection.convert.wrapAsScala._
    Option(body.dialInNumbers) match {
      case Some(dialIns) =>
        val d = asScalaBuffer(body.dialInNumbers).toSet
        d.map(x => DialNumber(x))
      case None => Set.empty
    }
  }
}