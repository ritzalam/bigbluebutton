package org.bigbluebutton.core.apps.reguser.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg.RegisterUserInMessage
import org.bigbluebutton.core.api.OutGoingMsg.UserRegisteredEvent2x
import org.bigbluebutton.core.apps.reguser.RegisteredUsersModel
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, PinNumberGenerator }

trait RegisterUserCommandMsgHdlr {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handle(msg: RegisterUserInMessage): Unit = {
    val pinNumber = PinNumberGenerator.generatePin(state.props.voiceConf, state.status.get)
    val regUser = RegisteredUsersModel.create(msg.userId, msg.extUserId, msg.name, msg.roles,
      msg.authToken, msg.avatar, msg.logoutUrl, msg.welcome, msg.dialNumbers, pinNumber, msg.config, msg.extData)

    state.registeredUsersModel.add(regUser)
    outGW.send(new UserRegisteredEvent2x(state.props.id, state.props.recordingProp.recorded, regUser))
  }
}
