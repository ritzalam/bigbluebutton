package org.bigbluebutton.core.domain

case class BreakoutUser(id: String, name: String)
case class BreakoutRoom(id: String, name: String, voiceConfId: String,
  assignedUsers: Vector[String], users: Vector[BreakoutUser], defaultPresentationURL: String)

case class QuestionResponsesVO(val questionID: String, val responseIDs: Array[String])
case class PollResponseVO(val pollID: String, val responses: Array[QuestionResponsesVO])
case class ResponderVO(responseID: String, user: Responder)

case class AnswerVO(val id: Int, val key: String, val text: Option[String], val responders: Option[Array[Responder]])
case class QuestionVO(val id: Int, val questionType: String, val multiResponse: Boolean, val questionText: Option[String], val answers: Option[Array[AnswerVO]])
case class PollVO(val id: String, val questions: Array[QuestionVO], val title: Option[String], val started: Boolean, val stopped: Boolean, val showResult: Boolean)

case class Responder(val userId: IntUserId, name: Name)

case class ResponseOutVO(id: String, text: String, responders: Array[Responder] = Array[Responder]())
case class QuestionOutVO(id: String, multiResponse: Boolean, question: String, responses: Array[ResponseOutVO])

case class SimpleAnswerOutVO(id: Int, key: String)
case class SimplePollOutVO(id: String, answers: Array[SimpleAnswerOutVO])

case class SimpleVoteOutVO(id: Int, key: String, numVotes: Int)
case class SimplePollResultOutVO(id: String, answers: Array[SimpleVoteOutVO], numRespondents: Int, numResponders: Int)

case class CurrentPresenter(id: IntUserId, name: Name, assignedBy: IntUserId)
case class CurrentPresentationInfo(presenter: CurrentPresenter, presentations: Seq[Presentation])
case class CursorLocation(xPercent: Double = 0D, yPercent: Double = 0D)
case class Presentation(id: String, name: String, current: Boolean = false,
  pages: scala.collection.immutable.HashMap[String, Page])

case class Page(id: String, num: Int,
  thumbUri: String = "",
  swfUri: String,
  txtUri: String,
  svgUri: String,
  current: Boolean = false,
  xOffset: Double = 0, yOffset: Double = 0,
  widthRatio: Double = 100D, heightRatio: Double = 100D)

case class AnnotationVO(id: String, status: String, shapeType: String, shape: scala.collection.immutable.Map[String, Object], wbId: String)
case class Whiteboard(id: String, shapes: Seq[AnnotationVO])

object Role extends Enumeration {
  type Role = Value
  val MODERATOR = Value("MODERATOR")
  val VIEWER = Value("VIEWER")
}

case class RegisteredUser(id: IntUserId, extId: ExtUserId, name: Name, role: Role.Role, authToken: AuthToken)

case class UserVO(id: IntUserId, extId: ExtUserId, name: Name, role: Role.Role,
  emojiStatus: EmojiStatus, presenter: IsPresenter, hasStream: HasStream, locked: Locked,
  webcamStreams: Set[String], phoneUser: PhoneUser, voiceUser: VoiceUser,
  listenOnly: ListenOnly, joinedWeb: JoinedWeb)

case class CallerId(name: CallerIdName, number: CallerIdNum)

case class VoiceUser(id: VoiceUserId, webUserId: IntUserId, callerId: CallerId,
  joinedVoice: JoinedVoice, locked: Locked, muted: Muted,
  talking: Talking, listenOnly: ListenOnly)

case class Presenter(id: IntUserId, name: Name, assignedBy: IntUserId)

case class User(id: IntUserId, extId: ExtUserId, name: Name, roles: Set[String],
  presence: Set[Presence], permissions: UserAuthz)

case class Permissions(disableCam: Boolean = false, disableMic: Boolean = false,
  disablePrivChat: Boolean = false, disablePubChat: Boolean = false,
  lockedLayout: Boolean = false, lockOnJoin: Boolean = false, lockOnJoinConfigurable: Boolean = false)

case class Voice1(id: String, webId: String, callId: CallerId, phoningIn: Boolean,
  joined: Boolean, locked: Boolean, muted: Boolean, talking: Boolean)

sealed trait Presence
case class FlashBrowserPresence(id: IntUserId, extId: ExtUserId, name: Name, sessionId: String) extends Presence
case class FlashSipPresence(id: IntUserId, extId: ExtUserId, name: Name, sessionId: String) extends Presence

/**
 *   Use Value Classes to help with type safety.
 *   https://ivanyu.me/blog/2014/12/14/value-classes-in-scala/
 */

case class Name(value: String) extends AnyVal
case class IntMeetingId(value: String) extends AnyVal
case class ExtMeetingId(value: String) extends AnyVal
case class Duration(value: Int) extends AnyVal
case class Recorded(value: Boolean) extends AnyVal
case class VoiceConf(value: String) extends AnyVal
case class AuthToken(value: String) extends AnyVal
case class IntUserId(value: String) extends AnyVal
case class ExtUserId(value: String) extends AnyVal
case class EmojiStatus(value: String) extends AnyVal
case class IsPresenter(value: Boolean) extends AnyVal
case class HasStream(value: Boolean) extends AnyVal
case class Locked(value: Boolean) extends AnyVal
case class PhoneUser(value: Boolean) extends AnyVal
case class ListenOnly(value: Boolean) extends AnyVal
case class JoinedWeb(value: Boolean) extends AnyVal
case class VoiceUserId(value: String) extends AnyVal
case class CallerIdName(value: String) extends AnyVal
case class CallerIdNum(value: String) extends AnyVal
case class JoinedVoice(value: Boolean) extends AnyVal
case class Muted(value: Boolean) extends AnyVal
case class Talking(value: Boolean) extends AnyVal
case class PresentationId(value: String) extends AnyVal
case class ReplyTo(value: String) extends AnyVal //TODO can we use IntUserId here?

case class BreakoutId(value: String) extends AnyVal