package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._
import org.bigbluebutton.core2x.api.SessionId

trait ClientUserAgent
case object FlashWebUserAgent extends ClientUserAgent
case object Html5WebUserAgent extends ClientUserAgent

object Client2x {
  def save(presence: Client2x, data: DataApp2x): Client2x = {
    modify(presence)(_.data).setTo(data)
  }

  def save(presence: Client2x, app: Voice4x): Client2x = {
    modify(presence)(_.voice).setTo(app)
  }
}

case class Client2x(
  id: ClientId,
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

