package org.bigbluebutton.core.apps.reguser

import org.bigbluebutton.core.domain.SessionToken

class SessionTokens {

  private var sessionTokens: Set[SessionToken] = Set.empty

  def add(sessionToken: SessionToken): Unit = {
    sessionTokens = sessionTokens + sessionToken
  }

  def remove(sessionToken: SessionToken): Unit = {
    sessionTokens = sessionTokens - sessionToken
  }

  def contains(sessionToken: SessionToken): Boolean = {
    sessionTokens.contains(sessionToken)
  }

  def tokens(): Set[SessionToken] = {
    sessionTokens
  }

}
