package org.bigbluebutton.core2x

import akka.actor.{Actor, ActorLogging, Props}
import org.bigbluebutton.core2x.bus.handlers._
import org.bigbluebutton.core2x.bus.handlers.presentation._
import org.bigbluebutton.core2x.bus.{IncomingEventBus2x, IncomingJsonMessageBus, ReceivedJsonMessage}
import org.bigbluebutton.core2x.handlers._
import org.bigbluebutton.core2x.handlers.presentation._
import org.bigbluebutton.core2x.handlers.whiteboard.SendWhiteboardAnnotationRequestEventMessageHandler

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
    with ClearPresentationEventMessageHandler
    with GetPageInfoEventMessageHandler
    with GetPresentationInfoEventMessageHandler
    with GoToPageEventMessageHandler
    with PresentationConversionCompletedEventMessageHandler
    with PresentationConversionUpdateEventMessageHandler
    with PresentationPageCountErrorEventMessageHandler
    with PresentationPageGeneratedEventMessageHandler
    with PreuploadedPresentationsEventMessageHandler
    with RemovePresentationEventMessageHandler

    // whiteboard.*
    with SendWhiteboardAnnotationRequestEventMessageHandler

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
