package org.bigbluebutton.core

import org.bigbluebutton.core.api.IncomingMsg._
import java.util.Date

import org.bigbluebutton.common.messages2x.objects.AnnotationStatus
import org.bigbluebutton.core.apps.breakout.BreakoutRoomModel
import org.bigbluebutton.core.apps.presentation.{ Page, Presentation, PresentationModel }
import org.bigbluebutton.core.apps.presentation.domain._
import org.bigbluebutton.core.user.UsersModel
import org.bigbluebutton.core.user.client.FlashWebUserAgent
import org.bigbluebutton.core.apps.whiteboard.WhiteboardModel
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.meeting.models._
import org.bigbluebutton.core.reguser.RegisteredUsersModel

trait MeetingTestFixtures {
  val bbbDevIntMeetingId = IntMeetingId("bbb-dev-weekly-meeting")
  val bbbDevExtMeetingId = ExtMeetingId("Bbb-Dev-Weekly-Meeting")
  val bbbDevMeetingName = Name("BBB Dev Weekly Meeting")
  val bbbDevRecorded = Recorded(true)
  val voiceConf = VoiceConf("85115")
  val duration = 120
  val autoStartRecording = false
  val allowStartStopRecording = true
  val maxUsers = 5
  val allowVoiceOnly = false
  val isBreakout = false

  val richardIntUserId = IntUserId("richard")
  val richardExtUserId = ExtUserId("Richard")
  val richardUserName = Name("Richard Alam")
  val richardRoles: Set[Role] = Set(ModeratorRole, PresenterRole)
  val richardAuthToken = SessionToken("Du30LetMeWin!")
  val richardAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val richardLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val richardWelcome = Welcome("Hello World!")
  val richardDialNums: Set[DialNumber] = Set(DialNumber("6135551234"))

  val richardRegisteredUser = RegisteredUsersModel.create(
    richardIntUserId,
    richardExtUserId,
    richardUserName,
    richardRoles,
    richardAuthToken,
    richardAvatar,
    richardLogoutUrl,
    richardWelcome,
    richardDialNums,
    PinNumber(85115001),
    "config1",
    "data12")

  val richardUser = User.create(richardIntUserId, richardExtUserId, richardUserName, richardRoles)

  val fredIntUserId = IntUserId("fred")
  val fredExtUserId = ExtUserId("Fred")
  val fredUserName = Name("Fred Dixon")
  val fredRoles: Set[Role] = Set(ViewerRole)
  val fredAuthToken = SessionToken("MdsLetMeWin!")
  val fredAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val fredLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val fredWelcome = Welcome("Hello World!")
  val fredDialNums = Set(DialNumber("6135551234"))

  val fredRegisteredUser = RegisteredUsersModel.create(
    fredIntUserId,
    fredExtUserId,
    fredUserName,
    fredRoles,
    fredAuthToken,
    fredAvatar,
    fredLogoutUrl,
    fredWelcome,
    fredDialNums,
    PinNumber(85115002),
    "config1",
    "data12")

  val fredUser = User.create(fredIntUserId, fredExtUserId, fredUserName, fredRoles)

  val felipeIntUserId = IntUserId("felipe")
  val felipeExtUserId = ExtUserId("Felipe")
  val felipeUserName = Name("Felipe Cecagno")
  val felipeRoles: Set[Role] = Set(ViewerRole)
  val felipeAuthToken = SessionToken("GraceLetMeWin!")
  val felipeAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val felipeLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val felipeWelcome = Welcome("Hello World!")
  val felipeDialNums = Set(DialNumber("6135551234"))

  val felipeRegisteredUser = RegisteredUsersModel.create(
    felipeIntUserId,
    felipeExtUserId,
    felipeUserName,
    felipeRoles,
    felipeAuthToken,
    felipeAvatar,
    felipeLogoutUrl,
    felipeWelcome,
    felipeDialNums,
    PinNumber(85115003),
    "config1",
    "data12")

  val felipeUser = User.create(felipeIntUserId, felipeExtUserId, felipeUserName, felipeRoles)

  val antonIntUserId = IntUserId("anton")
  val antonExtUserId = ExtUserId("Anton")
  val antonUserName = Name("Anton Georgiev")
  val antonRoles: Set[Role] = Set(ViewerRole)
  val antonAuthToken = SessionToken("MarLetMeWin!")
  val antonAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val antonLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val antonWelcome = Welcome("Hello World!")
  val antonDialNums = Set(DialNumber("6135551234"))

  val antonRegisteredUser = RegisteredUsersModel.create(
    antonIntUserId,
    antonExtUserId,
    antonUserName,
    antonRoles,
    antonAuthToken,
    antonAvatar,
    antonLogoutUrl,
    antonWelcome,
    antonDialNums,
    PinNumber(85115004),
    "config1",
    "data12")

  val antonUser = User.create(antonIntUserId, antonExtUserId, antonUserName, antonRoles)

  val chadIntUserId = IntUserId("chad")
  val chadExtUserId = ExtUserId("Chad")
  val chadUserName = Name("Chad Pilkey")
  val chadRoles: Set[Role] = Set(ViewerRole)
  val chadAuthToken = SessionToken("jbLetMeIn!")
  val chadAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val chadLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val chadWelcome = Welcome("Hello World!")
  val chadDialNums = Set(DialNumber("6135551234"))

  val chadRegisteredUser = RegisteredUsersModel.create(
    chadIntUserId,
    chadExtUserId,
    chadUserName,
    chadRoles,
    chadAuthToken,
    chadAvatar,
    chadLogoutUrl,
    chadWelcome,
    chadDialNums,
    PinNumber(85115005),
    "config1",
    "data12")

  val chadUser = User.create(chadIntUserId, chadExtUserId, chadUserName, chadRoles)

  val extensionProp = new MeetingExtensionProp2x
  val recordingProp = new MeetingRecordingProp

  val bbbDevProps: MeetingProperties2x = MeetingProperties2x(
    bbbDevIntMeetingId,
    bbbDevExtMeetingId,
    bbbDevMeetingName,
    voiceConf,
    duration,
    maxUsers,
    allowVoiceOnly,
    isBreakout,
    extensionProp,
    recordingProp)

  val abilities: MeetingPermissions = new MeetingPermissions
  val registeredUsers = new RegisteredUsersModel
  val users = new UsersModel
  val chats = new ChatModel
  val layouts = new LayoutModel
  val polls = new PollModel
  val whiteboards = new WhiteboardModel
  val presentations = new PresentationModel
  val breakoutRooms = new BreakoutRoomModel
  val captions = new CaptionModel
  val extension: MeetingExtensionStatus = new MeetingExtensionStatus

  val richardRegisterUserCommand = RegisterUserInMessage(
    bbbDevIntMeetingId,
    richardIntUserId,
    richardUserName,
    Set(ModeratorRole),
    richardExtUserId,
    richardAuthToken,
    richardAvatar,
    richardLogoutUrl,
    richardWelcome,
    richardDialNums,
    "config1",
    "data12")

  val richardValidateAuthTokenCommand = new ValidateAuthTokenInMessage(
    bbbDevIntMeetingId,
    richardIntUserId,
    richardAuthToken)

  val richardUserJoinCommand: UserJoinMeetingInMessage = new UserJoinMeetingInMessage(
    bbbDevIntMeetingId,
    richardIntUserId,
    richardAuthToken,
    SessionId("session-1"),
    ClientId("presence-1"),
    FlashWebUserAgent)

  val systemPubSubPingCommand = new PubSubPingMessageInMsg(
    "system1",
    new Date().getTime())

  val systemKeepAliveCommand = new KeepAliveMessageInMsg(
    "alive-id-002")

  val presentationMessageKeyCompleted = "CONVERSION_COMPLETED"
  val presentationMessageKeyError = "CONVERSION_ERROR" //todo recheck
  val presentationMessageCode = "CONVERT"
  val presentationPresentationId = PresentationId("presentationId001-123")

  val presentationConversionUpdateCommand = new PresentationConversionUpdateEventInMessage(
    bbbDevIntMeetingId, presentationMessageKeyCompleted, presentationMessageCode, presentationPresentationId)

  val presentationPageGeneratedCommand = new PresentationPageGeneratedEventInMessage(
    bbbDevIntMeetingId, presentationMessageKeyCompleted, presentationMessageCode, presentationPresentationId, 55, 44)

  val presentationPageCountErrorCommand = new PresentationPageCountErrorEventInMessage(
    bbbDevIntMeetingId, presentationMessageKeyError, presentationMessageCode, presentationPresentationId, 55, 44)

  val clearPresentationCommand = new ClearPresentationEventInMessage(bbbDevIntMeetingId, fredIntUserId,
    presentationPresentationId)

  val removePresentationCommand = new RemovePresentationEventInMessage(bbbDevIntMeetingId,
    fredIntUserId, presentationPresentationId)

  val getPresentationInfoCommand = new GetPresentationInfoEventInMessage(bbbDevIntMeetingId,
    fredIntUserId, presentationPresentationId)

  val presentationPageId = "presentationId001/page3"
  val presentationXPercentage = 78.15715
  val presentationYPercentage = 42.424242

  val sendCursorUpdateCommand = new SendCursorUpdateEventInMessage(bbbDevIntMeetingId,
    fredIntUserId, presentationPageId, presentationXPercentage, presentationYPercentage)

  val goToPageCommand = new GoToPageInEventInMessage(bbbDevIntMeetingId, fredIntUserId, presentationPageId)

  val getPageInfoCommand = new GetPageInfoEventInMessage(bbbDevIntMeetingId, fredIntUserId,
    presentationPageId)

  val piliShare = true

  val du30SharePresentationCommand = new SharePresentationEventInMessage(bbbDevIntMeetingId,
    fredIntUserId, presentationPresentationId, piliShare)

  val presentation2XOffset = XOffset(45.1245)
  val presentation2YOffset = YOffset(75.1245)
  val presentation2WidthRatio = WidthRatio(42.1523)
  val presentation2HeightRatio = HeightRatio(11.523)

  val resizeAndMovePageCommand = new ResizeAndMovePageEventInMessage(bbbDevIntMeetingId,
    fredIntUserId, presentation2XOffset, presentation2YOffset, presentationPageId, presentation2WidthRatio, presentation2HeightRatio)

  val piliPresentationName = "Demo Presentation"
  val piliCurrentPresentation = true
  val piliDefault = false
  var piliPages: Set[Page] = Set.empty
  piliPages = piliPages + new Page("pageId001", 1, ThumbUrl("someThumbUrl1"), SwfUrl("someSwfUrl1"),
    TextUrl("SomeTextUrl1"), SvgUrl("SomeSvgUrl1"))
  piliPages = piliPages + new Page("pageId002", 2, ThumbUrl("someThumbUrl2"), SwfUrl("someSwfUrl2"),
    TextUrl("SomeTextUrl2"), SvgUrl("SomeSvgUrl2"))

  val piliPresentation001: Presentation = new Presentation(presentationPresentationId,
    piliPresentationName, piliCurrentPresentation, piliPages, piliDefault)
  val du30PresentationConversionCompletedCommand = new PresentationConversionCompletedEventInMessage(bbbDevIntMeetingId,
    presentationMessageKeyCompleted, presentationMessageCode, piliPresentation001)

  val piliAnnotationId = "someAnnotId003"
  val piliAnnotationStatus = "DRAW_END"
  val piliShapeType = "TRIANGLE"
  val piliWbId = "whitebdId1342"
  val piliShapeStatus = AnnotationStatus.DRAW_END
  val piliTransparency: Boolean = false
  val piliThickness = new Integer(1)
  val piliColor = new Integer(0)

  //  var piliShape: scala.collection.immutable.Map[String, Object]
  //  var piliShape001: Map[String, Object]
  //  piliShape001 += "shapeType" -> piliShapeType
  //  piliShape001 += "points" -> List(86.71893, 10.835914, 89.04181, 8.034056)
  //  piliShape001 += "color" -> piliColor
  //  piliShape001 += "transperency" -> Boolean.box(true)
  //  piliShape001 += "status" -> piliShapeStatus
  //  piliShape001 += "id" -> "someShapeId13245"
  //  piliShape001 += "thickness" -> piliThickness
  //  piliShape001 += "wbId" -> piliWbId

  //  val piliAnnotation001 = new AnnotationVO(piliAnnotationId, piliAnnotationStatus, piliShapeType,
  //    piliShape001, piliWbId)
  //  val du30SendWhiteboardAnnotationRequestCommand = new SendWhiteboardAnnotationRequest(piliIntMeetingId,
  //    mdsIntUserId, piliAnnotation001)

}
