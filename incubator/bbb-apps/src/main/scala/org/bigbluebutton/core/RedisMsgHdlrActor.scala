package org.bigbluebutton.core

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core.json.handlers._
import org.bigbluebutton.core.json.handlers.presentation._
import org.bigbluebutton.core.json.handlers.whiteboard.SendWhiteboardAnnotationRequestEventJsonMsgHdlr
import org.bigbluebutton.core.json.{ IncomingEventBus2x, IncomingJsonMessageBus, ReceivedJsonMessage }

object RedisMsgHdlrActor {
  def props(eventBus: IncomingEventBus2x, incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMsgHdlrActor], eventBus, incomingJsonMessageBus)
}

class RedisMsgHdlrActor(
  val eventBus: IncomingEventBus2x,
  val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging
    with UnhandledJsonMsgHdlr

    // presentation.*
    with ClearPresentationEventJsonMsgHdlr
    with GetPageInfoEventJsonMsgHdlr
    with GetPresentationInfoEventJsonMsgHdlr
    with GoToPageEventJsonMsgHdlr
    with PresentationConversionCompletedEventJsonMsgHdlr
    with PresentationConversionUpdateEventJsonMsgHdlr
    with PresentationPageCountErrorEventJsonMsgHdlr
    with PresentationPageGeneratedEventJsonMsgHdlr
    with PreuploadedPresentationsEventJsonMsgHdlr
    with RemovePresentationEventJsonMsgHdlr

    // whiteboard.*
    with SendWhiteboardAnnotationRequestEventJsonMsgHdlr

    with CreateMeetingRequestJsonMsgHdlr
    with KeepAliveJsonMsgHdlr
    with PubSubPingJsonMsgHdlr
    with JsonMsgHdlr
    with RegisterUserRequestJsonMsgHdlr
    with UserJoinMeetingJsonMsgHdlr
    with ValidateAuthTokenRequestJsonMsgHdlr {

  def receive = {
    case msg: ReceivedJsonMessage => handleReceivedJsonMsg(msg)
    case _ => // do nothing
  }

}
