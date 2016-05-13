package org.bigbluebutton.core.models

import com.softwaremill.quicklens._
import org.bigbluebutton.core.domain.{ Abilities2x, MeetingExtensionProp, MeetingProperties2x }

case class MeetingAbilities(removed: Set[Abilities2x], added: Set[Abilities2x])

class MeetingPermissions {
  private var permissions: MeetingAbilities = new MeetingAbilities(Set.empty, Set.empty)

  def get: MeetingAbilities = permissions
  def save(abilities: MeetingAbilities): Unit = permissions = abilities
}

class MeetingState(
    val props: MeetingProperties2x,
    val abilities: MeetingPermissions,
    val registeredUsers: RegisteredUsers2x,
    val users: Users3x,
    val chats: ChatModel,
    val layouts: LayoutModel,
    val polls: PollModel,
    val whiteboards: WhiteboardModel,
    val presentations: PresentationModel,
    val breakoutRooms: BreakoutRoomModel,
    val captions: CaptionModel) {
  val status: MeetingStatus = new MeetingStatus
  val extension: ExtensionStatus = new ExtensionStatus
}

class MeetingStatus {
  private var status: Meeting3x = new Meeting3x()

  def get: Meeting3x = status

  def mute(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.muted).setTo(true)
  }

  def unMute(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.muted).setTo(false)
  }

  def recordingStarted(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.isRecording).setTo(true)
  }

  def recordingStopped(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.isRecording).setTo(false)
  }

}

class ExtensionStatus {
  private var status: MeetingExtensionProp = new MeetingExtensionProp()

}