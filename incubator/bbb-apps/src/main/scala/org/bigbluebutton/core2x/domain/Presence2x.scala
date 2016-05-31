package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._

trait PresenceUserAgent
case object FlashWebUserAgent extends PresenceUserAgent
case object Html5WebUserAgent extends PresenceUserAgent

object Presence2x {
  def save(presence: Presence2x, data: DataApp2x): Presence2x = {
    modify(presence)(_.data).setTo(data)
  }

  def save(presence: Presence2x, app: Voice4x): Presence2x = {
    modify(presence)(_.voice).setTo(app)
  }
}

case class Presence2x(
  id: PresenceId,
  userAgent: UserAgent,
  sessions: Set[SessionId],
  data: DataApp2x,
  voice: Voice4x,
  webCams: WebCamStreams,
  screenShare: ScreenShareStreams)

object DataApp2x {
  def update(data: DataApp2x, session: SessionId): DataApp2x = {
    modify(data)(_.sessionId).setTo(session)
  }
}

case class DataApp2x(sessionId: SessionId)
case class WebCamStreams(streams: Set[Stream])
case class VoiceApp2x(sessionId: SessionId, voice: Voice4x)
case class ScreenShareStreams(streams: Set[Stream])

