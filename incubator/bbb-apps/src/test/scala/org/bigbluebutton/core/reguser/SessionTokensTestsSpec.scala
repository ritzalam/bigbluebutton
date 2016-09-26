package org.bigbluebutton.core.reguser

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain.SessionToken
import org.bigbluebutton.core.reguser.SessionTokens

class SessionTokensTestsSpec extends UnitSpec {
  it should "add a token" in {
    val sessionTokens = new SessionTokens
    val foo = SessionToken("foo")
    sessionTokens.add(foo)
    assert(sessionTokens.contains(foo))
  }

  it should "remove a token" in {
    val sessionTokens = new SessionTokens
    val foo = SessionToken("foo")
    sessionTokens.add(foo)
    val bar = SessionToken("bar")
    sessionTokens.add(bar)
    assert(sessionTokens.contains(foo))
    assert(sessionTokens.contains(bar))
    sessionTokens.remove(foo)
    assert(sessionTokens.contains(foo) == false)
  }
}
