package org.bigbluebutton.core2x.domain

case class WebcamStream(id: String, viewers: Set[IntUserId], publisher: IntUserId, url: String)