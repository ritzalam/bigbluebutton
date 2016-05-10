package org.bigbluebutton.core

import org.bigbluebutton.core.api.ValidateAuthToken
import org.bigbluebutton.core.domain.{ User3x }

class UserActorMessageHandler {

  def handleValidateAuthToken(msg: ValidateAuthToken): Unit = {

  }

  def handleMuteUserRequest(): Unit = {

  }
}

case class UserState(
  user: User3x, confPin: Int,
  tokens: Set[String], welcome: String, logoutUrl: String, layout: String)