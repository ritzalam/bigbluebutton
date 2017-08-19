package org.bigbluebutton.core2

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.common2.msgs._
import org.bigbluebutton.common2.util.JsonUtil
import org.bigbluebutton.core.MessageSender

object FromAkkaAppsMsgSenderActor {
  def props(msgSender: MessageSender): Props = Props(classOf[FromAkkaAppsMsgSenderActor], msgSender)
}

class FromAkkaAppsMsgSenderActor(msgSender: MessageSender)
    extends Actor with ActorLogging with SystemConfiguration {

  def receive = {
    case msg: BbbCommonEnvCoreMsg => handleBbbCommonEnvCoreMsg(msg)
    case _                        => log.warning("Cannot handle message ")
  }

  def handleBbbCommonEnvCoreMsg(msg: BbbCommonEnvCoreMsg): Unit = {
    val json = JsonUtil.toJson(msg)

    msg.envelope.name match {
      case SyncGetPresentationInfoRespMsg.NAME => msgSender.send(toHTML5RedisChannel, json)
      case SyncGetMeetingInfoRespMsg.NAME      => msgSender.send(toHTML5RedisChannel, json)
      case SyncGetUsersMeetingRespMsg.NAME     => msgSender.send(toHTML5RedisChannel, json)
      case SyncGetWhiteboardAccessRespMsg.NAME => msgSender.send(toHTML5RedisChannel, json)

      // Sent to FreeSWITCH
      case ScreenshareStartRtmpBroadcastVoiceConfMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case ScreenshareStopRtmpBroadcastVoiceConfMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case EjectAllFromVoiceConfMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case GetUsersInVoiceConfSysMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case EjectUserFromVoiceConfSysMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case MuteUserInVoiceConfSysMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case StartRecordingVoiceConfSysMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case StopRecordingVoiceConfSysMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)
      case TransferUserToVoiceConfSysMsg.NAME =>
        msgSender.send(toVoiceConfRedisChannel, json)

      //==================================================================
      // Send chat, presentation, and whiteboard in different channels so as not to
      // flood other applications (e.g. bbb-web) with unnecessary messages

      // Whiteboard
      case SendWhiteboardAnnotationEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsWbRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case SendCursorPositionEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsWbRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case ClearWhiteboardEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsWbRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case UndoWhiteboardEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsWbRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))

      // Chat
      case SendPublicMessageEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsChatRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case ClearPublicChatHistoryEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsChatRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))

      // Presentation
      case PresentationConversionCompletedEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case SetCurrentPageEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case ResizeAndMovePageEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case RemovePresentationEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case SetCurrentPresentationEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))

      // Breakout
      case UpdateBreakoutUsersEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case BreakoutRoomsListEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case BreakoutRoomJoinURLEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case BreakoutRoomsTimeRemainingUpdateEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case BreakoutRoomStartedEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      case MeetingTimeRemainingUpdateEvtMsg.NAME =>
        msgSender.send(fromAkkaAppsPresRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
      //==================================================================

      case _ =>
        msgSender.send(fromAkkaAppsRedisChannel, json)
        msgSender.sendToDS("foo-bar", JsonUtil.toJson(msg.core))
    }
  }
}
