package org.bigbluebutton.core.apps.reguser

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain.SessionToken

class SessionTokensTestsSpec extends UnitSpec {
  it should "add a token" in {
    val sessionTokens = new SessionTokens
    val foo = SessionToken("foo")
    sessionTokens.add(foo)
    println(sessionTokens.tokens())
    assert(sessionTokens.contains(foo))
  }

  it should "remove a token" in {
    val sessionTokens = new SessionTokens
    val foo = SessionToken("foo")
    sessionTokens.add(foo)
    val bar = SessionToken("bar")
    sessionTokens.add(bar)
    println(sessionTokens.tokens())
    assert(sessionTokens.contains(foo))
    assert(sessionTokens.contains(bar))
    sessionTokens.remove(foo)
    assert(sessionTokens.contains(foo) == false)
  }
}
