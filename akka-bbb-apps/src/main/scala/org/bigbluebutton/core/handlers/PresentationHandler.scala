package org.bigbluebutton.core.handlers

import org.bigbluebutton.core.api._
import com.google.gson.Gson
import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.LiveMeeting
import org.bigbluebutton.core.models._

trait PresentationHandler extends PresentationMessageSender {
  this: LiveMeeting =>

  val outGW: OutMessageGateway

  private var cursorLocation = new CursorLocation

  def handlePreuploadedPresentations(msg: PreuploadedPresentations) {
    val pres = msg.presentations

    msg.presentations.foreach(presentation => {
      presModel.addPresentation(presentation)

      sharePresentation(PresentationId(presentation.id), true)
    })
  }

  def handleInitializeMeeting(msg: InitializeMeeting) {

  }

  def handleClearPresentation(msg: ClearPresentation) {
    sendClearPresentation(IntMeetingId(mProps.id.value), Recorded(mProps.recorded.value))
  }

  def handlePresentationConversionUpdate(msg: PresentationConversionUpdate) {
    outGW.send(new PresentationConversionProgress(mProps.id, msg.messageKey,
      msg.code, msg.presentationId, msg.presName))
  }

  def handlePresentationPageCountError(msg: PresentationPageCountError) {
    outGW.send(new PresentationConversionError(mProps.id, msg.messageKey,
      msg.code, msg.presentationId,
      msg.numberOfPages,
      msg.maxNumberPages, msg.presName))
  }

  def handlePresentationSlideGenerated(msg: PresentationSlideGenerated) {
    outGW.send(new PresentationPageGenerated(mProps.id, msg.messageKey,
      msg.code, msg.presentationId,
      msg.numberOfPages,
      msg.pagesCompleted, msg.presName))
  }

  def handlePresentationConversionCompleted(msg: PresentationConversionCompleted) {

    presModel.addPresentation(msg.presentation)

    outGW.send(new PresentationConversionDone(mProps.id, mProps.recorded, msg.messageKey,
      msg.code, msg.presentation))

    sharePresentation(PresentationId(msg.presentation.id), true)
  }

  def handleRemovePresentation(msg: RemovePresentation) {
    val curPres = presModel.getCurrentPresentation

    val removedPresentation = presModel.remove(msg.presentationId)

    curPres foreach (cp => {
      if (cp.id == msg.presentationId.value) {
        sharePresentation(msg.presentationId, false);
      }
    })

    sendRemovePresentation(msg.meetingId, mProps.recorded, msg.presentationId)
  }

  def handleGetPresentationInfo(msg: GetPresentationInfo) {
    val curPresenter = usersModel.getCurrentPresenterInfo();
    val presenter = new CurrentPresenter(curPresenter.id, curPresenter.name, curPresenter.assignedBy)
    val presentations = presModel.getPresentations
    val presentationInfo = new CurrentPresentationInfo(presenter, presentations)
    sendGetPresentationInfo(mProps.id, mProps.recorded,
      msg.requesterId, presentationInfo, ReplyTo(msg.replyTo))
  }

  def handleSendCursorUpdate(msg: SendCursorUpdate) {
    cursorLocation = new CursorLocation(msg.xPercent, msg.yPercent)
    outGW.send(new SendCursorUpdateOutMsg(mProps.id, mProps.recorded, msg.xPercent, msg.yPercent))
  }

  def handleResizeAndMoveSlide(msg: ResizeAndMoveSlide) {
    val page = presModel.resizePage(msg.xOffset, msg.yOffset,
      msg.widthRatio, msg.heightRatio);
    page foreach (p => outGW.send(new ResizeAndMoveSlideOutMsg(mProps.id, mProps.recorded, p)))
  }

  def handleGotoSlide(msg: GotoSlide) {
    //      println("Received GotoSlide for meeting=[" +  msg.meetingID + "] page=[" + msg.page + "]")
    //      println("*** Before change page ****")
    //      printPresentations
    presModel.changePage(msg.page) foreach { page =>
      //        println("Switching page for meeting=[" +  msg.meetingID + "] page=[" + page.id + "]")
      outGW.send(new GotoSlideOutMsg(mProps.id, mProps.recorded, page))

    }
    //      println("*** After change page ****")
    //      printPresentations

    usersModel.getCurrentPresenter() foreach { pres =>
      handleStopPollRequest(StopPollRequest(mProps.id, pres.id))
    }

  }

  def handleSharePresentation(msg: SharePresentation) {
    sharePresentation(msg.presentationId, msg.share)
  }

  def sharePresentation(presentationId: PresentationId, share: Boolean) {
    val pres = presModel.sharePresentation(presentationId)

    pres foreach { p =>
      outGW.send(new SharePresentationOutMsg(mProps.id, mProps.recorded, p))

      presModel.getCurrentPage(p) foreach { page =>
        outGW.send(new GotoSlideOutMsg(mProps.id, mProps.recorded, page))
      }
    }

  }

  def handleGetSlideInfo(msg: GetSlideInfo) {
    presModel.getCurrentPresentation foreach { pres =>
      presModel.getCurrentPage(pres) foreach { page =>
        outGW.send(new GetSlideInfoOutMsg(mProps.id, mProps.recorded, msg.requesterId, page, msg.replyTo))
      }
    }

  }

  def printPresentations() {
    presModel.getPresentations foreach { pres =>
      println("presentation id=[" + pres.id + "] current=[" + pres.current + "]")
      pres.pages.values foreach { page =>
        println("page id=[" + page.id + "] current=[" + page.current + "]")
      }
    }

  }

}