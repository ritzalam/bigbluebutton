package org.bigbluebutton.core.apps.presentation.handlers

import org.bigbluebutton.core.OutMessageGateway
import org.bigbluebutton.core.api.IncomingMsg.PreuploadedPresentationsEventInMessage
import org.bigbluebutton.core.meeting.models.MeetingStateModel

trait PreuploadedPresentationsEventInMessageHandler {
  val state: MeetingStateModel
  val outGW: OutMessageGateway

  def handlePreuploadedPresentationsEventInMessage(msg: PreuploadedPresentationsEventInMessage): Unit = {
    val pres = msg.presentations
    val model = state.presentationsModel

    //pres foreach( p => )
  }
}
