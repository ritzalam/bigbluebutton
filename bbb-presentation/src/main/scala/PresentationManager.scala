package org.bigbluebutton

import java.io.File

import akka.actor.ActorSystem
import org.bigbluebutton.presentation.{ DocumentConversionService, UploadedPresentation }
import org.bigbluebutton.pubsub.MessageSender

class PresentationManager(system: ActorSystem, msgSender: MessageSender,
    documentConversionService: DocumentConversionService) {
  val log = system.log

  def handlePresentationUpload(meetingId: String, presId: String,
    presFilename: String, presentationBaseUrl: String, fileCompletePath: String): Unit = {

    val file = new File(fileCompletePath)

    val uploadedPres = new UploadedPresentation(meetingId, presId,
      presFilename, presentationBaseUrl)
    uploadedPres.setUploadedFile(file)

    documentConversionService.processDocument(uploadedPres)
  }

}
