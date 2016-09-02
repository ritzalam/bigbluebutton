/**
  * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
  *
  * Copyright (c) 2016 BigBlueButton Inc. and by respective authors (see below).
  *
  * This program is free software; you can redistribute it and/or modify it under the
  * terms of the GNU Lesser General Public License as published by the Free Software
  * Foundation; either version 3.0 of the License, or (at your option) any later
  * version.
  *
  * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
  * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
  * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public License along
  * with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
  *
  */

package org.bigbluebutton.bbbred5apps

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.google.gson.{JsonObject, JsonParser}
import org.bigbluebutton.messages.AbstractMessage
import org.bigbluebutton.messages.chat.{GetChatHistoryRequestMessage, SendPrivateChatMessage, SendPublicChatMessage}
import org.bigbluebutton.red5.client.messaging.{BroadcastClientJsonMessage, ConnectionInvokerService, DirectClientJsonMessage}

import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.concurrent.Await

object SendToClientActor {
  def props(system: ActorSystem, connectionService: ConnectionInvokerService): Props =
    Props(classOf[SendToClientActor], system, connectionService)

}

class SendToClientActor(val aSystem: ActorSystem, val connectionService: ConnectionInvokerService)
  extends Actor with ActorLogging {
  log.warning("Creating a new SendToClientActor warn")


  def receive = {
    case msg: JsonMessageWithName => {
      msg.name match {
        case SendPublicChatMessage.NAME => handleSendChatMessage(msg);
        case SendPrivateChatMessage.NAME => handleSendChatMessage(msg);
        case "get_chat_history_reply" => log.warning("0000222")


        case _ => log.warning("Default 2222 handling of message " + msg)
      }

    }

    case msg: Any => log.warning("Default 111 handling of message " + msg)
  }

  def constructCommandString(messageName: String) :String = messageName.concat("Command") //TODO
  // start using

  def extractMeetingId(message: String) : String = {
    val parser: JsonParser = new JsonParser
    val obj: JsonObject = parser.parse(message).asInstanceOf[JsonObject]

    if (obj.has("header")) {
      val header: JsonObject = obj.get("header").getAsJsonObject
      header.get("meeting_id").getAsString
    } else ""
  }


  def handleSendChatMessage(msg: JsonMessageWithName): Unit = {
    val message = msg.json
    val messageName = msg.name

    val parser: JsonParser = new JsonParser
    val obj: JsonObject = parser.parse(message).asInstanceOf[JsonObject]

    if (obj.has("header") && obj.has("body")) {
      val header: JsonObject = obj.get("header").getAsJsonObject

      if (header.has("name")) {
        val meetingId: String = extractMeetingId(msg.json)

        if (SendPublicChatMessage.NAME.equals(messageName)) {
          val m = new BroadcastClientJsonMessage(meetingId, "ChatReceivePublicMessageCommand",
            message)
          connectionService.sendMessage(m)
        } else if (SendPrivateChatMessage.NAME.equals(messageName)) {
          val privateMessageObject = SendPrivateChatMessage.fromJson(message)
          if (privateMessageObject != null) {
            val toUserId: String = privateMessageObject.body.chatMessage.toUserID
            val receiver = new DirectClientJsonMessage(meetingId, toUserId,
              "ChatReceivePrivateMessageCommand", message)
            connectionService.sendMessage(receiver)

            val sender = new DirectClientJsonMessage(meetingId, privateMessageObject.body
              .requesterID, "ChatReceivePrivateMessageCommand", message)
            connectionService.sendMessage(sender)
          }
        }
      }

    }
  }

  def handleSendDirect(msg:JsonMessageWithName): Unit = {
    val parser: JsonParser = new JsonParser
    val obj: JsonObject = parser.parse(msg.json).asInstanceOf[JsonObject]
    if (obj.has("body")) {
      val body: JsonObject = obj.get("body").getAsJsonObject

      val requesterID = body.get("requesterID").getAsString


      if (body != null) {
        val meetingId = extractMeetingId(msg.json)
        val m: DirectClientJsonMessage = new DirectClientJsonMessage(meetingId,
          requesterID, msg.name, body.getAsString)
        connectionService.sendMessage(m)
      }
    }
  }


}
