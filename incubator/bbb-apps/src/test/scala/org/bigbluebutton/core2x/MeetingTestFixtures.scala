package org.bigbluebutton.core2x

import org.bigbluebutton.core2x.api.IncomingMsg._
import org.bigbluebutton.core2x.apps.presentation.{ Page, Presentation, PresentationModel }
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.core2x.models._
import java.util.Date

import org.bigbluebutton.messages.vo.enums.AnnotationStatus

import org.bigbluebutton.core2x.apps.presentation.domain._

trait MeetingTestFixtures {
  val piliIntMeetingId = IntMeetingId("pili-pinas-2016")
  val piliExtMeetingId = ExtMeetingId("Pili-Pinas-2016")
  val piliMeetingName = Name("Pili Pinas 2016")
  val piliRecorded = Recorded(true)
  val voiceConf = VoiceConf("85115")
  val duration = 120
  val autoStartRecording = false
  val allowStartStopRecording = true
  val maxUsers = 5
  val allowVoiceOnly = false
  val isBreakout = false

  val du30IntUserId = IntUserId("du30")
  val du30ExtUserId = ExtUserId("DU30")
  val du30UserName = Name("Rody Duterte")
  val du30Roles: Set[Role2x] = Set(ModeratorRole, PresenterRole)
  val du30AuthToken = AuthToken("Du30LetMeWin!")
  val du30Avatar = Avatar("http://www.gravatar.com/sdsdas")
  val du30LogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val du30Welcome = Welcome("Hello World!")
  val du30DialNums: Set[DialNumber] = Set(DialNumber("6135551234"))

  val du30RegisteredUser = RegisteredUsersModel.create(
    du30IntUserId,
    du30ExtUserId,
    du30UserName,
    du30Roles,
    du30AuthToken,
    du30Avatar,
    du30LogoutUrl,
    du30Welcome,
    du30DialNums,
    PinNumber(85115001),
    "config1",
    "data12")

  val du30User = User3x.create(du30IntUserId, du30ExtUserId, du30UserName, du30Roles)

  val mdsIntUserId = IntUserId("mds")
  val mdsExtUserId = ExtUserId("MDS")
  val mdsUserName = Name("Miriam Santiago")
  val mdsRoles: Set[Role2x] = Set(ViewerRole)
  val mdsAuthToken = AuthToken("MdsLetMeWin!")
  val mdsAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val mdsLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val mdsWelcome = Welcome("Hello World!")
  val mdsDialNums = Set(DialNumber("6135551234"))

  val mdsRegisteredUser = RegisteredUsersModel.create(
    mdsIntUserId,
    mdsExtUserId,
    mdsUserName,
    mdsRoles,
    mdsAuthToken,
    mdsAvatar,
    mdsLogoutUrl,
    mdsWelcome,
    mdsDialNums,
    PinNumber(85115002),
    "config1",
    "data12")

  val mdsUser = User3x.create(mdsIntUserId, mdsExtUserId, mdsUserName, mdsRoles)

  val graceIntUserId = IntUserId("grace")
  val graceExtUserId = ExtUserId("GRACE")
  val graceUserName = Name("Grace Poe")
  val graceRoles: Set[Role2x] = Set(ViewerRole)
  val graceAuthToken = AuthToken("GraceLetMeWin!")
  val graceAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val graceLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val graceWelcome = Welcome("Hello World!")
  val graceDialNums = Set(DialNumber("6135551234"))

  val graceRegisteredUser = RegisteredUsersModel.create(
    graceIntUserId,
    graceExtUserId,
    graceUserName,
    graceRoles,
    graceAuthToken,
    graceAvatar,
    graceLogoutUrl,
    graceWelcome,
    graceDialNums,
    PinNumber(85115003),
    "config1",
    "data12")

  val graceUser = User3x.create(graceIntUserId, graceExtUserId, graceUserName, graceRoles)

  val marIntUserId = IntUserId("mar")
  val marExtUserId = ExtUserId("MAR")
  val marUserName = Name("Mar Roxas")
  val marRoles: Set[Role2x] = Set(ViewerRole)
  val marAuthToken = AuthToken("MarLetMeWin!")
  val marAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val marLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val marWelcome = Welcome("Hello World!")
  val marDialNums = Set(DialNumber("6135551234"))

  val marRegisteredUser = RegisteredUsersModel.create(
    marIntUserId,
    marExtUserId,
    marUserName,
    marRoles,
    marAuthToken,
    marAvatar,
    marLogoutUrl,
    marWelcome,
    marDialNums,
    PinNumber(85115004),
    "config1",
    "data12")

  val marUser = User3x.create(marIntUserId, marExtUserId, marUserName, marRoles)

  val jbIntUserId = IntUserId("jb")
  val jbExtUserId = ExtUserId("jb")
  val jbUserName = Name("Jojemar Binay")
  val jbRoles: Set[Role2x] = Set(ViewerRole)
  val jbAuthToken = AuthToken("jbLetMeIn!")
  val jbAvatar = Avatar("http://www.gravatar.com/sdsdas")
  val jbLogoutUrl = LogoutUrl("http://www.amoutofhere.com")
  val jbWelcome = Welcome("Hello World!")
  val jbDialNums = Set(DialNumber("6135551234"))

  val jbRegisteredUser = RegisteredUsersModel.create(
    jbIntUserId,
    jbExtUserId,
    jbUserName,
    jbRoles,
    jbAuthToken,
    jbAvatar,
    jbLogoutUrl,
    jbWelcome,
    jbDialNums,
    PinNumber(85115005),
    "config1",
    "data12")

  val jbUser = User3x.create(marIntUserId, marExtUserId, marUserName, marRoles)

  val extensionProp = new MeetingExtensionProp2x
  val recordingProp = new MeetingRecordingProp

  val piliProps: MeetingProperties2x = MeetingProperties2x(
    piliIntMeetingId,
    piliExtMeetingId,
    piliMeetingName,
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

  val du30RegisterUserCommand = RegisterUserInMessage(
    piliIntMeetingId,
    du30IntUserId,
    du30UserName,
    Set(ModeratorRole),
    du30ExtUserId,
    du30AuthToken,
    du30Avatar,
    du30LogoutUrl,
    du30Welcome,
    du30DialNums,
    "config1",
    "data12")

  val du30ValidateAuthTokenCommand = new ValidateAuthTokenInMessage(
    piliIntMeetingId,
    du30IntUserId,
    du30AuthToken)

  val du30UserJoinCommand: UserJoinMeetingInMessage = new UserJoinMeetingInMessage(
    piliIntMeetingId,
    du30IntUserId,
    du30AuthToken,
    SessionId("session-1"),
    PresenceId("presence-1"),
    FlashWebUserAgent)

  val du30PubSubPingCommand = new PubSubPingMessageInMsg(
    "system1",
    new Date().getTime())

  val du30KeepAliveCommand = new KeepAliveMessageInMsg(
    "alive-id-002")

  val piliMessageKeyCompleted = "CONVERSION_COMPLETED"
  val piliMessageKeyError = "CONVERSION_ERROR" //todo recheck
  val piliMessageCode = "CONVERT"
  val piliPresentationId = PresentationId("presentationId001-123")

  val du30PresentationConversionUpdateCommand = new PresentationConversionUpdateEventInMessage(
    piliIntMeetingId, piliMessageKeyCompleted, piliMessageCode, piliPresentationId)

  val du30PresentationPageGeneratedCommand = new PresentationPageGeneratedEventInMessage(
    piliIntMeetingId, piliMessageKeyCompleted, piliMessageCode, piliPresentationId, 55, 44)

  val du30PresentationPageCountErrorCommand = new PresentationPageCountErrorEventInMessage(
    piliIntMeetingId, piliMessageKeyError, piliMessageCode, piliPresentationId, 55, 44)

  val du30ClearPresentationCommand = new ClearPresentationEventInMessage(piliIntMeetingId, mdsIntUserId,
    piliPresentationId)

  val du30RemovePresentationCommand = new RemovePresentationEventInMessage(piliIntMeetingId,
    mdsIntUserId, piliPresentationId)

  val du30GetPresentationInfoCommand = new GetPresentationInfoEventInMessage(piliIntMeetingId,
    mdsIntUserId, piliPresentationId)

  val piliPageId = "presentationId001/page3"
  val piliXPercentage = 78.15715
  val piliYPercentage = 42.424242

  val du30SendCursorUpdateCommand = new SendCursorUpdateEventInMessage(piliIntMeetingId,
    mdsIntUserId, piliPageId, piliXPercentage, piliYPercentage)

  val du30GoToPageCommand = new GoToPageInEventInMessage(piliIntMeetingId, mdsIntUserId, piliPageId)

  val du30GetPageInfoCommand = new GetPageInfoEventInMessage(piliIntMeetingId, mdsIntUserId,
    piliPageId)

  val piliShare = true

  val du30SharePresentationCommand = new SharePresentationEventInMessage(piliIntMeetingId,
    mdsIntUserId, piliPresentationId, piliShare)

  val piliXOffset = XOffset(45.1245)
  val piliYOffset = YOffset(75.1245)
  val piliWidthRatio = WidthRatio(42.1523)
  val piliHeightRatio = HeightRatio(11.523)

  val du30ResizeAndMovePageCommand = new ResizeAndMovePageEventInMessage(piliIntMeetingId,
    mdsIntUserId, piliXOffset, piliYOffset, piliPageId, piliWidthRatio, piliHeightRatio)

  val piliPresentationName = "Demo Presentation"
  val piliCurrentPresentation = true
  val piliDefault = false
  var piliPages: Set[Page] = null
  piliPages = piliPages + new Page("pageId001", 1, ThumbUrl("someThumbUrl1"), SwfUrl("someSwfUrl1"),
    TextUrl("SomeTextUrl1"), SvgUrl("SomeSvgUrl1"))
  piliPages = piliPages + new Page("pageId002", 2, ThumbUrl("someThumbUrl2"), SwfUrl("someSwfUrl2"),
    TextUrl("SomeTextUrl2"), SvgUrl("SomeSvgUrl2"))

  val piliPresentation001: Presentation = new Presentation(piliPresentationId,
    piliPresentationName, piliCurrentPresentation, piliPages, piliDefault)
  val du30PresentationConversionCompletedCommand = new PresentationConversionCompletedEventInMessage(piliIntMeetingId,
    piliMessageKeyCompleted, piliMessageCode, piliPresentation001)

  val piliAnnotationId = "someAnnotId003"
  val piliAnnotationStatus = "DRAW_END"
  val piliShapeType = "TRIANGLE"
  val piliWbId = "whitebdId1342"
  val piliShapeStatus = AnnotationStatus.DRAW_END
  //  val piliTransparency: Boolean = false
  val piliThickness = new Integer(1)
  val piliColor = new Integer(0)

  //  var piliShape: scala.collection.immutable.Map[String, Object]
  var piliShape001: Map[String, Object]
  piliShape001 += "shapeType" -> piliShapeType
  piliShape001 += "points" -> List(86.71893, 10.835914, 89.04181, 8.034056)
  piliShape001 += "color" -> piliColor
  piliShape001 += "transperency" -> Boolean.box(true)
  piliShape001 += "status" -> piliShapeStatus
  piliShape001 += "id" -> "someShapeId13245"
  piliShape001 += "thickness" -> piliThickness
  piliShape001 += "wbId" -> piliWbId

  val piliAnnotation001 = new AnnotationVO(piliAnnotationId, piliAnnotationStatus, piliShapeType,
    piliShape001, piliWbId)
  val du30SendWhiteboardAnnotationRequestCommand = new SendWhiteboardAnnotationRequest(piliIntMeetingId,
    mdsIntUserId, piliAnnotation001)

}
