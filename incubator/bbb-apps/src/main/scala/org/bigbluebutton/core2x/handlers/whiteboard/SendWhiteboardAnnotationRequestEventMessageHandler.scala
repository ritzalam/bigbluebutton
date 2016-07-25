package org.bigbluebutton.core2x.handlers.whiteboard

import org.bigbluebutton.core2x.RedisMessageHandlerActor
import org.bigbluebutton.core2x.api.IncomingMsg.SendWhiteboardAnnotationRequest
import org.bigbluebutton.core2x.bus.handlers.UnhandledReceivedJsonMessageHandler
import org.bigbluebutton.core2x.bus.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core2x.domain.{ AnnotationVO, IntMeetingId, IntUserId }
import org.bigbluebutton.messages.vo.AnnotationBody
import org.bigbluebutton.messages.whiteboard.SendWhiteboardAnnotationRequestEventMessage

trait SendWhiteboardAnnotationRequestEventMessageHandler extends UnhandledReceivedJsonMessageHandler with SendWhiteboardAnnotationRequestEventMessageHandlerHelper {
  this: RedisMessageHandlerActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMessage(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, senderId: IntUserId, annotation: AnnotationVO): Unit = {
      log.debug(s"Publishing ${msg.name} [$senderId]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          SendWhiteboardAnnotationRequest(meetingId, senderId, annotation)))
    }

    if (msg.name == SendWhiteboardAnnotationRequestEventMessage.NAME) {
      log.debug(s"Received JSON message [ ${msg.name}]")
      val m = SendWhiteboardAnnotationRequestEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        senderId <- Option(m.header.senderId)
        annotation <- convertAnnotationBody(m.body.annotation)
        requesterId <- Option(m.body.requesterId)
      } yield publish(IntMeetingId(meetingId), IntUserId(senderId), annotation)
    } else {
      super.handleReceivedJsonMessage(msg)
    }

  }
}

trait SendWhiteboardAnnotationRequestEventMessageHandlerHelper {
  def convertAnnotationBody(body: AnnotationBody): Option[AnnotationVO] = {
    for {
      id <- Option(body.id)
      status <- Option(body.status)
      shapeType <- Option(body.shapeType)
      shape = extractInnerShape(body.shape)
      wbId <- Option(body.wbId)
    } yield AnnotationVO(id, status, shapeType, shape, wbId)
  }

  def extractInnerShape(obj: java.util.Map[String, Object]): scala.collection.immutable.Map[String, Object] = {

    import scala.collection.JavaConversions.mapAsScalaMap
    val a = mapAsScalaMap(obj)

    a.toMap
  }

}
