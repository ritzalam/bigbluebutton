package org.bigbluebutton.core2x

import akka.actor.{ Actor, ActorLogging, Props }
import org.bigbluebutton.core2x.json.rx._
import org.bigbluebutton.core2x.json.rx.presentation._
import org.bigbluebutton.core2x.json.rx.whiteboard.{ SendWhiteboardAnnotationRequestEventJsonMessageHandler }
import org.bigbluebutton.core2x.json.{ IncomingEventBus2x, IncomingJsonMessageBus, ReceivedJsonMessage }
import org.bigbluebutton.core2x.handlers.presentation._

object RedisMessageHandlerActor {
  def props(eventBus: IncomingEventBus2x, incomingJsonMessageBus: IncomingJsonMessageBus): Props =
    Props(classOf[RedisMessageHandlerActor], eventBus, incomingJsonMessageBus)
}

class RedisMessageHandlerActor(
  val eventBus: IncomingEventBus2x,
  val incomingJsonMessageBus: IncomingJsonMessageBus)
    extends Actor with ActorLogging
    with UnhandledReceivedJsonMessageHandler

    // presentation.*
    with ClearPresentationEventJsonMessageHandler
    with GetPageInfoEventJsonMessageHandler
    with GetPresentationInfoEventJsonMessageHandler
    with GoToPageEventJsonMessageHandler
    with PresentationConversionCompletedEventJsonMessageHandler
    with PresentationConversionUpdateEventJsonMessageHandler
    with PresentationPageCountErrorEventJsonMessageHandler
    with PresentationPageGeneratedEventJsonMessageHandler
    with PreuploadedPresentationsEventJsonMessageHandler
    with RemovePresentationEventJsonMessageHandler

    // whiteboard.*
    with SendWhiteboardAnnotationRequestEventJsonMessageHandler

    with CreateMeetingRequestMessageJsonHandler
    with KeepAliveMessageJsonHandler
    with PubSubPingMessageJsonHandler
    with ReceivedJsonMessageHandler
    with RegisterUserRequestMessageJsonHandler
    with UserJoinMeetingMessageJsonHandler
    with ValidateAuthTokenRequestMessageJsonHandler {

  def receive = {
    case msg: ReceivedJsonMessage => handleReceivedJsonMessage(msg)
    case _ => // do nothing
  }

}
