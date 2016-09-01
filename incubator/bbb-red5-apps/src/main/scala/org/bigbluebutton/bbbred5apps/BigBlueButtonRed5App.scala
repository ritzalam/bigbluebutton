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

import akka.actor.ActorSystem
import com.google.gson.{JsonElement, JsonObject, JsonParser}
import org.bigbluebutton.bbbred5apps.messages.{UserConnected, UserDisconnected}
import org.bigbluebutton.bbbred5apps.util.LogHelper
import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService
import org.bigbluebutton.red5.pubsub.MessagePublisher

case class JsonMessageWithName(name:String, json: String)
class BigBlueButtonRed5App(red5Publisher: MessagePublisher, connectionService:
ConnectionInvokerService) extends IBigBlueButtonRed5App with LogHelper {

  implicit val system = ActorSystem("bigbluebutton-red5-apps-system")
  val meetingManager = system.actorOf(MeetingManager.props(system, red5Publisher),
    "meeting-manager")

  val sendToClientActor = system.actorOf(SendToClientActor.props(system, connectionService),
    "send-to-client-actor")


  def userDisconnected(meetingId: String, userId: String, sessionId: String): Unit = {
    meetingManager ! UserDisconnected(meetingId, userId, sessionId)
  }

  def userConnected(meetingId: String, userId: String, muted: java.lang.Boolean, lockSettings:
  java.util.Map[java.lang.String, java.lang.Boolean], sessionId: String): Unit = {
    meetingManager ! UserConnected(meetingId, userId, muted, lockSettings, sessionId)

  }

  def validateAuthToken(meetingId: String, userId: String, token:String, correlationId:String,
                        sessionId:String): Unit = {
    red5Publisher.validateAuthToken(meetingId, userId, token, correlationId, sessionId)
  }

  def handleJsonMessage(json: String): Unit = {
    red5Publisher.handleJsonMessage(json)
  }


  def sendJsonMessageToPubSub (channel: String, jsonMessage: String): Unit = {
    logger.warn("________App::sendJsonMessageToPubSub" + jsonMessage)
    red5Publisher.publishToChannel(channel, jsonMessage)
  }

  // Chat
  def getChatHistory(meetingID: String, requesterID: String, replyTo: String): Unit = {
    red5Publisher.getChatHistory(meetingID, requesterID, replyTo)
  }

  def sendPublicMessage(meetingID: String, requesterID: String, message: java.util.Map[java
  .lang.String, String]): Unit = {
    red5Publisher.sendPublicMessage(meetingID, requesterID, message)
  }

  def sendPrivateMessage(meetingID: String, requesterID: String, message: java.util.Map[java
  .lang.String, String]): Unit = {
    red5Publisher.sendPrivateMessage(meetingID, requesterID, message)
  }


  def handleChatMessageFromPubSub(json: String): Unit = {
    val messageName = messageNameExtractor(json)
    logger.warn("________sendToClientActor ! " + messageName)


    sendToClientActor ! JsonMessageWithName(messageName, json)

  }



  def messageNameExtractor(json: String) : String = {
    val parser: JsonParser = new JsonParser
    val obj: JsonObject = parser.parse(json).asInstanceOf[JsonObject]
    var answer = ""

    if (obj.has("header") && obj.has("payload")) {
      val header = obj.get("header")

      val headerObj = header.getAsJsonObject
      if (headerObj.has("name")) {
        val messageNameElement: JsonElement = headerObj.get("name")
        answer = messageNameElement.getAsString
      }
    }

    answer

  }
}
