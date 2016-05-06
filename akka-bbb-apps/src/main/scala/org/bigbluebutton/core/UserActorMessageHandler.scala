package org.bigbluebutton.core

import org.bigbluebutton.core.api.ValidateAuthToken
import org.bigbluebutton.core.domain.{ ExtUserId, IntUserId, User3x }

class UserActorMessageHandler {

  def handleValidateAuthToken(msg: ValidateAuthToken): Unit = {

  }
}

case class UserState(
  user: User3x, confPin: Int,
  tokens: Set[String], welcome: String, logoutUrl: String, layout: String)