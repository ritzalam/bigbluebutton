package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._
import org.bigbluebutton.core2x.api.SessionId

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

/**
 * Borrow some ideas from SDP.
 * https://en.wikipedia.org/wiki/Session_Description_Protocol
 */
case class MediaAttribute(key: String, value: String)
case class Stream(id: String, sessionId: SessionId, attributes: Set[MediaAttribute], viewers: Set[IntUserId])
