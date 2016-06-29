package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core2x.api.OutGoingMsg._
import org.bigbluebutton.core2x.pubsub.senders._

object MessageSenderActor2x {
  def props(msgSender: MessageSender): Props =
    Props(classOf[MessageSenderActor2x], msgSender)
}

class MessageSenderActor2x(val service: MessageSender)
    extends Actor with ActorLogging
    with ValidateAuthTokenSuccessReplyOutMessageJsonSender {

  def receive = {
    case msg: ValidateAuthTokenSuccessReplyOutMsg => handleValidateAuthTokenSuccessReplyOutMessage(msg)
    case _ => // do nothing
  }

}
