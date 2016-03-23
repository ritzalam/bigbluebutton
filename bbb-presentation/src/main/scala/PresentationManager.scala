package org.bigbluebutton

import akka.actor.ActorSystem
import org.bigbluebutton.presentation.imp.DocumentConversionServiceImp
import org.bigbluebutton.presentation.{DocumentConversionService, UploadedPresentation}
import org.bigbluebutton.pubsub.MessageSender

class PresentationManager(system: ActorSystem, msgSender: MessageSender) {
  val log = system.log
  val documentConversionService = new DocumentConversionServiceImp() //TODO set params

  def handlePresentationUpload(meetingId: String, presId: String,
      presFilename: String, presentationBaseUrl:String, fileCompletePath:String): Unit = {

    log.debug("___::handlePresentationUpload " + meetingId)

    val uploadedPres = new UploadedPresentation(meetingId, presId,
      presFilename, presentationBaseUrl)
    uploadedPres.setUploadedFilePath(fileCompletePath)
    documentConversionService.processDocument(uploadedPres)
  }

}
