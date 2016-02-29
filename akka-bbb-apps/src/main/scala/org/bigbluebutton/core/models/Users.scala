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

case class RegisteredUser(id: IntUserId, extId: ExtUserId, name: Name, role: Role.Role, authToken: AuthToken)

case class UserVO(userID: String, externUserID: String, name: String, role: Role.Role,
  emojiStatus: String, presenter: Boolean, hasStream: Boolean, locked: Boolean,
  webcamStreams: Set[String], phoneUser: Boolean, voiceUser: VoiceUser,
  listenOnly: Boolean, joinedWeb: Boolean)

case class VoiceUser(userId: String, webUserId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, listenOnly: Boolean)

case class User2(id: String, extId: String, name: String, roles: Set[String],
  emojiStatus: String, presenter: Boolean, hasStream: Boolean, locked: Boolean,
  webcamStreams: Set[String], phoneUser: Boolean, voiceUser: VoiceUser2,
  listenOnly: Boolean, joinedWeb: Boolean)

case class VoiceUser2(id: String, webId: String, callerName: String,
  callerNum: String, joined: Boolean, locked: Boolean, muted: Boolean,
  talking: Boolean, listenOnly: Boolean)

case class Presenter(presenterID: String, presenterName: String, assignedBy: String)

case class User(id: String, externId: String, name: String, moderator: Boolean,
  avatarUrl: String, logoutUrl: String, presenter: Boolean, callerId: CallerId,
  phoneCaller: Boolean, emojiStatus: String, muted: Boolean, talking: Boolean)

case class CallerId(name: String, number: String)

case class Permissions(disableCam: Boolean = false, disableMic: Boolean = false,
  disablePrivChat: Boolean = false, disablePubChat: Boolean = false,
  lockedLayout: Boolean = false, lockOnJoin: Boolean = false, lockOnJoinConfigurable: Boolean = false)

case class Voice(id: String, webId: String, callId: CallerId, phoningIn: Boolean,
  joined: Boolean, locked: Boolean, muted: Boolean, talking: Boolean)

class Users2 {
  var users = new collection.immutable.HashMap[String, User2]

  def add(uvo: User2) {
    users += uvo.id -> uvo
  }

  def remove(id: String): Option[User2] = {
    val user = users get (id)
    user foreach (u => users -= id)
    user
  }

  def generateWebUserId(): String = {
    val webUserId = RandomStringGenerator.randomAlphanumericString(6)
    if (!hasUser(webUserId)) webUserId else generateWebUserId
  }

  def hasUser(userId: String): Boolean = {
    users.contains(userId)
  }
}