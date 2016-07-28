package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.api.OutGoingMsg._
import org.bigbluebutton.core.api.json.senders.ValidateAuthTokenSuccessReplyOutMsgJsonSender

object MsgSenderActor2X {
  def props(msgSender: MessageSender): Props =
    Props(classOf[MsgSenderActor2X], msgSender)
}

class MsgSenderActor2X(val service: MessageSender)
    extends Actor with ActorLogging
    with ValidateAuthTokenSuccessReplyOutMsgJsonSender {

  def receive = {
    case msg: ValidateAuthTokenSuccessReplyOutMsg => handleValidateAuthTokenSuccessReplyOutMessage(msg)
    case _ => // do nothing
  }

}
