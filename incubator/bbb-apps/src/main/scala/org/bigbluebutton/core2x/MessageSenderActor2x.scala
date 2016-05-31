package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core2x.api.OutGoingMessage._
import org.bigbluebutton.core2x.pubsub.senders._

object MessageSenderActor2x {
  def props(msgSender: MessageSender): Props =
    Props(classOf[MessageSenderActor2x], msgSender)
}

class MessageSenderActor2x(val service: MessageSender)
    extends Actor with ActorLogging
    with MeetingCreatedEventSender
    with UserJoinedEventSender
    with UserRegisteredEventSender
    with ValidateAuthTokenReplySender
    with ValidateAuthTokenTimedOutEventSender {

  def receive = {
    case msg: MeetingCreated => handleMeetingCreated(msg)
    case msg: UserRegisteredEvent2x => handleUserRegistered(msg)
    case msg: ValidateAuthTokenReply2x => handleValidateAuthTokenReply(msg)
    case msg: ValidateAuthTokenTimedOut => handleValidateAuthTokenTimedOut(msg)
    case msg: UserJoinedEvent2x => handleUserJoinedEvent2x(msg)
    case _ => // do nothing
  }

}
