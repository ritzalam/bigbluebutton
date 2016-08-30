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
import org.bigbluebutton.bbbred5apps.messages.{UserConnected, UserDisconnected}
import org.bigbluebutton.bbbred5apps.util.LogHelper
import org.bigbluebutton.red5.pubsub.MessagePublisher

class BigBlueButtonRed5App(red5Publisher: MessagePublisher) extends IBigBlueButtonRed5App with LogHelper {

  implicit val system = ActorSystem("bigbluebutton-red5-apps-system")
  val connectionManager = system.actorOf(ConnectionManager.props(system, red5Publisher), "connection-manager")


  def userDisconnected(meetingId: String, userId: String, sessionId: String): Unit = {
      connectionManager ! UserDisconnected(meetingId, userId, sessionId)
  }

  def userConnected(meetingId: String, userId: String, muted: java.lang.Boolean, lockSettings:
  java.util.Map[java.lang.String, java.lang.Boolean], sessionId: String): Unit = {
    connectionManager ! UserConnected(meetingId, userId, muted, lockSettings, sessionId)

  }

  def validateAuthToken(meetingId: String, userId: String, token:String, correlationId:String,
                        sessionId:String): Unit = {
    red5Publisher.validateAuthToken(meetingId, userId, token, correlationId, sessionId)
  }

  def handleJsonMessage(json: String): Unit = {
    red5Publisher.handleJsonMessage(json)
  }

}
