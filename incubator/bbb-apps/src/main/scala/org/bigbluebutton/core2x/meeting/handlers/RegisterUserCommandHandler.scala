package org.bigbluebutton.core2x.meeting.handlers

import org.bigbluebutton.core2x.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.RegisterUserInMessage
import org.bigbluebutton.core2x.api.OutGoingMsg.UserRegisteredEvent2x
import org.bigbluebutton.core2x.meeting.models.{ MeetingStateModel, PinNumberGenerator, RegisteredUsersModel }

trait RegisterUserCommandHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handleRegisterUser2x(msg: RegisterUserInMessage): Unit = {
    val pinNumber = PinNumberGenerator.generatePin(state.props.voiceConf, state.status.get)
    val regUser = RegisteredUsersModel.create(
      msg.userId,
      msg.extUserId,
      msg.name,
      msg.roles,
      msg.authToken,
      msg.avatar,
      msg.logoutUrl,
      msg.welcome,
      msg.dialNumbers,
      pinNumber,
      msg.config,
      msg.extData)

    state.registeredUsersModel.add(regUser)
    outGW.send(new UserRegisteredEvent2x(state.props.id, state.props.recordingProp.recorded, regUser))
  }
}
