package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.common.message.PubSubPongMessage2x
import org.bigbluebutton.common.message.PubSubPongMessageMarshaller

object MessageSenderActor2x {
  def props(msgSender: MessageSender): Props =
    Props(classOf[MessageSenderActor2x], msgSender)
}

class MessageSenderActor2x(val service: MessageSender)
    extends Actor with ActorLogging {

  def receive = {
    case msg: PubSubPongMessage2x =>
      object Marshaller extends PubSubPongMessageMarshaller
      val json = Marshaller.marshall(msg)
      println("Sending " + json)
      service.send(msg.header.channel, json)
  }
}