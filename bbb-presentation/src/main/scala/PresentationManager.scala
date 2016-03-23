package org.bigbluebutton

import java.io.File

import akka.actor.ActorSystem
import org.bigbluebutton.presentation.{DocumentConversionService, UploadedPresentation}
import org.bigbluebutton.pubsub.MessageSender
import org.apache.commons.io.FilenameUtils

class PresentationManager(system: ActorSystem, msgSender: MessageSender,
                          documentConversionService: DocumentConversionService) {
  val log = system.log


  def handlePresentationUpload(meetingId: String, presId: String,
      presFilename: String, presentationBaseUrl:String, fileCompletePath:String): Unit = {

    log.debug("___::handlePresentationUpload " + meetingId)
    val file = new File(fileCompletePath)
    log.info("____file is " + file.getName)

    val uploadedPres = new UploadedPresentation(meetingId, presId,
    presFilename, presentationBaseUrl)
    uploadedPres.setUploadedFile(file)

    documentConversionService.processDocument(uploadedPres)
  }

}
