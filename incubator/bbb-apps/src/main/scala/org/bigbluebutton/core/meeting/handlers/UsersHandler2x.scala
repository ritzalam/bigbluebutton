package org.bigbluebutton.core.meeting.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg._
import org.bigbluebutton.core.api.OutGoingMsg._
import org.bigbluebutton.core.domain.{ RegisteredUser, User }
import org.bigbluebutton.core.meeting.models.{ MeetingStateModel, PinNumberGenerator }
import org.bigbluebutton.core.reguser.RegisteredUsersModel
import org.bigbluebutton.core.user.{ UserInMsgHdlr, UsersModel }

trait UsersHandler2x {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  private var userHandlers = new collection.immutable.HashMap[String, UserInMsgHdlr]

  def handleRegisterUser2xA(msg: RegisterUserInMessage): Unit = {
    val pinNumber = PinNumberGenerator.generatePin(state.props.voiceConf, state.status.get)
    val regUser = RegisteredUsersModel.create(msg.userId, msg.extUserId, msg.name, msg.roles,
      msg.authToken, msg.avatar, msg.logoutUrl, msg.welcome, msg.dialNumbers, pinNumber, msg.config, msg.extData)

    state.registeredUsersModel.add(regUser)
    outGW.send(new UserRegisteredEvent2x(state.props.id, state.props.recordingProp.recorded, regUser))
  }

  def handleValidateAuthToken2xA(msg: ValidateAuthTokenInMessage): Unit = {
    def handle(regUser: RegisteredUser): Unit = {
      val userHandler = new UserInMsgHdlr(regUser, outGW)
      userHandlers += msg.senderId.value -> userHandler
      //userHandler.handleValidateAuthToken2x(msg, state)
    }

    for {
      regUser <- RegisteredUsersModel.findWithToken(msg.token, state.registeredUsersModel.toVector)
    } yield handle(regUser)

  }

  def handleEjectUserFromMeetingA(msg: EjectUserFromMeetingInMsg) {
    def removeAndEject(user: User): Unit = {
      // remove user from list of users
      state.usersModel.remove(user.id)
      // remove user from registered users to prevent re-joining
      state.registeredUsersModel.remove(msg.senderId)

      // Send message to user that he has been ejected.
      outGW.send(new UserEjectedFromMeetingEventOutMsg(state.props.id,
        state.props.recordingProp.recorded,
        msg.senderId, msg.ejectedBy))
      // Tell system to disconnect user.
      outGW.send(new DisconnectUser2x(msg.meetingId, msg.senderId))
      // Tell all others that user has left the meeting.
      outGW.send(new UserLeftEventOutMsg(state.props.id,
        state.props.recordingProp.recorded,
        msg.senderId))
    }

    for {
      user <- UsersModel.findWithId(msg.senderId, state.usersModel.toVector)
    } yield removeAndEject(user)
  }

}
