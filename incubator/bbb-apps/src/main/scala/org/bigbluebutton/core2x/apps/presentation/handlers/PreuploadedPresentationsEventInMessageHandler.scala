package org.bigbluebutton.core2x.apps.presentation.handlers

import org.bigbluebutton.core2x.OutMessageGateway
import org.bigbluebutton.core2x.api.IncomingMsg.PreuploadedPresentationsEventInMessage
import org.bigbluebutton.core2x.meeting.models.MeetingStateModel

trait PreuploadedPresentationsEventInMessageHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handlePreuploadedPresentationsEventInMessage(msg: PreuploadedPresentationsEventInMessage): Unit = {
    val pres = msg.presentations
    val model = state.presentationsModel

    //pres foreach( p => )
  }
}
