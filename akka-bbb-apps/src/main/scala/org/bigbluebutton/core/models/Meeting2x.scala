package org.bigbluebutton.core.models

import java.util.concurrent.TimeUnit
import org.bigbluebutton.core.domain._
import org.bigbluebutton.core.util.RandomStringGenerator
import com.softwaremill.quicklens._

class RunningMeeting2x {

}

trait Meeting2x {
  val props: MeetingProperties

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

case class Meeting3x(
  abilities: Set[Abilities2x] = Set.empty,
  isRecording: Boolean = false,
  muted: Boolean = false,
  ended: Boolean = false,
  hasLastWebUserLeft: Boolean = false,
  lastWebUserLeftOnTimestamp: Long = 0L,
  voiceRecordingFilename: String = "",
  startedOn: Long = 0L,
  pinNumbers: Set[PinNumber] = Set.empty,
  lastGeneratedPin: Int = 0,
  breakoutRoomsStartedOn: Long = 0L,
  breakoutRoomsDurationInMinutes: Int = 120)

object Meeting3x {
  def isExtensionAllowed(extension: MeetingExtensionProp): Boolean = extension.numExtensions < extension.maxExtensions
  def incNumExtension(extension: MeetingExtensionProp): MeetingExtensionProp = {
    if (extension.numExtensions < extension.maxExtensions) {
      modify(extension)(_.numExtensions).setTo(extension.numExtensions + 1)
    }
    extension
  }

  def fifteenMinutesNoticeSent(extension: MeetingExtensionProp): MeetingExtensionProp = {
    modify(extension)(_.sent15MinNotice).setTo(true)
  }

  def tenMinutesNoticeSent(extension: MeetingExtensionProp): MeetingExtensionProp = {
    modify(extension)(_.sent10MinNotice).setTo(true)
  }

  def fiveMinutesNoticeSent(extension: MeetingExtensionProp): MeetingExtensionProp = {
    modify(extension)(_.sent5MinNotice).setTo(true)
  }

  def mute(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.muted).setTo(true)
  }

  def unMute(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.muted).setTo(false)
  }

  def recordingStarted(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.isRecording).setTo(true)
  }

  def recordingStopped(meeting: Meeting3x): Meeting3x = {
    modify(meeting)(_.isRecording).setTo(false)
  }

}

object PinNumberGenerator {
  def generatePin(conf: VoiceConf, meeting: Meeting3x): PinNumber = {
    def inc(curPin: Int): Int = {
      if ((curPin + 1) < 1000) curPin + 1
      else 1
    }

    def genAvailablePin(): PinNumber = {
      val pin = conf.value.toInt + inc(meeting.lastGeneratedPin)
      val myPin = PinNumber(pin)
      if (meeting.pinNumbers.contains(myPin)) genAvailablePin
      myPin
    }

    genAvailablePin
  }
}