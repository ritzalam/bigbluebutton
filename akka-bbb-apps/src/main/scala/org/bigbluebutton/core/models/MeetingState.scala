package org.bigbluebutton.core.models

class MeetingState {
  val registeredUsers: RegisteredUsers2x = new RegisteredUsers2x
  val users = new Users3x
  val chats = new ChatModel
  val layouts = new LayoutModel
  val polls = new PollModel
  val whiteboards = new WhiteboardModel
  val presentations = new PresentationModel
  val breakoutRooms = new BreakoutRoomModel
  val captions = new CaptionModel
}
