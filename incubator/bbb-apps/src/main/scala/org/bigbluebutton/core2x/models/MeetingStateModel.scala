package org.bigbluebutton.core2x.models

import org.bigbluebutton.core2x.apps.presentation.PresentationModel
import org.bigbluebutton.core2x.domain.{ Abilities2x, MeetingProperties2x }

case class MeetingAbilities(removed: Set[Abilities2x], added: Set[Abilities2x])

class MeetingPermissions {
  private var permissions: MeetingAbilities = new MeetingAbilities(Set.empty, Set.empty)

  def get: MeetingAbilities = permissions
  def save(abilities: MeetingAbilities): Unit = permissions = abilities
}

class MeetingStateModel(
  val props: MeetingProperties2x,
  val abilities: MeetingPermissions,
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
