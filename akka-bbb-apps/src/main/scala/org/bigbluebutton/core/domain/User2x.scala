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
    modify(user)(_.voice.muted).setTo(true)
  }

  def unmuted(user: User2x): User2x = {
    modify(user)(_.voice.muted).setTo(false)
  }

  def talking(user: User2x): User2x = {
    modify(user)(_.voice.talking).setTo(true)
  }

  def notTalking(user: User2x): User2x = {
    modify(user)(_.voice.talking).setTo(false)
  }

  def joinedVoice(user: User2x): User2x = {
    modify(user)(_.voice.joined).setTo(true)
  }

  def leftVoice(user: User2x): User2x = {
    modify(user)(_.voice.joined).setTo(false)
  }
}

case class User2x(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId,
  roles: Set[Role2x],
  presence: Set[Presence],
  voice: Voice2x,
  permissions: UserPermissions,
  webcamStreams: Set[Stream],
  deskshareStreams: Set[Stream])

case class Voice2x(
  id: String,
  webId: String,
  callId: CallerId,
  phoningIn: Boolean,
  joined: Boolean,
  locked: Boolean,
  muted: Boolean,
  talking: Boolean)


case class Stream(id: String, uri: String, viewers: Set[IntUserId])