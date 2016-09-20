package org.bigbluebutton.core.meeting.models

import org.bigbluebutton.core.apps.reguser.RegisteredUsersModel
import org.bigbluebutton.core.apps.user.UsersModel
import org.bigbluebutton.core.{ MeetingTestFixtures, UnitSpec }

class RegisteredUsersTests extends UnitSpec with MeetingTestFixtures {

  it should "add a registered user" in {
    val testRegUsers = new RegisteredUsersModel
    testRegUsers.add(richardRegisteredUser)
    testRegUsers.add(fredRegisteredUser)
    testRegUsers.add(antonRegisteredUser)

    assert(testRegUsers.toVector.length == 3)
  }

  it should "find a registered user using token" in {
    val testRegUsers = new RegisteredUsersModel
    testRegUsers.add(richardRegisteredUser)
    testRegUsers.add(fredRegisteredUser)
    testRegUsers.add(antonRegisteredUser)

    assert(testRegUsers.toVector.length == 3)

    RegisteredUsersModel.findWithToken(richardRegisteredUser.authToken, testRegUsers.toVector) match {
      case Some(u) => assert(u.id == richardRegisteredUser.id)
      case None => fail("Failed to find user.")
    }
  }

  it should "find a registered user using id" in {
    val testRegUsers = new RegisteredUsersModel
    testRegUsers.add(richardRegisteredUser)
    testRegUsers.add(fredRegisteredUser)
    testRegUsers.add(antonRegisteredUser)

    assert(testRegUsers.toVector.length == 3)

    RegisteredUsersModel.findWithUserId(richardRegisteredUser.id, testRegUsers.toVector) match {
      case Some(u) => assert(u.id == richardRegisteredUser.id)
      case None => fail("Failed to find user.")
    }
  }

  it should "remove a registered user using id" in {
    val testRegUsers = new RegisteredUsersModel
    testRegUsers.add(richardRegisteredUser)
    testRegUsers.add(fredRegisteredUser)
    testRegUsers.add(antonRegisteredUser)

    assert(testRegUsers.toVector.length == 3)

    val regUser4 = testRegUsers.remove(richardRegisteredUser.id) match {
      case Some(u) => assert(u.id == richardRegisteredUser.id)
      case None => fail("Failed to find user.")
    }

    assert(testRegUsers.toVector.length == 2)
  }

  // ====================================
  it should "add a user" in {
    val users = new UsersModel
    users.save(richardUser)
    users.save(fredUser)
    users.save(antonUser)

    assert(users.toVector.length == 3)
  }

  it should "find a user using id" in {
    val users = new UsersModel
    users.save(richardUser)
    users.save(fredUser)
    users.save(antonUser)

    UsersModel.findWithId(richardRegisteredUser.id, users.toVector) match {
      case Some(u) => assert(u.id == richardRegisteredUser.id)
      case None => fail("Failed to find user.")
    }
  }

  it should "find a user using external id" in {
    val users = new UsersModel
    users.save(richardUser)
    users.save(fredUser)
    users.save(antonUser)

    UsersModel.findWithExtId(richardRegisteredUser.extId, users.toVector) match {
      case Some(u) => assert(u.id == richardRegisteredUser.id)
      case None => fail("Failed to find user.")
    }
  }

  it should "remove a user using id" in {
    val users = new UsersModel
    users.save(richardUser)
    users.save(fredUser)
    users.save(antonUser)

    users.remove(richardRegisteredUser.id) match {
      case Some(u) => assert(u.id == richardRegisteredUser.id)
      case None => fail("Failed to find user.")
    }

    assert(users.toVector.length == 2)
  }

  it should "have one user with moderator role" in {
    val users = new UsersModel
    users.save(richardUser)
    users.save(fredUser)
    users.save(antonUser)

    val mods = UsersModel.findModerators(users.toVector)
    assert(mods.length == 1)
  }

  it should "have one user with presenter role" in {
    val users = new UsersModel
    users.save(richardUser)
    users.save(fredUser)
    users.save(antonUser)

    val pres = UsersModel.findPresenters(users.toVector)
    assert(pres.length == 1)
  }

}