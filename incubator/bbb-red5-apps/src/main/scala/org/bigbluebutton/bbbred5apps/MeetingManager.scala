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
import org.bigbluebutton.bbbred5apps.messages._
import org.bigbluebutton.red5.pubsub.MessagePublisher

import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.concurrent.Await

object MeetingManager {
  def props(system: ActorSystem, red5Publisher: MessagePublisher): Props =
    Props(classOf[MeetingManager], system, red5Publisher)
}

class MeetingManager(val aSystem: ActorSystem, val red5Publisher: MessagePublisher)
  extends Actor with ActorLogging {
  log.warning("Creating a new MeetingManager warn")

  private val meetings = new mutable.HashMap[String, ActiveMeetingActor]

  def receive = {
    case msg: MeetingEnded             => handleMeetingHasEnded(msg)
    case msg: MeetingCreated => handleMeetingCreated(msg)

//    case msg: UserDisconnected            => handleUserDisconnected(msg)
//    case msg: UserConnected               => handleUserConnected(msg)
//    case msg: PendingAuthJsonMessage      => forwardToClientsManager(msg)

    case msg: Any => log.warning("Unknown message " + msg)
  }

//  private def handleUserConnected(msg: UserConnected): Unit = {
//    log.warning(s"\n\nhandleUserConnected $msg\n\n")
//
//    // TODO add logic for connection vs reconnection
//    red5Publisher.initLockSettings(msg.meetingId, msg.lockSettings)
//
//    red5Publisher.initAudioSettings(msg.meetingId, msg.userId, msg.muted)
//  }
//
//
//
//  private def handleUserDisconnected(msg: UserDisconnected) {
//    log.warning(s"\n\nhandleUserDisconnected $msg\n\n")
//
//    // TODO add logic for disconnection vs reconnection?
//    red5Publisher.userLeft(msg.meetingId, msg.userId, msg.sessionId)
//    //    if (log.isDebugEnabled) {
//    //      log.debug("Received UserDisconnected message for meeting=[" + msg.meetingId + "]")
//    //    }
//  }
//
//  private def forwardToClientsManager(msg: PendingAuthJsonMessage): Unit = {
//  }


  private def handleMeetingHasEnded(msg: MeetingEnded) {
    log.info("Removing meeting [" + msg.meetingId + "]")

    meetings.get(msg.meetingId) foreach { meeting =>
      meeting.actorRef forward msg
    }

    meetings -= msg.meetingId
  }

  private def handleMeetingCreated(msg: MeetingCreated) {
    log.info("Creating meeting [" + msg.meetingId + "]")

    meetings.get(msg.meetingId) match {
      case None => {
        if (log.isDebugEnabled) {
          log.debug("Creating meeting=[" + msg.meetingId + "]")
        }
        val activeMeetingActor = ActiveMeetingActor()
        meetings += msg.meetingId -> activeMeetingActor

      }
      case Some(screenshare) => {
        if (log.isDebugEnabled) {
          log.debug("Meeting already exists. meetingId=[" + msg.meetingId + "]")
        }
      }
    }
  }


}
