package org.bigbluebutton.core.apps.reguser

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.{ IncomingEventBus2x, OutMessageGateway }
import org.bigbluebutton.core.api.IncomingMsg.ValidateAuthTokenInMsg2x
import org.bigbluebutton.core.apps.reguser.handlers.RegUserValidateAuthTokenInMsgHdlr
import org.bigbluebutton.core.domain.{ IntMeetingId, IntUserId }

object RegisteredUserActor {
  def props(meetingId: IntMeetingId,
    userId: IntUserId,
    inGW: IncomingEventBus2x,
    outGW: OutMessageGateway): Props = Props(classOf[RegisteredUserActor], meetingId, userId, inGW, outGW)
}

class RegisteredUserActor(val meetingId: IntMeetingId,
  val userId: IntUserId,
  val inGW: IncomingEventBus2x,
  val outGW: OutMessageGateway)
    extends Actor with ActorLogging
    with RegUserValidateAuthTokenInMsgHdlr {

  private[reguser] val sessionTokens: Set[String] = Set.empty

  private val userIdChannel = meetingId.value + "/" + userId.value

  override def preStart(): Unit = {
    inGW.subscribe(self, userIdChannel)
    super.preStart()
  }

  override def postStop(): Unit = {
    inGW.subscribe(self, userIdChannel)

    sessionTokens foreach { st =>
      inGW.unsubscribe(self, st)
    }
    super.postStop()
  }

  def receive = {
    case m: ValidateAuthTokenInMsg2x => handle(m)
    case _ => // do nothing
  }
}
