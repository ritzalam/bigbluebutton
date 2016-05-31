package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._

object Voice4x {
  def mute(voice: Voice4x): Voice4x = {
    modify(voice)(_.muted).setTo(Muted(true))
  }

  def unMute(voice: Voice4x): Voice4x = {
    modify(voice)(_.muted).setTo(Muted(false))
  }

  def joined(voice: Voice4x): Voice4x = {
    modify(voice)(_.joined).setTo(JoinedVoice(true))
  }

  def left(voice: Voice4x): Voice4x = {
    Voice4x(voice.id)
  }

  def listenOnly(voice: Voice4x): Voice4x = {
    modify(voice)(_.listenDirection).setTo(ListenDirection(true))
    modify(voice)(_.talkDirection).setTo(TalkDirection(false))
  }
}

case class Voice4x(
  id: VoiceUserId,
  joined: JoinedVoice = JoinedVoice(false),
  userAgent: UserAgent = UserAgent("None"),
  callerId: CallerId = CallerId(CallerIdName("unknown"), CallerIdNum("unknown")),
  listenDirection: ListenDirection = ListenDirection(false),
  talkDirection: TalkDirection = TalkDirection(false),
  muted: Muted = Muted(true),
  talking: Talking = Talking(false))
