package org.bigbluebutton.core.apps.reguser

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.{ IncomingEventBus2x, OutMessageGateway }
import org.bigbluebutton.core.api.IncomingMsg.{ AssignUserSessionTokenInMsg2x, ValidateAuthTokenInMsg2x }
import org.bigbluebutton.core.apps.reguser.handlers.{ AssignUserSessionTokenInMsgHdlr, ValidateAuthTokenInMsgHdlr }

object RegisteredUserActor {
  def props(meetingId: String,
    userId: String,
    sessionTokens: SessionTokens,
    inGW: IncomingEventBus2x,
    outGW: OutMessageGateway): Props =
    Props(classOf[RegisteredUserActor], meetingId, userId, sessionTokens, inGW, outGW)
}

class RegisteredUserActor(val meetingId: String,
  val userId: String,
  val sessionTokens: SessionTokens,
  val inGW: IncomingEventBus2x,
  val outGW: OutMessageGateway) extends Actor with ActorLogging
    with ValidateAuthTokenInMsgHdlr
    with AssignUserSessionTokenInMsgHdlr {

  private val regUserIdChannel = userId + "@" + meetingId

  override def preStart(): Unit = {
    inGW.subscribe(self, regUserIdChannel)
    super.preStart()
  }

  override def postStop(): Unit = {
    inGW.subscribe(self, regUserIdChannel)

    sessionTokens.tokens foreach { st =>
      inGW.unsubscribe(self, st.value)
    }
    super.postStop()
  }

  def receive = {
    case m: ValidateAuthTokenInMsg2x => handle(m)
    case m: AssignUserSessionTokenInMsg2x => handle(m)
    case _ => // do nothing
  }
}
