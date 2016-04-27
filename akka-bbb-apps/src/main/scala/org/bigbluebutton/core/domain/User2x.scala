package org.bigbluebutton.core.domain

import com.softwaremill.quicklens._

trait Role2x
case object ModeratorRole extends Role2x
case object ViewerRole extends Role2x
case object PresenterRole extends Role2x
case object StenographerRole extends Role2x
case object SignLanguageInterpreterRole extends Role2x

sealed trait Presence
case class FlashBrowserPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class FlashVoiceTwoWayPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class FlashVoiceListenOnlyPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class WebRtcVoiceListenOnlyPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class WebRtcVoiceTwoWayPresence(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId) extends Presence

case class PhoneInVoicePresence(
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

trait Voice3x
case class FlashWebListenOnly(sessionId: SessionId) extends Voice3x

case class FlashWebDuplex(sessionId: SessionId, muted: Muted, talking: Talking) extends Voice3x {
  def mute(voice: FlashWebDuplex): FlashWebDuplex = {
    modify(voice)(_.muted).setTo(Muted(true))
  }

  def unmute(voice: FlashWebDuplex): FlashWebDuplex = {
    modify(voice)(_.muted).setTo(Muted(false))
  }

  def talking(voice: FlashWebDuplex): FlashWebDuplex = {
    modify(voice)(_.talking).setTo(Talking(true))
  }

  def silent(voice: FlashWebDuplex): FlashWebDuplex = {
    modify(voice)(_.talking).setTo(Talking(false))
  }
}

case class WebRtcWebListenOnly(sessionId: SessionId) extends Voice3x

case class WebRtcWebDuplex(sessionId: SessionId, muted: Muted, talking: Talking) extends Voice3x {
  def mute(voice: WebRtcWebDuplex): WebRtcWebDuplex = {
    modify(voice)(_.muted).setTo(Muted(true))
  }

  def unmute(voice: WebRtcWebDuplex): WebRtcWebDuplex = {
    modify(voice)(_.muted).setTo(Muted(false))
  }

  def talking(voice: WebRtcWebDuplex): WebRtcWebDuplex = {
    modify(voice)(_.talking).setTo(Talking(true))
  }

  def silent(voice: WebRtcWebDuplex): WebRtcWebDuplex = {
    modify(voice)(_.talking).setTo(Talking(false))
  }
}

case class PhoneCalling(sessionId: SessionId, callerId: CallerId, muted: Muted, talking: Talking) extends Voice3x {
  def mute(voice: PhoneCalling): PhoneCalling = {
    modify(voice)(_.muted).setTo(Muted(true))
  }

  def unmute(voice: PhoneCalling): PhoneCalling = {
    modify(voice)(_.muted).setTo(Muted(false))
  }

  def talking(voice: PhoneCalling): PhoneCalling = {
    modify(voice)(_.talking).setTo(Talking(true))
  }

  def silent(voice: PhoneCalling): PhoneCalling = {
    modify(voice)(_.talking).setTo(Talking(false))
  }
}

case class User3x(
  id: IntUserId,
  roles: Set[Role2x],
  applyMeetingPermissions: Boolean,
  presence: Set[Presence2x],
  restrictedPermissions: Set[Permission2x],
  roleData: Set[RoleData])

trait PresenceUserAgent
case object FlashWebUserAgent extends PresenceUserAgent
case object Html5WebUserAgent extends PresenceUserAgent

sealed trait Presence2x
case class FlashWebPresence(
    id: PresenceId,
    dataApp: DataApp2x,
    webcamApp: WebcamApp2x,
    voiceApp: VoiceApp2x,
    screenshareApp: ScreenshareApp2x) extends Presence2x {
  val userAgent: PresenceUserAgent = FlashWebUserAgent

  def save(presence: FlashWebPresence, data: DataApp2x): FlashWebPresence = {
    modify(presence)(_.dataApp).setTo(data)
  }

  def save(presence: FlashWebPresence, webcamApp: WebcamApp2x): FlashWebPresence = {
    modify(presence)(_.webcamApp).setTo(webcamApp)
  }

  def save(presence: FlashWebPresence, voiceApp: VoiceApp2x): FlashWebPresence = {
    modify(presence)(_.voiceApp).setTo(voiceApp)
  }

  def save(presence: FlashWebPresence, screenshareApp: ScreenshareApp2x): FlashWebPresence = {
    modify(presence)(_.screenshareApp).setTo(screenshareApp)
  }
}

case class Html5WebPresence(
    id: PresenceId,
    dataApp: DataApp2x,
    webcamApp: WebcamApp2x,
    voiceApp: VoiceApp2x,
    screenshareApp: ScreenshareApp2x) extends Presence2x {
  val userAgent: PresenceUserAgent = Html5WebUserAgent

  def save(presence: Html5WebPresence, data: DataApp2x): Html5WebPresence = {
    modify(presence)(_.dataApp).setTo(data)
  }

  def save(presence: Html5WebPresence, webcamApp: WebcamApp2x): Html5WebPresence = {
    modify(presence)(_.webcamApp).setTo(webcamApp)
  }

  def save(presence: Html5WebPresence, voiceApp: VoiceApp2x): Html5WebPresence = {
    modify(presence)(_.voiceApp).setTo(voiceApp)
  }

  def save(presence: Html5WebPresence, screenshareApp: ScreenshareApp2x): Html5WebPresence = {
    modify(presence)(_.screenshareApp).setTo(screenshareApp)
  }
}

case class DataApp2x(sessionId: SessionId)

case class WebcamApp2x(sessionId: SessionId, streams: Set[Stream])

case class VoiceApp2x(sessionId: SessionId, voice: Voice3x)

case class ScreenshareApp2x(sessionId: SessionId, streams: Set[Stream])

trait RoleData
case class SignLanguageInterpreterRoleData(locale: Locale, stream: Stream) extends RoleData {
  val role: Role2x = SignLanguageInterpreterRole
}
case class StenographerRoleData(locale: Locale, captionStream: CaptionStream) extends RoleData {
  val role: Role2x = StenographerRole
}

case class CaptionStream(url: String)