package org.bigbluebutton.core.api.json.handlers.presentation

import org.bigbluebutton.core.RedisMsgHdlrActor
import org.bigbluebutton.core.api.IncomingMsg.PresentationConversionCompletedEventInMessage
import org.bigbluebutton.core.apps.presentation.{ Page, Presentation }
import org.bigbluebutton.core.apps.presentation.domain._
import org.bigbluebutton.core.api.json.{ BigBlueButtonInMessage, IncomingEventBus2x, ReceivedJsonMessage }
import org.bigbluebutton.core.api.json.handlers.UnhandledJsonMsgHdlr
import org.bigbluebutton.core.domain.IntMeetingId
import org.bigbluebutton.messages.presentation.PresentationConversionCompletedEventMessage
import org.bigbluebutton.messages.vo.{ PageBody, PresentationBody }

trait PresentationConversionCompletedEventJsonMsgHdlr
    extends UnhandledJsonMsgHdlr
    with PresentationConversionCompletedEventJsonMessageHandlerHelper {

  this: RedisMsgHdlrActor =>

  val eventBus: IncomingEventBus2x

  override def handleReceivedJsonMsg(msg: ReceivedJsonMessage): Unit = {
    def publish(meetingId: IntMeetingId, messageKey: String, code: String, presentation: Presentation): Unit = {
      log.debug(s"Publishing ${msg.name} [ ${presentation.id} $code]")
      eventBus.publish(
        BigBlueButtonInMessage(meetingId.value,
          new PresentationConversionCompletedEventInMessage(meetingId, messageKey, code,
            presentation)))
    }

    if (msg.name == PresentationConversionCompletedEventMessage.NAME) {
      log.debug("Received JSON message [" + msg.name + "]")
      val m = PresentationConversionCompletedEventMessage.fromJson(msg.data)
      for {
        meetingId <- Option(m.header.meetingId)
        messageKey <- Option(m.body.messageKey)
        code <- Option(m.body.code)
        presentation <- convertPresentation(m.body.presentation)
        presentation <- convertPresentation(m.body.presentation)
      } yield publish(IntMeetingId(meetingId), messageKey, code, presentation)
    } else {
      super.handleReceivedJsonMsg(msg)
    }

  }
}

trait PresentationConversionCompletedEventJsonMessageHandlerHelper {
  def convertPresentation(body: PresentationBody): Option[Presentation] = {
    for {
      current <- Option(body.current)
      defaultPres <- Option(body.defaultPres)
      id <- Option(body.id)
      name <- Option(body.name)
      pages = extractPages(body.pages)
    } yield new Presentation(PresentationId(id), name, current, pages, defaultPres)
  }

  def extractPages(list: java.util.List[PageBody]): Set[Page] = {
    var pages: Set[Page] = null

    import scala.collection.convert.wrapAsScala._
    // convert the list to a set
    val r = asScalaBuffer(list).toSet

    var res = Set[Option[Page]]()

    r.map(b => {
      res = res + convertAPage(b) //TODO rework
    })

    res.flatten
  }

  def convertAPage(p: PageBody): Option[Page] = {
    for {
      id <- Option(p.id) //TODO make this a type like PresentationId
      current <- Option(p.current)
      num <- Option(p.num)
      svgUrl <- Option(p.svgUrl)
      swfUrl <- Option(p.swfUrl)
      textUrl <- Option(p.textUrl)
      thumbUrl <- Option(p.thumbUrl)
    } yield new Page(id, num, ThumbUrl(thumbUrl), SwfUrl(swfUrl), TextUrl(textUrl),
      SvgUrl(svgUrl), current)
  }
}
