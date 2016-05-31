package org.bigbluebutton.core

import org.bigbluebutton.core.bus._
import org.bigbluebutton.core.api.IncomingMessage._
import org.bigbluebutton.core.api.IBigBlueButtonInGW
import scala.collection.JavaConversions._
import akka.actor.ActorSystem
import org.bigbluebutton.core.domain.AnnotationVO
import org.bigbluebutton.common.messages._
import org.bigbluebutton.messages._
import akka.event.Logging
import org.bigbluebutton.core.api.IncomingMessage
import org.bigbluebutton.core.domain._

class BigBlueButtonInGW(
    val system: ActorSystem,
    eventBus: IncomingEventBus,
    outGW: OutMessageGateway,
    val red5DeskShareIP: String,
    val red5DeskShareApp: String) extends IBigBlueButtonInGW {

  val log = Logging(system, getClass)

  val bbbActor = system.actorOf(BigBlueButtonActor.props(system, eventBus, outGW), "bigbluebutton-actor")
  eventBus.subscribe(bbbActor, "meeting-manager")

  val bbbActor2x = system.actorOf(BigBlueButtonActor2x.props(system, eventBus, outGW), "bigbluebutton-actor2x")
  eventBus.subscribe(bbbActor, "meeting-manager")

  def handleBigBlueButtonMessage(message: IBigBlueButtonMessage) {
    message match {
      case msg: StartCustomPollRequestMessage =>
        eventBus.publish(BigBlueButtonEvent("meeting-manager",
          new StartCustomPollRequest(IntMeetingId(msg.payload.meetingId),
            IntUserId(msg.payload.requesterId), msg.payload.pollType, msg.payload.answers)))
      case msg: PubSubPingMessage =>
        eventBus.publish(BigBlueButtonEvent("meeting-manager",
          new PubSubPing(msg.payload.system, msg.payload.timestamp)))

      case msg: CreateMeetingRequest =>
        val recProp = MeetingRecordingProp(
          Recorded(msg.payload.record),
          msg.payload.autoStartRecording,
          msg.payload.allowStartStopRecording)

        val mProps = new MeetingProperties2x(
          IntMeetingId(msg.payload.id),
          ExtMeetingId(msg.payload.externalId),
          Name(msg.payload.name),
          VoiceConf(msg.payload.voiceConfId),
          msg.payload.durationInMinutes,
          20,
          false,
          msg.payload.isBreakout,
          new MeetingExtensionProp(),
          recProp
        )

        eventBus.publish(BigBlueButtonEvent("meeting-manager",
          new CreateMeeting2x(IntMeetingId(msg.payload.id), mProps)))

      case msg: RegisterUserMessage =>
        val userRole = if (msg.role == "MODERATOR") ModeratorRole else ViewerRole
        eventBus.publish(BigBlueButtonEvent(msg.meetingID,
          new RegisterUser2xCommand(
            IntMeetingId(msg.meetingID),
            IntUserId(msg.internalUserId),
            Name(msg.fullname),
            Set(userRole),
            ExtUserId(msg.externUserID),
            AuthToken(msg.authToken),
            Avatar(msg.avatarURL),
            LogoutUrl(msg.avatarURL), // TODO: Setup logout url
            Welcome("Welcome"), // TODO: Setup welcome message
            Set(DialNumber("6132555555")), // TODO: Setup dial number
            Set("Config"), // TODO: Setup user config
            Set("Extra Data") // TOD: Setup extra user data
          )))

      case msg: ValidateAuthTokenMessage =>
        eventBus.publish(BigBlueButtonEvent(
          msg.meetingId,
          new ValidateAuthToken(
            IntMeetingId(msg.meetingId),
            IntUserId(msg.userId),
            AuthToken(msg.token),
            "todo", "todo")))
    }
  }

  def handleJsonMessage(json: String) {
    JsonMessageDecoder.decode(json) match {
      case Some(validMsg) => forwardMessage(validMsg)
      case None => log.error("Unhandled message: {}", json)
    }
  }

  def forwardMessage(msg: IncomingMessage.InMessage) = {
    msg match {
      case m: BreakoutRoomsListMessage => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case m: CreateBreakoutRooms => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case m: RequestBreakoutJoinURLInMessage => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case m: TransferUserToMeetingRequest => eventBus.publish(BigBlueButtonEvent(m.meetingId.value, m))
      case m: EndAllBreakoutRooms => eventBus.publish(BigBlueButtonEvent(m.meetingId, m))
      case _ => log.error("Unhandled message: {}", msg)
    }
  }

  def destroyMeeting(meetingID: String) {
    forwardMessage(new EndAllBreakoutRooms(meetingID))
    eventBus.publish(
      BigBlueButtonEvent(
        "meeting-manager",
        new DestroyMeeting(
          IntMeetingId(meetingID))))
  }

  def getAllMeetings(meetingID: String) {
    // eventBus.publish(BigBlueButtonEvent("meeting-manager",
    //   new GetAllMeetingsRequest("meetingId")))
  }

  def isAliveAudit(aliveId: String) {
    eventBus.publish(BigBlueButtonEvent("meeting-manager", new IncomingMessage.KeepAliveMessage(aliveId)))
  }

  def lockSettings(meetingID: String, locked: java.lang.Boolean,
    lockSettings: java.util.Map[String, java.lang.Boolean]) {
  }

  def statusMeetingAudit(meetingID: String) {

  }

  def endMeeting(meetingId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new EndMeeting(IntMeetingId(meetingId))))
  }

  def endAllMeetings() {

  }

  /**
   * ***********************************************************
   * Message Interface for Users
   * ***********************************************************
   */

  def registerUser(meetingID: String, userID: String, name: String, role: String, extUserID: String, authToken: String, avatarURL: String): Unit = {
    val userRole = if (role == "MODERATOR") Role.MODERATOR else Role.VIEWER
    //eventBus.publish(BigBlueButtonEvent(meetingID,
    //  new RegisterUser(meetingID, userID, name, userRole, extUserID, authToken, avatarURL)))
  }

  def sendLockSettings(meetingId: String, userId: String, settings: java.util.Map[String, java.lang.Boolean]) {
    // Convert java.util.Map to scala.collection.immutable.Map
    // settings.mapValues -> convaert java Map to scala mutable Map
    // v => v.booleanValue() -> convert java Boolean to Scala Boolean
    // toMap -> converts from scala mutable map to scala immutable map
    val s = settings.mapValues(v => v.booleanValue() /* convert java Boolean to Scala Boolean */ ).toMap
    val disableCam = s.getOrElse("disableCam", false)
    val disableMic = s.getOrElse("disableMic", false)
    val disablePrivChat = s.getOrElse("disablePrivateChat", false)
    val disablePubChat = s.getOrElse("disablePublicChat", false)
    val lockedLayout = s.getOrElse("lockedLayout", false)
    var lockOnJoin = s.getOrElse("lockOnJoin", false)
    var lockOnJoinConfigurable = s.getOrElse("lockOnJoinConfigurable", false)

    val permissions = new Permissions(disableCam = disableCam,
      disableMic = disableMic,
      disablePrivChat = disablePrivChat,
      disablePubChat = disablePubChat,
      lockedLayout = lockedLayout,
      lockOnJoin = lockOnJoin,
      lockOnJoinConfigurable = lockOnJoinConfigurable)

    eventBus.publish(BigBlueButtonEvent(meetingId,
      new SetLockSettings(IntMeetingId(meetingId), IntUserId(userId), permissions)))
  }

  def initLockSettings(meetingId: String, settings: java.util.Map[String, java.lang.Boolean]) {
    // Convert java.util.Map to scala.collection.immutable.Map
    // settings.mapValues -> convert java Map to scala mutable Map
    // v => v.booleanValue() -> convert java Boolean to Scala Boolean
    // toMap -> converts from scala mutable map to scala immutable map
    val s = settings.mapValues(v => v.booleanValue() /* convert java Boolean to Scala Boolean */ ).toMap
    val disableCam = s.getOrElse("disableCam", false)
    val disableMic = s.getOrElse("disableMic", false)
    val disablePrivChat = s.getOrElse("disablePrivateChat", false)
    val disablePubChat = s.getOrElse("disablePublicChat", false)
    val lockedLayout = s.getOrElse("lockedLayout", false)
    val lockOnJoin = s.getOrElse("lockOnJoin", false)
    val lockOnJoinConfigurable = s.getOrElse("lockOnJoinConfigurable", false)
    val permissions = new Permissions(disableCam = disableCam,
      disableMic = disableMic,
      disablePrivChat = disablePrivChat,
      disablePubChat = disablePubChat,
      lockedLayout = lockedLayout,
      lockOnJoin = lockOnJoin,
      lockOnJoinConfigurable = lockOnJoinConfigurable)

    eventBus.publish(BigBlueButtonEvent(meetingId,
      new InitLockSettings(IntMeetingId(meetingId), permissions)))
  }

  def initAudioSettings(meetingId: String, requesterID: String, muted: java.lang.Boolean) {
    //eventBus.publish(BigBlueButtonEvent(meetingId,
    //  new InitAudioSettings(IntMeetingId(meetingId)), IntUserId(requesterID), muted.booleanValue())))
  }

  def getLockSettings(meetingId: String, userId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetLockSettings(IntMeetingId(meetingId), IntUserId(userId))))
  }

  def lockUser(meetingId: String, requesterID: String, lock: Boolean, userId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new LockUserRequest(IntMeetingId(meetingId), IntUserId(requesterID), IntUserId(userId), lock)))
  }

  def setRecordingStatus(meetingId: String, userId: String, recording: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new SetRecordingStatus(IntMeetingId(meetingId), IntUserId(userId), recording.booleanValue())))
  }

  def getRecordingStatus(meetingId: String, userId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetRecordingStatus(IntMeetingId(meetingId), IntUserId(userId))))
  }

  // Users
  def userEmojiStatus(meetingId: String, userId: String, emojiStatus: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new UserEmojiStatus(IntMeetingId(meetingId), IntUserId(userId), EmojiStatus(emojiStatus))))
  }

  def ejectUserFromMeeting(meetingId: String, userId: String, ejectedBy: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new EjectUserFromMeeting(IntMeetingId(meetingId), IntUserId(userId), IntUserId(ejectedBy))))
  }

  def shareWebcam(meetingId: String, userId: String, stream: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new UserShareWebcam(IntMeetingId(meetingId), IntUserId(userId), stream)))
  }

  def unshareWebcam(meetingId: String, userId: String, stream: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new UserUnshareWebcam(IntMeetingId(meetingId), IntUserId(userId), stream)))
  }

  def setUserStatus(meetingId: String, userID: String, status: String, value: Object) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new ChangeUserStatus(IntMeetingId(meetingId), IntUserId(userID), status, value)))
  }

  def getUsers(meetingId: String, requesterID: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetUsers(IntMeetingId(meetingId), IntUserId(requesterID))))
  }

  def userLeft(meetingId: String, userID: String, sessionId: String): Unit = {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new UserLeaving(IntMeetingId(meetingId), IntUserId(userID), sessionId)))
  }

  def userJoin(meetingId: String, userID: String, authToken: String): Unit = {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new UserJoining(IntMeetingId(meetingId), IntUserId(userID), AuthToken(authToken))))
  }

  def assignPresenter(meetingId: String, newPresenterID: String, newPresenterName: String, assignedBy: String): Unit = {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new AssignPresenter(IntMeetingId(meetingId), IntUserId(newPresenterID), Name(newPresenterName), IntUserId(assignedBy))))
  }

  def getCurrentPresenter(meetingID: String, requesterID: String): Unit = {
    // do nothing
  }

  def userConnectedToGlobalAudio(voiceConf: String, userid: String, name: String) {
    // we are required to pass the meeting_id as first parameter (just to satisfy trait)
    // but it's not used anywhere. That's why we pass voiceConf twice instead
    eventBus.publish(BigBlueButtonEvent(voiceConf,
      new IncomingMessage.UserConnectedToGlobalAudio(IntMeetingId(voiceConf), voiceConf, IntUserId(userid), Name(name))))
  }

  def userDisconnectedFromGlobalAudio(voiceConf: String, userid: String, name: String) {
    // we are required to pass the meeting_id as first parameter (just to satisfy trait)
    // but it's not used anywhere. That's why we pass voiceConf twice instead
    eventBus.publish(BigBlueButtonEvent(voiceConf,
      new IncomingMessage.UserDisconnectedFromGlobalAudio(IntMeetingId(voiceConf), voiceConf, IntUserId(userid), Name(name))))
  }

  /**
   * ************************************************************************************
   * Message Interface for Presentation
   * ************************************************************************************
   */

  def clear(meetingId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new ClearPresentation(IntMeetingId(meetingId))))
  }

  def sendConversionUpdate(messageKey: String, meetingId: String, code: String, presentationId: String, presName: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new PresentationConversionUpdate(IntMeetingId(meetingId), messageKey, code,
        PresentationId(presentationId), presName)))
  }

  def sendPageCountError(messageKey: String, meetingId: String, code: String, presentationId: String, numberOfPages: Int, maxNumberPages: Int, presName: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new PresentationPageCountError(IntMeetingId(meetingId), messageKey, code,
        PresentationId(presentationId), numberOfPages, maxNumberPages, presName)))
  }

  def sendSlideGenerated(messageKey: String, meetingId: String, code: String, presentationId: String, numberOfPages: Int, pagesCompleted: Int, presName: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new PresentationSlideGenerated(IntMeetingId(meetingId), messageKey, code,
        PresentationId(presentationId), numberOfPages, pagesCompleted, presName)))
  }

  def generatePresentationPages(presId: String, numPages: Int, presBaseUrl: String): scala.collection.immutable.HashMap[String, Page] = {
    var pages = new scala.collection.immutable.HashMap[String, Page]
    val baseUrl =
      for (i <- 1 to numPages) {
        val id = presId + "/" + i
        val num = i;
        val current = if (i == 1) true else false
        val thumbnail = presBaseUrl + "/thumbnail/" + i
        val swfUri = presBaseUrl + "/slide/" + i

        val txtUri = presBaseUrl + "/textfiles/" + i
        val svgUri = presBaseUrl + "/svg/" + i

        val p = new Page(id = id, num = num, thumbUri = thumbnail, swfUri = swfUri,
          txtUri = txtUri, svgUri = svgUri,
          current = current)
        pages += (p.id -> p)
      }

    pages
  }

  def sendConversionCompleted(messageKey: String, meetingId: String, code: String, presentationId: String, numPages: Int, presName: String, presBaseUrl: String) {

    val pages = generatePresentationPages(presentationId, numPages, presBaseUrl)
    val presentation = new Presentation(id = presentationId, name = presName, pages = pages)
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new PresentationConversionCompleted(IntMeetingId(meetingId), messageKey, code, presentation)))

  }

  def removePresentation(meetingId: String, presentationID: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new RemovePresentation(IntMeetingId(meetingId), PresentationId(presentationID))))
  }

  def getPresentationInfo(meetingId: String, requesterID: String, replyTo: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetPresentationInfo(IntMeetingId(meetingId), IntUserId(requesterID), replyTo)))
  }

  def sendCursorUpdate(meetingId: String, xPercent: Double, yPercent: Double) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new SendCursorUpdate(IntMeetingId(meetingId), xPercent, yPercent)))
  }

  def resizeAndMoveSlide(meetingId: String, xOffset: Double, yOffset: Double, widthRatio: Double, heightRatio: Double) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new ResizeAndMoveSlide(IntMeetingId(meetingId), xOffset, yOffset, widthRatio, heightRatio)))
  }

  def gotoSlide(meetingId: String, pageId: String) {
    //	  println("**** Forwarding GotoSlide for meeting[" + meetingID + "] ****")
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GotoSlide(IntMeetingId(meetingId), pageId)))

  }

  def sharePresentation(meetingId: String, presentationID: String, share: Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new SharePresentation(IntMeetingId(meetingId), PresentationId(presentationID), share)))
  }

  def getSlideInfo(meetingId: String, requesterID: String, replyTo: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetSlideInfo(IntMeetingId(meetingId), IntUserId(requesterID), replyTo)))
  }

  /**
   * ***********************************************************************
   * Message Interface for Layout
   * *******************************************************************
   */

  def getCurrentLayout(meetingId: String, requesterID: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetCurrentLayoutRequest(IntMeetingId(meetingId), IntUserId(requesterID))))
  }

  def broadcastLayout(meetingId: String, requesterID: String, layout: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new BroadcastLayoutRequest(IntMeetingId(meetingId), IntUserId(requesterID), layout)))
  }

  def lockLayout(meetingId: String, setById: String, lock: Boolean, viewersOnly: Boolean, layout: String) {
    if (layout != null) {
      eventBus.publish(BigBlueButtonEvent(meetingId,
        new LockLayoutRequest(IntMeetingId(meetingId), IntUserId(setById), lock, viewersOnly, Some(layout))))
    } else {
      eventBus.publish(BigBlueButtonEvent(meetingId,
        new LockLayoutRequest(IntMeetingId(meetingId), IntUserId(setById), lock, viewersOnly, None)))
    }

  }

  /**
   * *******************************************************************
   * Message Interface for Chat
   * *****************************************************************
   */

  def getChatHistory(meetingId: String, requesterID: String, replyTo: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetChatHistoryRequest(IntMeetingId(meetingId), IntUserId(requesterID), replyTo)))
  }

  def sendPublicMessage(meetingId: String, requesterID: String, message: java.util.Map[String, String]) {
    // Convert java Map to Scala Map, then convert Mutable map to immutable map
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new SendPublicMessageRequest(IntMeetingId(meetingId), IntUserId(requesterID), mapAsScalaMap(message).toMap)))
  }

  def sendPrivateMessage(meetingId: String, requesterID: String, message: java.util.Map[String, String]) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new SendPrivateMessageRequest(IntMeetingId(meetingId), IntUserId(requesterID), mapAsScalaMap(message).toMap)))
  }

  /**
   * *******************************************************************
   * Message Interface for Whiteboard
   * *****************************************************************
   */
  private def buildAnnotation(annotation: scala.collection.mutable.Map[String, Object]): Option[AnnotationVO] = {
    var shape: Option[AnnotationVO] = None

    val id = annotation.getOrElse("id", null).asInstanceOf[String]
    val shapeType = annotation.getOrElse("type", null).asInstanceOf[String]
    val status = annotation.getOrElse("status", null).asInstanceOf[String]
    val wbId = annotation.getOrElse("whiteboardId", null).asInstanceOf[String]
    //    println("** GOT ANNOTATION status[" + status + "] shape=[" + shapeType + "]");

    if (id != null && shapeType != null && status != null && wbId != null) {
      shape = Some(new AnnotationVO(id, status, shapeType, annotation.toMap, wbId))
    }

    shape
  }

  def sendWhiteboardAnnotation(meetingId: String, requesterID: String, annotation: java.util.Map[String, Object]) {
    val ann: scala.collection.mutable.Map[String, Object] = mapAsScalaMap(annotation)

    buildAnnotation(ann) match {
      case Some(shape) => {
        eventBus.publish(BigBlueButtonEvent(meetingId,
          new SendWhiteboardAnnotationRequest(IntMeetingId(meetingId), IntUserId(requesterID), shape)))
      }
      case None => // do nothing
    }
  }

  def requestWhiteboardAnnotationHistory(meetingId: String, requesterID: String, whiteboardId: String, replyTo: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new GetWhiteboardShapesRequest(IntMeetingId(meetingId), IntUserId(requesterID), whiteboardId, replyTo)))
  }

  def clearWhiteboard(meetingId: String, requesterID: String, whiteboardId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new ClearWhiteboardRequest(IntMeetingId(meetingId), IntUserId(requesterID), whiteboardId)))
  }

  def undoWhiteboard(meetingId: String, requesterID: String, whiteboardId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new IncomingMessage.UndoWhiteboardRequest(IntMeetingId(meetingId), IntUserId(requesterID), whiteboardId)))
  }

  def enableWhiteboard(meetingId: String, requesterID: String, enable: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new EnableWhiteboardRequest(IntMeetingId(meetingId), IntUserId(requesterID), enable)))
  }

  def isWhiteboardEnabled(meetingId: String, requesterID: String, replyTo: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new IsWhiteboardEnabledRequest(IntMeetingId(meetingId), IntUserId(requesterID), replyTo)))
  }

  /**
   * *******************************************************************
   * Message Interface for Voice
   * *****************************************************************
   */

  def muteAllExceptPresenter(meetingId: String, requesterID: String, mute: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new MuteAllExceptPresenterRequest(IntMeetingId(meetingId), IntUserId(requesterID), mute)))
  }

  def muteAllUsers(meetingId: String, requesterID: String, mute: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new MuteMeetingRequest(IntMeetingId(meetingId), IntUserId(requesterID), mute)))
  }

  def isMeetingMuted(meetingId: String, requesterID: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new IsMeetingMutedRequest(IntMeetingId(meetingId), IntUserId(requesterID))))
  }

  def muteUser(meetingId: String, requesterID: String, userID: String, mute: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new MuteUserRequest(IntMeetingId(meetingId), IntUserId(requesterID), IntUserId(userID), mute)))
  }

  def lockMuteUser(meetingId: String, requesterID: String, userID: String, lock: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new LockUserRequest(IntMeetingId(meetingId), IntUserId(requesterID), IntUserId(userID), lock)))
  }

  def ejectUserFromVoice(meetingId: String, userId: String, ejectedBy: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new EjectUserFromVoiceRequest(IntMeetingId(meetingId), IntUserId(userId), IntUserId(ejectedBy))))
  }

  def voiceUserJoined(voiceConfId: String, voiceUserId: String, userId: String, callerIdName: String,
    callerIdNum: String, muted: java.lang.Boolean, avatarURL: String, talking: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(voiceConfId,
      new IncomingMessage.UserJoinedVoiceConfMessage(VoiceConf(voiceConfId), VoiceUserId(voiceUserId),
        IntUserId(userId), ExtUserId(userId), CallerId(CallerIdName(callerIdName), CallerIdNum(callerIdNum)),
        Muted(muted), Talking(talking), avatarURL, ListenOnly(false)
      /*hardcode listenOnly to false as the message for listenOnly is ConnectedToGlobalAudio*/ )))
  }

  def voiceUserLeft(voiceConfId: String, voiceUserId: String) {
    eventBus.publish(BigBlueButtonEvent(voiceConfId,
      new IncomingMessage.UserLeftVoiceConfMessage(VoiceConf(voiceConfId), VoiceUserId(voiceUserId))))
  }

  def voiceUserLocked(voiceConfId: String, voiceUserId: String, locked: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(voiceConfId,
      new IncomingMessage.UserLockedInVoiceConfMessage(VoiceConf(voiceConfId), VoiceUserId(voiceUserId), locked)))
  }

  def voiceUserMuted(voiceConfId: String, voiceUserId: String, muted: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(voiceConfId,
      new IncomingMessage.UserMutedInVoiceConfMessage(VoiceConf(voiceConfId), VoiceUserId(voiceUserId), muted)))
  }

  def voiceUserTalking(voiceConfId: String, voiceUserId: String, talking: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(voiceConfId,
      new IncomingMessage.UserTalkingInVoiceConfMessage(VoiceConf(voiceConfId), VoiceUserId(voiceUserId), talking)))
  }

  def voiceRecording(voiceConfId: String, recordingFile: String, timestamp: String, recording: java.lang.Boolean) {
    eventBus.publish(BigBlueButtonEvent(voiceConfId,
      new IncomingMessage.VoiceConfRecordingStartedMessage(VoiceConf(voiceConfId), recordingFile, recording, timestamp)))
  }

  /**
   * *******************************************************************
   * Message Interface for DeskShare
   * *****************************************************************
   */
  def deskShareStarted(conferenceName: String, callerId: String, callerIdName: String) {
    bbbActor ! new DeskShareStartedRequest(conferenceName, callerId, callerIdName)
  }

  def deskShareStopped(conferenceName: String, callerId: String, callerIdName: String) {
    bbbActor ! new DeskShareStoppedRequest(conferenceName, callerId, callerIdName)
  }

  def deskShareRTMPBroadcastStarted(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String) {
    bbbActor ! new DeskShareRTMPBroadcastStartedRequest(conferenceName, streamname, videoWidth, videoHeight, timestamp)
  }

  def deskShareRTMPBroadcastStopped(conferenceName: String, streamname: String, videoWidth: Int, videoHeight: Int, timestamp: String) {
    bbbActor ! new DeskShareRTMPBroadcastStoppedRequest(conferenceName, streamname, videoWidth, videoHeight, timestamp)
  }

  def deskShareGetInfoRequest(meetingId: String, requesterId: String, replyTo: String): Unit = {
    bbbActor ! new DeskShareGetDeskShareInfoRequest(meetingId, requesterId, replyTo)
  }

  // Polling
  def votePoll(meetingId: String, userId: String, pollId: String, questionId: Integer, answerId: Integer) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new RespondToPollRequest(IntMeetingId(meetingId), IntUserId(userId), pollId, questionId, answerId)))
  }

  def startPoll(meetingId: String, requesterId: String, pollId: String, pollType: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new StartPollRequest(IntMeetingId(meetingId), IntUserId(requesterId), pollType)))
  }

  def stopPoll(meetingId: String, userId: String, pollId: String) {
    eventBus.publish(BigBlueButtonEvent(meetingId,
      new StopPollRequest(IntMeetingId(meetingId), IntUserId(userId))))
  }

  def showPollResult(meetingId: String, requesterId: String, pollId: String, show: java.lang.Boolean) {
    if (show) {
      eventBus.publish(BigBlueButtonEvent(meetingId,
        new ShowPollResultRequest(IntMeetingId(meetingId), IntUserId(requesterId), pollId)))
    } else {
      eventBus.publish(BigBlueButtonEvent(meetingId,
        new HidePollResultRequest(IntMeetingId(meetingId), IntUserId(requesterId), pollId)))
    }
  }

  /**
   * *******************************************************************
   * Message Interface for Caption
   * *****************************************************************
   */

  def sendCaptionHistory(meetingID: String, requesterID: String) {
    bbbActor ! new SendCaptionHistoryRequest(IntMeetingId(meetingID), IntUserId(requesterID))
  }

  def updateCaptionOwner(meetingID: String, locale: String, ownerID: String) {
    bbbActor ! new UpdateCaptionOwnerRequest(IntMeetingId(meetingID), locale, ownerID)
  }

  def editCaptionHistory(meetingID: String, userID: String, startIndex: Integer, endIndex: Integer, locale: String, text: String) {
    bbbActor ! new EditCaptionHistoryRequest(IntMeetingId(meetingID), userID, startIndex, endIndex, locale, text)
  }
}