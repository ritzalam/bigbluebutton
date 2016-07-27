package org.bigbluebutton.core2x

import org.bigbluebutton.SystemConfiguration
import org.apache.commons.codec.digest.DigestUtils
import org.bigbluebutton.core.api.TimestampGenerator
import org.bigbluebutton.core2x.domain.{ ExtUserId, IntMeetingId, IntUserId, SessionToken }

object TokenIdFactory extends SystemConfiguration {

  def genUserId(meetingId: IntMeetingId, userId: ExtUserId): String = {
    val s = meetingId.value + userId.value + bbbWebSharedSecret
    DigestUtils.sha1Hex(s);
  }

  def genSessionToken(userId: IntUserId): String = {
    val s = userId.value + TimestampGenerator.getCurrentTime() + bbbWebSharedSecret
    DigestUtils.sha1Hex(s);
  }

  def genClientId(sessionToken: SessionToken): String = {
    val s = sessionToken.value + bbbWebSharedSecret
    DigestUtils.sha1Hex(s);
  }
}
