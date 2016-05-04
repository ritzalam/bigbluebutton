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

case class UserAbilities(removed: Set[Abilities2x], added: Set[Abilities2x], applyMeetingAbilities: Boolean)

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
    modify(user)(_.restricted.applyMeetingAbilities).setTo(true)
  }

  def unPinUserPermissions(user: User2x): User2x = {
    modify(user)(_.restricted.applyMeetingAbilities).setTo(false)
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
  restricted: UserAbilities,
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

object Stream {
  def update(stream: Stream, uri: String): Stream = {
    modify(stream)(_.uri).setTo(uri)
  }

  def add(stream: Stream, user: IntUserId): Stream = {
    val newViewers = stream.viewers + user
    modify(stream)(_.viewers).setTo(newViewers)
  }

  def remove(stream: Stream, user: IntUserId): Stream = {
    val newViewers = stream.viewers - user
    modify(stream)(_.viewers).setTo(newViewers)
  }
}

case class Stream(id: String, uri: String, viewers: Set[IntUserId])

trait Voice3x
case class FlashWebListenOnly(sessionId: SessionId) extends Voice3x

object FlashWebDuplex {
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

case class FlashWebDuplex(sessionId: SessionId, muted: Muted, talking: Talking) extends Voice3x

case class WebRtcWebListenOnly(sessionId: SessionId) extends Voice3x

object WebRtcWebDuplex {
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

case class WebRtcWebDuplex(sessionId: SessionId, muted: Muted, talking: Talking) extends Voice3x

object PhoneCalling {
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

case class PhoneCalling(sessionId: SessionId, callerId: CallerId, muted: Muted, talking: Talking) extends Voice3x

object User3x {
  def update(old: Presence2x, user: User3x, updated: Presence2x): User3x = {
    modify(user)(_.presence).setTo((user.presence - old) + updated)
  }

  def findWithPresenceId(presence: Set[Presence2x], presenceId: PresenceId): Option[Presence2x] = {
    presence.find(p => p.id == presenceId)
  }

  def add(user: User3x, presence: Presence2x): User3x = {
    modify(user)(_.presence).setTo(user.presence + presence)
  }

  def add(user: User3x, role: Role2x): User3x = {
    modify(user)(_.roles).setTo(user.roles + role)
  }

  def remove(user: User3x, role: Role2x): User3x = {
    modify(user)(_.roles).setTo(user.roles - role)
  }

}

case class User3x(
  id: IntUserId,
  roles: Set[Role2x],
  presence: Set[Presence2x],
  permissions: UserAbilities,
  roleData: Set[RoleData],
  config: Set[String],
  extData: Set[String])

trait PresenceUserAgent
case object FlashWebUserAgent extends PresenceUserAgent
case object Html5WebUserAgent extends PresenceUserAgent

trait Presence2x { val id: PresenceId }

object FlashWebPresence2x {
  def save(presence: FlashWebPresence2x, data: DataApp2x): FlashWebPresence2x = {
    modify(presence)(_.dataApp).setTo(Some(data))
  }

  def save(presence: FlashWebPresence2x, app: WebcamApp2x): FlashWebPresence2x = {
    modify(presence)(_.webcamApp).setTo(Some(app))
  }

  def save(presence: FlashWebPresence2x, app: VoiceApp2x): FlashWebPresence2x = {
    modify(presence)(_.voiceApp).setTo(Some(app))
  }

  def save(presence: FlashWebPresence2x, app: ScreenshareApp2x): FlashWebPresence2x = {
    modify(presence)(_.screenshareApp).setTo(Some(app))
  }
}

case class FlashWebPresence2x(
  id: PresenceId,
  dataApp: Option[DataApp2x] = None,
  webcamApp: Option[WebcamApp2x] = None,
  voiceApp: Option[VoiceApp2x] = None,
  screenshareApp: Option[ScreenshareApp2x] = None) extends Presence2x

object Html5WebPresence2x {
  def save(presence: Html5WebPresence2x, data: DataApp2x): Html5WebPresence2x = {
    modify(presence)(_.dataApp).setTo(Some(data))
  }

  def save(presence: Html5WebPresence2x, app: WebcamApp2x): Html5WebPresence2x = {
    modify(presence)(_.webcamApp).setTo(Some(app))
  }

  def save(presence: Html5WebPresence2x, app: VoiceApp2x): Html5WebPresence2x = {
    modify(presence)(_.voiceApp).setTo(Some(app))
  }

  def save(presence: Html5WebPresence2x, app: ScreenshareApp2x): Html5WebPresence2x = {
    modify(presence)(_.screenshareApp).setTo(Some(app))
  }
}

case class Html5WebPresence2x(
  id: PresenceId,
  name: Name,
  dataApp: Option[DataApp2x] = None,
  webcamApp: Option[WebcamApp2x] = None,
  voiceApp: Option[VoiceApp2x] = None,
  screenshareApp: Option[ScreenshareApp2x] = None) extends Presence2x

object DataApp2x {
  def update(data: DataApp2x, session: SessionId): DataApp2x = {
    modify(data)(_.sessionId).setTo(session)
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

