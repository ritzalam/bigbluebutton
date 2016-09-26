package org.bigbluebutton.core.apps.webcam

import org.bigbluebutton.core.domain.IntUserId

case class WebcamStream(id: String, viewers: Set[IntUserId], publisher: IntUserId, url: String)