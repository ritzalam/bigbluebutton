
package org.bigbluebutton

import akka.actor.ActorSystem
import org.bigbluebutton.endpoint.redis.{ AppsRedisSubscriberActor, KeepAliveRedisPublisher, RedisPublisher }
import org.bigbluebutton.presentation.imp._
import org.bigbluebutton.pubsub.{ MessageSender, PresentationReceiver }

object Boot extends App with SystemConfiguration {

  implicit val system = ActorSystem("bbb-presentation-conversion-system")

  val redisPublisher = new RedisPublisher(system)
  val msgSender = new MessageSender(redisPublisher)

  val officeToPdfConversionService = new OfficeToPdfConversionService()

  val pageExtractor = new GhostscriptPageExtractor()
  pageExtractor.setGhostscriptExec(ghostScriptExec)
  pageExtractor.setNoPdfMarkWorkaround(noPdfMarkWorkaround)

  val imageMagickPageConverter = new ImageMagickPageConverter()
  imageMagickPageConverter.setImageMagickDir(imageMagickDir)

  val png2SwfConverter = new Png2SwfPageConverter()
  png2SwfConverter.setSwfToolsDir(swfToolsDir)

  val jpg2SwfConverter = new Jpeg2SwfPageConverter()
  jpg2SwfConverter.setSwfToolsDir(swfToolsDir)

  val pageCounter = new Pdf2SwfPageCounter()
  pageCounter.setSwfToolsDir(swfToolsDir)

  val pageCounterService = new PageCounterService()
  pageCounterService.setPageCounter(pageCounter)
  pageCounterService.setMaxNumPages(maxNumPages)

  val pdf2SwfPageConverter = new Pdf2SwfPageConverter()
  pdf2SwfPageConverter.setGhostscriptExec(ghostScriptExec)
  pdf2SwfPageConverter.setSwfToolsDir(swfToolsDir)
  pdf2SwfPageConverter.setImageMagickDir(imageMagickDir)
  pdf2SwfPageConverter.setFontsDir(fontsDir)
  pdf2SwfPageConverter.setNoPdfMarkWorkaround(noPdfMarkWorkaround)
  pdf2SwfPageConverter.setPlacementsThreshold(placementsThreshold)
  pdf2SwfPageConverter.setDefineTextThreshold(defineTextThreshold)
  pdf2SwfPageConverter.setImageTagThreshold(imageTagThreshold)

  val imageConvSvc = new PdfPageToImageConversionService()
  imageConvSvc.setImageToSwfConverter(png2SwfConverter)
  imageConvSvc.setPageExtractor(pageExtractor)
  imageConvSvc.setPdfToImageConverter(imageMagickPageConverter)

  val thumbCreator = new ThumbnailCreatorImp()
  thumbCreator.setBlankThumbnail(BLANK_THUMBNAIL)
  thumbCreator.setImageMagickDir(imageMagickDir)

  val textFileCreator = new TextFileCreatorImp()
  textFileCreator.setImageMagickDir(imageMagickDir)

  val svgImageCreator = new SvgImageCreatorImp()
  svgImageCreator.setImageMagickDir(imageMagickDir)

  val swfSlidesGenerationProgressNotifier = new SwfSlidesGenerationProgressNotifier()
  swfSlidesGenerationProgressNotifier.setMessageSender(msgSender)

  val imageToSwfSlidesGenerationService = new ImageToSwfSlidesGenerationService()
  imageToSwfSlidesGenerationService.setPngPageConverter(png2SwfConverter)
  imageToSwfSlidesGenerationService.setJpgPageConverter(jpg2SwfConverter)
  imageToSwfSlidesGenerationService.setSvgImageCreator(svgImageCreator)
  imageToSwfSlidesGenerationService.setThumbnailCreator(thumbCreator)
  imageToSwfSlidesGenerationService.setTextFileCreator(textFileCreator)
  imageToSwfSlidesGenerationService.setBlankSlide(BLANK_SLIDE)
  imageToSwfSlidesGenerationService.setMaxConversionTime(maxConversionTime)
  imageToSwfSlidesGenerationService.setSwfSlidesGenerationProgressNotifier(swfSlidesGenerationProgressNotifier)

  val pdfToSwfSlidesGenerationService = new PdfToSwfSlidesGenerationService(numConversionThreads)
  pdfToSwfSlidesGenerationService.setCounterService(pageCounterService)
  pdfToSwfSlidesGenerationService.setPageConverter(pdf2SwfPageConverter)
  pdfToSwfSlidesGenerationService.setPdfPageToImageConversionService(imageConvSvc)
  pdfToSwfSlidesGenerationService.setThumbnailCreator(thumbCreator)
  pdfToSwfSlidesGenerationService.setTextFileCreator(textFileCreator)
  pdfToSwfSlidesGenerationService.setSvgImageCreator(svgImageCreator)
  pdfToSwfSlidesGenerationService.setBlankSlide(BLANK_SLIDE)
  pdfToSwfSlidesGenerationService.setMaxSwfFileSize(MAX_SWF_FILE_SIZE)
  pdfToSwfSlidesGenerationService.setMaxConversionTime(maxConversionTime)
  pdfToSwfSlidesGenerationService.setSwfSlidesGenerationProgressNotifier(swfSlidesGenerationProgressNotifier)
  pdfToSwfSlidesGenerationService.setSvgImagesRequired(svgImagesRequired)

  val documentConversionService = new DocumentConversionServiceImp()
  documentConversionService.setMessageSender(msgSender)
  documentConversionService.setOfficeToPdfConversionService(officeToPdfConversionService)
  documentConversionService.setPdfToSwfSlidesGenerationService(pdfToSwfSlidesGenerationService)
  documentConversionService.setImageToSwfSlidesGenerationService(imageToSwfSlidesGenerationService)

  val presentationManager = new PresentationManager(system, msgSender, documentConversionService)
  val redisMsgReceiver = new PresentationReceiver(presentationManager)
  val redisSubscriberActor = system.actorOf(AppsRedisSubscriberActor.props(redisMsgReceiver),
    "redis-subscriber")

  val keepAliveRedisPublisher = new KeepAliveRedisPublisher(system, redisPublisher)
}
