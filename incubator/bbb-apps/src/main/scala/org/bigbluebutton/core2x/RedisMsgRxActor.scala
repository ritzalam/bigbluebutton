package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core2x.json.handlers._
import org.bigbluebutton.core2x.json.handlers.presentation._
import org.bigbluebutton.core2x.json.handlers.whiteboard.{ SendWhiteboardAnnotationRequestEventJsonMsgRx }
import org.bigbluebutton.core2x.json.{ IncomingEventBus2x, IncomingJsonMessageBus, ReceivedJsonMessage }
import org.bigbluebutton.core2x.handlers.presentation._

object RedisMsgRxActor {
  def props(eventBus: IncomingEventBus2x, incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMsgRxActor], eventBus, incomingJsonMessageBus)
}

class RedisMsgRxActor(
  val eventBus: IncomingEventBus2x,
  val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging
    with UnhandledJsonMsgRx

    // presentation.*
    with ClearPresentationEventJsonMsgRx
    with GetPageInfoEventJsonMsgRx
    with GetPresentationInfoEventJsonMsgRx
    with GoToPageEventJsonMsgRx
    with PresentationConversionCompletedEventJsonMsgRx
    with PresentationConversionUpdateEventJsonMsgRx
    with PresentationPageCountErrorEventJsonMsgRx
    with PresentationPageGeneratedEventJsonMsgRx
    with PreuploadedPresentationsEventJsonMsgRx
    with RemovePresentationEventJsonMsgRx

    // whiteboard.*
    with SendWhiteboardAnnotationRequestEventJsonMsgRx

    with CreateMeetingRequestJsonMsgRx
    with KeepAliveJsonMsgRx
    with PubSubPingJsonMsgRx
    with JsonMsgRx
    with RegisterUserRequestJsonMsgRx
    with UserJoinMeetingMessageJsonHandler
    with ValidateAuthTokenRequestMessageJsonHandler {

  def receive = {
    case msg: ReceivedJsonMessage => handleReceivedJsonMsg(msg)
    case _ => // do nothing
  }

}
