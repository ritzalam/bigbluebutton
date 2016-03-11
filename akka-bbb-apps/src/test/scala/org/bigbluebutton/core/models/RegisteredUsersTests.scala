package org.bigbluebutton.core.models

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core.domain._

class RegisteredUsersTests extends UnitSpec {
  val ru1 = RegisteredUser(IntUserId("u1"), ExtUserId("eu1"), Name("Rody"), Set(Role.MODERATOR), AuthToken("au1"))
  val ru2 = RegisteredUser(IntUserId("u2"), ExtUserId("eu2"), Name("Grace"), Set(Role.MODERATOR), AuthToken("au2"))
  val ru3 = RegisteredUser(IntUserId("u3"), ExtUserId("eu3"), Name("Mar"), Set(Role.MODERATOR), AuthToken("au3"))

  it should "add a registered user" in {
    object RegisteredUsers extends RegisteredUsers
    val rusers = RegisteredUsers

    val regUser1 = rusers.addRegisteredUser(ru1.authToken, ru1)
    val regUser2 = rusers.addRegisteredUser(ru2.authToken, ru2)
    val regUser3 = rusers.addRegisteredUser(ru3.authToken, ru3)

    assert(regUser3.length == 3)
  }

  it should "find a registered user using token" in {
    object RegisteredUsers extends RegisteredUsers
    val rusers = RegisteredUsers

    val regUser1 = rusers.addRegisteredUser(ru1.authToken, ru1)
    val regUser2 = rusers.addRegisteredUser(ru2.authToken, ru2)
    val regUser3 = rusers.addRegisteredUser(ru3.authToken, ru3)

    val u1 = rusers.findWithToken(ru3.authToken)
    assert(u1.get.id == ru3.id)
  }

  it should "find a registered user using id" in {
    object RegisteredUsers extends RegisteredUsers
    val rusers = RegisteredUsers

    val regUser1 = rusers.addRegisteredUser(ru1.authToken, ru1)
    val regUser2 = rusers.addRegisteredUser(ru2.authToken, ru2)
    val regUser3 = rusers.addRegisteredUser(ru3.authToken, ru3)

    val u1 = rusers.findWithUserId(ru3.id)
    assert(u1.get.id == ru3.id)
  }

  it should "remove a registered user using id" in {
    object RegisteredUsers extends RegisteredUsers
    val rusers = RegisteredUsers

    val regUser1 = rusers.addRegisteredUser(ru1.authToken, ru1)
    val regUser2 = rusers.addRegisteredUser(ru2.authToken, ru2)
    val regUser3 = rusers.addRegisteredUser(ru3.authToken, ru3)

    val regUser4 = rusers.removeRegisteredUser(ru3.id)
    assert(regUser4.get.id == ru3.id)
    assert(rusers.toArray.length == 2)
  }
}