package org.bigbluebutton.core.apps.reguser.handlers

import org.bigbluebutton.core.api.IncomingMsg.RegisterUserInMessage
import org.bigbluebutton.core.api.OutGoingMsg.UserRegisteredEvent2x
import org.bigbluebutton.core.apps.reguser.{ RegisteredUserActor, RegisteredUsersModel, SessionTokens }
import org.bigbluebutton.core.meeting.MeetingActorMsg
import org.bigbluebutton.core.meeting.models.PinNumberGenerator

trait RegisterUserCommandMsgHdlr {
  this: MeetingActorMsg =>

  def handle(msg: RegisterUserInMessage): Unit = {

    val actorRef = context.actorOf(
      RegisteredUserActor.props(msg.meetingId.value, msg.userId.value,
        new SessionTokens, bus, outGW),
      msg.userId.value + "@" + msg.meetingId.value)

    val pinNumber = PinNumberGenerator.generatePin(state.props.voiceConf, state.status.get)
    val regUser = RegisteredUsersModel.create(msg.userId, msg.extUserId, msg.name, msg.roles,
      msg.authToken, msg.avatar, msg.logoutUrl, msg.welcome, msg.dialNumbers, pinNumber, msg.config, msg.extData)

    state.registeredUsersModel.add(regUser)
    outGW.send(new UserRegisteredEvent2x(state.props.id, state.props.recordingProp.recorded, regUser))
  }
}
