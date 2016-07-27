package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core2x.api.OutGoingMsg._
import org.bigbluebutton.core2x.json.senders.ValidateAuthTokenSuccessReplyOutMsgJsonSender

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
