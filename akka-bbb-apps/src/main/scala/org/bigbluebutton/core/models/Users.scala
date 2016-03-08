package org.bigbluebutton.core.models

import org.bigbluebutton.core.util.RandomStringGenerator

case class MeetingConfig(name: String, id: MeetingID, passwords: MeetingPasswords,
  welcomeMsg: String, logoutUrl: String, maxUsers: Int, record: Boolean = false,
  duration: MeetingDuration, defaultAvatarURL: String, defaultConfigToken: String)

case class MeetingID(internal: String, external: String)

case class VoiceConfig(telVoice: String, webVoice: String, dialNumber: String)

case class MeetingPasswords(moderatorPass: String, viewerPass: String)

case class MeetingDuration(duration: Int = 0, createdTime: Long = 0,
  startTime: Long = 0, endTime: Long = 0)

case class MeetingInfo(meetingID: String, meetingName: String, recorded: Boolean, voiceBridge: String, duration: Int)

object Role extends Enumeration {
  type Role = Value
  val MODERATOR = Value("MODERATOR")
  val VIEWER = Value("VIEWER")
}

case class UserVO(id: IntUserId, extId: ExtUserId, name: Name, role: Role.Role,
  emojiStatus: EmojiStatus, presenter: IsPresenter, hasStream: HasStream, locked: Locked,
  webcamStreams: Set[String], phoneUser: PhoneUser, voiceUser: VoiceUser,
  listenOnly: ListenOnly, joinedWeb: JoinedWeb)

case class CallerId(name: CallerIdName, number: CallerIdNum)

case class VoiceUser(id: VoiceUserId, webUserId: IntUserId, callerId: CallerId,
  joinedVoice: JoinedVoice, locked: Locked, muted: Muted,
  talking: Talking, listenOnly: ListenOnly)

case class User2(id: String, extId: String, name: String, roles: Set[String],
  emojiStatus: String, presenter: Boolean, hasStream: Boolean, locked: Boolean,
  webcamStreams: Set[String], phoneUser: Boolean, voiceUser: VoiceUser2,
  listenOnly: Boolean, joinedWeb: Boolean)

case class VoiceUser2(id: String, webId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, listenOnly: Boolean)

case class Presenter(id: IntUserId, name: Name, assignedBy: IntUserId)

case class User(id: String, externId: String, name: String, moderator: Boolean,
  avatarUrl: String, logoutUrl: String, presenter: Boolean, callerId: CallerId,
  phoneCaller: Boolean, emojiStatus: String, muted: Boolean, talking: Boolean)

case class Permissions(disableCam: Boolean = false, disableMic: Boolean = false,
  disablePrivChat: Boolean = false, disablePubChat: Boolean = false,
  lockedLayout: Boolean = false, lockOnJoin: Boolean = false, lockOnJoinConfigurable: Boolean = false)

case class Voice(id: String, webId: String, callId: CallerId, phoningIn: Boolean,
  joined: Boolean, locked: Boolean, muted: Boolean, talking: Boolean)

