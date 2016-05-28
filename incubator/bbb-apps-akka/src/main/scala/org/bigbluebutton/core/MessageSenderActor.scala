package org.bigbluebutton.core

import akka.actor.Actor
import akka.actor.ActorLogging
import akka.actor.Props
import org.bigbluebutton.core.api.OutGoingMessage._
import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.pubsub.senders.MeetingMessageToJsonConverter
import org.bigbluebutton.core.pubsub.senders.UsersMessageToJsonConverter
import org.bigbluebutton.common.converters.ToJsonEncoder

object MessageSenderActor {
  def props(msgSender: MessageSender): Props =
    Props(classOf[MessageSenderActor], msgSender)
}

class MessageSenderActor(val service: MessageSender)
    extends Actor with ActorLogging {

  val encoder = new ToJsonEncoder()

  def receive = {
    case msg: MeetingCreated => handleMeetingCreated(msg)
    case msg: UserRegisteredEvent2x => handleUserRegistered(msg)
    case msg: ValidateAuthTokenReply2x => handleValidateAuthTokenReply(msg)
    case msg: ValidateAuthTokenTimedOut => handleValidateAuthTokenTimedOut(msg)
    case _ => // do nothing
  }

  private def handleMeetingCreated(msg: MeetingCreated) {
    val json = MeetingMessageToJsonConverter.meetingCreatedToJson(msg)
    service.send(MessagingConstants.FROM_MEETING_CHANNEL, json)
  }

  private def handleUserRegistered(msg: UserRegisteredEvent2x) {
    val json = UsersMessageToJsonConverter.userRegisteredToJson(msg)
    service.send(MessagingConstants.FROM_MEETING_CHANNEL, json)
    handleRegisteredUser(msg);
  }

  private def handleRegisteredUser(msg: UserRegisteredEvent2x) {
    val json = UsersMessageToJsonConverter.userRegisteredToJson(msg)
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }

  private def handleValidateAuthTokenReply(msg: ValidateAuthTokenReply2x) {
    println("**** handleValidateAuthTokenReply *****")
    val json = UsersMessageToJsonConverter.validateAuthTokenReplyToJson(msg)
    //println("************** Publishing [" + json + "] *******************")
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }

  private def handleValidateAuthTokenTimedOut(msg: ValidateAuthTokenTimedOut) {
    println("**** handleValidateAuthTokenTimedOut *****")
    val json = UsersMessageToJsonConverter.validateAuthTokenTimeoutToJson(msg)
    //println("************** Publishing [" + json + "] *******************")
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }
}
