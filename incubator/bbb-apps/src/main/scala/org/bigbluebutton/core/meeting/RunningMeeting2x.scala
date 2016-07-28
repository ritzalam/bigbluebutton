package org.bigbluebutton.core.meeting

import akka.actor.ActorContext
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.apps.presentation.PresentationModel
import org.bigbluebutton.core.domain.{ Clients, MeetingExtensionStatus, MeetingProperties2x }
import org.bigbluebutton.core.json.IncomingEventBus2x
import org.bigbluebutton.core.meeting.models._

object RunningMeeting2x {
  def apply(mProps: MeetingProperties2x, outGW: OutMessageGateway,
    eventBus: IncomingEventBus2x)(implicit context: ActorContext) =
    new RunningMeeting2x(mProps, outGW, eventBus)(context)
}

class RunningMeeting2x(val mProps: MeetingProperties2x, val outGW: OutMessageGateway,
    val eventBus: IncomingEventBus2x)(implicit val context: ActorContext) {

  val abilities: MeetingPermissions = new MeetingPermissions
  val clients: Clients = new Clients
  val registeredUsers = new RegisteredUsersModel
  val users = new UsersModel
  val chats = new ChatModel
  val layouts = new LayoutModel
  val polls = new PollModel
  val whiteboards = new WhiteboardModel
  val presentations = new PresentationModel
  val breakoutRooms = new BreakoutRoomModel
  val captions = new CaptionModel
  val extension: MeetingExtensionStatus = new MeetingExtensionStatus

  val state: MeetingStateModel = new MeetingStateModel(mProps,
    abilities,
    clients,
    registeredUsers,
    users,
    chats,
    layouts,
    polls,
    whiteboards,
    presentations,
    breakoutRooms,
    captions,
    new MeetingStatus)

  val actorRef = context.actorOf(MeetingActor2x.props(mProps, eventBus, outGW, state), mProps.id.value)

}
