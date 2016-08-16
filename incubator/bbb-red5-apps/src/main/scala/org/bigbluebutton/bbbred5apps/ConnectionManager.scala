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

import akka.pattern.ask

import scala.concurrent.duration._
import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import akka.util.Timeout
import org.bigbluebutton.bbbred5apps.messages.{UserConnected, UserDisconnected}
import org.bigbluebutton.red5.pubsub.MessagePublisher

import scala.collection.mutable.HashMap
import scala.concurrent.Await

object ConnectionManager {
  def props(system: ActorSystem, red5Publish: MessagePublisher): Props =
    Props(classOf[ConnectionManager], system, red5Publish)

  case class HasScreenShareSession(meetingId: String)
  case class HasScreenShareSessionReply(meetingId: String, sharing: Boolean, streamId:Option[String])
  case class MeetingHasEnded(meetingId: String)
}

class ConnectionManager(val aSystem: ActorSystem, val red5Publish: MessagePublisher)
  extends Actor with ActorLogging {
  log.info("Creating a new ConnectionManager")

//  private val screenshares = new HashMap[String, ActiveScreenshare]

  def receive = {
    case msg: UserDisconnected            => handleUserDisconnected(msg)
    case msg: UserConnected               => handleUserConnected(msg)

    case msg: Any => log.warning("Unknown message " + msg)
  }

  def handleUserConnected(msg: UserConnected): Unit = {
    log.info("\n\nhandleUserConnected\n\n")
  }



  private def handleUserDisconnected(msg: UserDisconnected) {
    red5Publish.userLeft(msg.meetingId, msg.userId, msg.sessionId)
//    if (log.isDebugEnabled) {
//      log.debug("Received UserDisconnected message for meeting=[" + msg.meetingId + "]")
//    }
//    screenshares.get(msg.meetingId) foreach { screenshare =>
//      screenshare.actorRef ! msg
//    }
  }
}

