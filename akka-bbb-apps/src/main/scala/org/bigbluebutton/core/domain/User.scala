package org.bigbluebutton.core.domain

object Role {
  val MODERATOR = "Moderator"
  val VIEWER = "Viewer"
  val PRESENTER = "Presenter"
}

case class RegisteredUser(id: IntUserId, extId: ExtUserId, name: Name, roles: Set[String], authToken: AuthToken)

case class UserVO(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  roles: Set[String],
  emojiStatus: EmojiStatus,
  presenter: IsPresenter,
  hasStream: HasStream,
  locked: Locked,
  webcamStreams: Set[String],
  phoneUser: PhoneUser,
  voiceUser: VoiceUser,
  listenOnly: ListenOnly,
  joinedWeb: JoinedWeb)

case class CallerId(name: CallerIdName, number: CallerIdNum)

case class VoiceUser(
  id: VoiceUserId,
  webUserId: IntUserId,
  callerId: CallerId,
  joinedVoice: JoinedVoice,
  locked: Locked,
  muted: Muted,
  talking: Talking,
  listenOnly: ListenOnly)

case class Presenter(id: IntUserId, name: Name, assignedBy: IntUserId)

case class User2x(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId,
  roles: Set[String],
  presence: Set[Presence],
  permissions: UserAuthz)

case class Permissions(
  disableCam: Boolean = false,
  disableMic: Boolean = false,
  disablePrivChat: Boolean = false,
  disablePubChat: Boolean = false,
  lockedLayout: Boolean = false,
  lockOnJoin: Boolean = false,
  lockOnJoinConfigurable: Boolean = false)

case class Voice2x(
  id: String,
  webId: String,
  callId: CallerId,
  phoningIn: Boolean,
  joined: Boolean,
  locked: Boolean,
  muted: Boolean,
  talking: Boolean)

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

case class SessionId(value: String) extends AnyVal

case class ReplyTo(value: String) extends AnyVal

