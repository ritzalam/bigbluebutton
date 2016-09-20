package org.bigbluebutton.core.meeting.models

import org.bigbluebutton.core.apps.breakout.BreakoutRoomModel
import org.bigbluebutton.core.apps.presentation.PresentationModel
import org.bigbluebutton.core.apps.reguser.RegisteredUsersModel
import org.bigbluebutton.core.apps.user.UsersModel
import org.bigbluebutton.core.apps.whiteboard.WhiteboardModel
import org.bigbluebutton.core.client.Clients
import org.bigbluebutton.core.domain.{ Ability, MeetingProperties2x }

case class MeetingAbilities(removed: Set[Ability], added: Set[Ability])

class MeetingPermissions {
  private var permissions: MeetingAbilities = new MeetingAbilities(Set.empty, Set.empty)

  def get: MeetingAbilities = permissions
  def save(abilities: MeetingAbilities): Unit = permissions = abilities
}

class MeetingStateModel(
  val props: MeetingProperties2x,
  val abilities: MeetingPermissions,
  val clients: Clients,
  val registeredUsersModel: RegisteredUsersModel,
  val usersModel: UsersModel,
  val chatsModel: ChatModel,
  val layoutsModel: LayoutModel,
  val pollsModel: PollModel,
  val whiteboardsModel: WhiteboardModel,
  val presentationsModel: PresentationModel,
  val breakoutRoomsModel: BreakoutRoomModel,
  val captionsModel: CaptionModel,
  val status: MeetingStatus)

class MeetingStatus {
  private var status: Meeting3x = new Meeting3x()

  def get: Meeting3x = status

  def save(meeting: Meeting3x): Unit = {
    status = meeting
  }
}
