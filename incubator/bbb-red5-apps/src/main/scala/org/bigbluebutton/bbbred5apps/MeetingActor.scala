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
import akka.actor.{Actor, ActorContext, ActorLogging, ActorSystem, Props}
import akka.util.Timeout
import org.bigbluebutton.bbbred5apps.messages.{UserConnected, UserDisconnected}
import org.bigbluebutton.red5.pubsub.MessagePublisher

import scala.collection.mutable
import scala.collection.mutable.HashMap
import scala.concurrent.Await

object MeetingActor {
  def props(): Props = Props(classOf[MeetingActor])
}

class MeetingActor() extends Actor with ActorLogging {

  log.warning("Creating a new MeetingActor warn")

  def receive = {

    case msg: Any => log.warning("Unknown message " + msg)
  }


}


object ActiveMeetingActor {
  def apply()(implicit context: ActorContext) =
    new ActiveMeetingActor()(context)
}

class ActiveMeetingActor()(implicit val context: ActorContext) {
  val actorRef = context.actorOf(MeetingActor.props())
}
