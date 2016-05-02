package org.bigbluebutton.core.models

import java.util.concurrent.TimeUnit
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.util.RandomStringGenerator

class RunningMeeting2x {

}

trait Meeting2x {
  val props: MeetingProperties

  val regUsers: RegisteredUsers2x = new RegisteredUsers2x
  val users: Users2x = new Users2x

  private var audioSettingsInited = false
  private var permissionsInited = false
  private var permissions: Set[Abilities2x] = Set.empty
  private var recording = false
  private var muted = false
  private var meetingEnded = false
  private var meetingMuted = false

  private var hasLastWebUserLeft = false
  private var lastWebUserLeftOnTimestamp: Long = 0

  private var voiceRecordingFilename: String = ""

  private var extension = new MeetingExtensionProp

  val startedOn = timeNowInSeconds

  var breakoutRoomsStartedOn: Long = 0
  var breakoutRoomsdurationInMinutes: Int = 0

  def isExtensionAllowed: Boolean = extension.numExtensions < extension.maxExtensions
  def incNumExtension(): Int = {
    if (extension.numExtensions < extension.maxExtensions) {
      extension = extension.copy(numExtensions = extension.numExtensions + 1); extension.numExtensions
    }
    extension.numExtensions
  }

  def notice15MinutesSent() = extension = extension.copy(sent15MinNotice = true)
  def notice10MinutesSent() = extension = extension.copy(sent10MinNotice = true)
  def notice5MinutesSent() = extension = extension.copy(sent5MinNotice = true)

  def getMeetingExtensionProp: MeetingExtensionProp = extension
  def muteMeeting() = meetingMuted = true
  def unmuteMeeting() = meetingMuted = false
  def isMeetingMuted: Boolean = meetingMuted
  def recordingStarted() = recording = true
  def recordingStopped() = recording = false
  def isRecording: Boolean = recording
  def lastWebUserLeft() = lastWebUserLeftOnTimestamp = timeNowInMinutes
  def lastWebUserLeftOn(): Long = lastWebUserLeftOnTimestamp
  def resetLastWebUserLeftOn() = lastWebUserLeftOnTimestamp = 0
  def setVoiceRecordingFilename(path: String) = voiceRecordingFilename = path
  def getVoiceRecordingFilename: String = voiceRecordingFilename
  def permisionsInitialized(): Boolean = permissionsInited
  def initializePermissions() = permissionsInited = true
  def audioSettingsInitialized(): Boolean = audioSettingsInited
  def initializeAudioSettings() = audioSettingsInited = true
  def permissionsEqual(other: Permissions): Boolean = permissions == other
  def getPermissions: Set[Abilities2x] = permissions
  def setPermissions(p: Set[Abilities2x]) = permissions = p
  def meetingHasEnded() = meetingEnded = true
  def hasMeetingEnded: Boolean = meetingEnded
  def timeNowInMinutes(): Long = TimeUnit.NANOSECONDS.toMinutes(System.nanoTime())
  def timeNowInSeconds(): Long = TimeUnit.NANOSECONDS.toSeconds(System.nanoTime())

  def generateWebUserId(users: Array[UserVO]): IntUserId = {
    val webUserId = IntUserId(RandomStringGenerator.randomAlphanumericString(6))
    if (!hasUser(webUserId, users)) webUserId else generateWebUserId(users)
  }

  def hasUser(userId: IntUserId, users: Array[UserVO]): Boolean = {
    val u = users find { u => u.id.value == userId.value }
    u.isDefined
  }
}
