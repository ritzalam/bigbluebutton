package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._

object Stream {

  def add(stream: Stream, user: IntUserId): Stream = {
    val newViewers = stream.viewers + user
    modify(stream)(_.viewers).setTo(newViewers)
  }

  def remove(stream: Stream, user: IntUserId): Stream = {
    val newViewers = stream.viewers - user
    modify(stream)(_.viewers).setTo(newViewers)
  }
}

case class MediaAttribute(key: String, value: String)
case class Stream(id: String, sessionId: SessionId, attributes: Set[MediaAttribute], viewers: Set[IntUserId])
