package org.bigbluebutton.core.domain

import com.softwaremill.quicklens._

trait Role2x
case object ModeratorRole extends Role2x
case object ViewerRole extends Role2x
case object PresenterRole extends Role2x

sealed trait Presence
case class FlashBrowserPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class FlashTwoWayPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class FlashListenOnlyPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class WebRtcListenOnlyPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class WebRtcTwoWayPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class PhoneInPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class RegisteredUser2x(id: IntUserId, extId: ExtUserId, name: Name, roles: Set[Role2x], authToken: AuthToken)

case class UserPermissions(permissions: Set[Permission2x], pinned: Boolean)

object User2x {
  def muted(user: User2x): User2x = {
    modify(user)(_.voice.muted).setTo(Muted(true))
  }

  def unmuted(user: User2x): User2x = {
    modify(user)(_.voice.muted).setTo(Muted(false))
  }

  def talking(user: User2x): User2x = {
    modify(user)(_.voice.talking).setTo(Talking(true))
  }

  def notTalking(user: User2x): User2x = {
    modify(user)(_.voice.talking).setTo(Talking(false))
  }

  def joinedVoice(user: User2x): User2x = {
    modify(user)(_.voice.joined).setTo(JoinedVoice(true))
  }

  def leftVoice(user: User2x): User2x = {
    modify(user)(_.voice.joined).setTo(JoinedVoice(false))
  }

  def updateSessionId(user: User2x, sessionId: SessionId): User2x = {
    modify(user)(_.sessionId).setTo(sessionId)
  }

  def pinUserPermissions(user: User2x): User2x = {
    modify(user)(_.permissions.pinned).setTo(true)
  }

  def unPinUserPermissions(user: User2x): User2x = {
    modify(user)(_.permissions.pinned).setTo(false)
  }
}

case class User2x(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId,
  emojiStatus: EmojiStatus,
  roles: Set[Role2x],
  voice: Voice2x,
  permissions: UserPermissions,
  webcamStreams: Set[Stream],
  deskshareStreams: Set[Stream])

case class Voice2x(
  id: VoiceUserId,
  webId: IntUserId,
  callId: CallerId,
  phoningIn: PhoneUser,
  joined: JoinedVoice,
  locked: Locked,
  muted: Muted,
  talking: Talking)

case class Stream(id: String, uri: String, viewers: Set[IntUserId])