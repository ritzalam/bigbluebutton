package org.bigbluebutton.core2x.handlers.presentation

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.PreuploadedPresentationsEventInMessage
import org.bigbluebutton.core2x.apps.presentation.PreuploadedPresentation
import org.bigbluebutton.core2x.apps.presentation.domain.PresentationId
import org.bigbluebutton.core2x.bus.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.IntMeetingId
import org.bigbluebutton.messages.presentation.PreuploadedPresentationsEventMessage
import org.bigbluebutton.messages.vo.PreuploadedPresentationBody

trait PreuploadedPresentationsEventJsonMessageHandler extends UnhandledReceivedJsonMessageHandler
    with PreuploadedPresentationsEventJsonMessageHandlerHelper {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, presentations: Set[PreuploadedPresentation]): Unit = {
      log.debug(s"Publishing ${msg.name} [ ${presentations.size} ]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PreuploadedPresentationsEventInMessage(meetingId, presentations)))
    }

    if (msg.name == PreuploadedPresentationsEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = PreuploadedPresentationsEventMessage.fromJson(msg.data)
      for {
        m2 <- convertMessage(m)
      } yield publish(m2.meetingId, m2.presentations)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}

trait PreuploadedPresentationsEventJsonMessageHandlerHelper {

  def convertMessage(msg: PreuploadedPresentationsEventMessage): Option[PreuploadedPresentationsEventInMessage] = {
    for {
      header <- Option(msg.header)
      meetingId <- Option(header.meetingId)
      senderId <- Option(header.senderId) //TODO where is it to be used?
      body <- Option(msg.body)
      presentations = extractPreuploadedPresentations(body.presentations)
    } yield new PreuploadedPresentationsEventInMessage(IntMeetingId(meetingId), presentations)
  }

  def extractPreuploadedPresentations(list: java.util.List[PreuploadedPresentationBody]): Set[PreuploadedPresentation] = {
    import scala.collection.convert.wrapAsScala._
    // convert the list to a set
    val r = asScalaBuffer(list).toSet

    var res = Set[Option[PreuploadedPresentation]]()

    r.map(b => {
      res = res + convertAPreuploadedPresentation(b) //TODO rework
    })

    res.flatten
  }

  def convertAPreuploadedPresentation(p: PreuploadedPresentationBody): Option[PreuploadedPresentation] = {
    for {
      presentationId <- Option(p.presentationId)
      presentationName <- Option(p.presentationName)
      defaultPres <- Option(p.defaultPres)
    } yield new PreuploadedPresentation(PresentationId(presentationId), presentationName,
      defaultPres)
  }

}

