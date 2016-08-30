package org.bigbluebutton.core.api

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.core.api.OutGoingMsg._
import org.bigbluebutton.core.api.json.senders.ValidateAuthTokenSuccessReplyOutMsgJsonSender

object MsgSenderActor {
  def props(msgSender: MessageSender): Props =
    Props(classOf[MsgSenderActor], msgSender)
}

class MsgSenderActor(val service: MessageSender)
    extends Actor with ActorLogging
    with ValidateAuthTokenSuccessReplyOutMsgJsonSender {

  def receive = {
    case msg: ValidateAuthTokenSuccessReplyOutMsg => handleValidateAuthTokenSuccessReplyOutMessage(msg)
    case _ => // do nothing
  }

}
