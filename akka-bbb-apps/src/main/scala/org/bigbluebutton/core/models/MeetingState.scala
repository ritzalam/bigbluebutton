package org.bigbluebutton.core.models

import com.softwaremill.quicklens._
import org.bigbluebutton.core.domain.{ MeetingExtensionProp, MeetingProperties2x }

class MeetingState(val props: MeetingProperties2x) {
  val registeredUsers = new RegisteredUsers2x
  val users = new Users3x
  val chats = new ChatModel
  val layouts = new LayoutModel
  val polls = new PollModel
  val whiteboards = new WhiteboardModel
  val presentations = new PresentationModel
  val breakoutRooms = new BreakoutRoomModel
  val captions = new CaptionModel

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