package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core2x.JsonConversionTestFixtures
import org.bigbluebutton.core2x.domain.{ GuestRole, ModeratorRole }
import org.bigbluebutton.messages.RegisterUserRequestMessage
import org.bigbluebutton.messages.vo.UserInfoBody

class RegisterUserRequestMessageJsonHandlerTest extends UnitSpec with JsonConversionTestFixtures {
  it should "extract roles" in {
    object Helper extends RegisterUserRequestMessageJsonHandlerHelper

    val header: RegisterUserRequestMessage.RegisterUserRequestMessageHeader =
      new RegisterUserRequestMessage.RegisterUserRequestMessageHeader(piliIntMeetingId)

    val userInfoBody: UserInfoBody = new UserInfoBody(
      du30IntUserId, du30ExtUserId, du30UserName, du30AuthToken, du30Roles, du30Avatar, du30LogoutUrl,
      du30Welcome, du30DialNums, du30ConfigXml, du30ExternalData)

    val message: RegisterUserRequestMessage = new RegisterUserRequestMessage(header, userInfoBody)
    println(message.toJson)
    val rxMessage = RegisterUserRequestMessage.fromJson(message.toJson)
    val roles = Helper.extractRoles(rxMessage.body)
    assert(roles.toVector.length == 2)
    assert(roles.contains(ModeratorRole))
    assert(roles.contains(GuestRole))
  }

  it should "extract roles with unknown role" in {
    object Helper extends RegisterUserRequestMessageJsonHandlerHelper

    val header: RegisterUserRequestMessage.RegisterUserRequestMessageHeader =
      new RegisterUserRequestMessage.RegisterUserRequestMessageHeader(piliIntMeetingId)

    du30Roles.add("unknown")

    val userInfoBody: UserInfoBody = new UserInfoBody(
      du30IntUserId, du30ExtUserId, du30UserName, du30AuthToken, du30Roles, du30Avatar, du30LogoutUrl,
      du30Welcome, du30DialNums, du30ConfigXml, du30ExternalData)

    val message: RegisterUserRequestMessage = new RegisterUserRequestMessage(header, userInfoBody)
    println(message.toJson)
    val rxMessage = RegisterUserRequestMessage.fromJson(message.toJson)
    val roles = Helper.extractRoles(rxMessage.body)
    assert(roles.toVector.length == 2)
    assert(roles.contains(ModeratorRole))
    assert(roles.contains(GuestRole))
  }

  it should "extract empty dial in numbers" in {
    object Helper extends RegisterUserRequestMessageJsonHandlerHelper

    val header: RegisterUserRequestMessage.RegisterUserRequestMessageHeader =
      new RegisterUserRequestMessage.RegisterUserRequestMessageHeader(piliIntMeetingId)

    val userInfoBody: UserInfoBody = new UserInfoBody(
      du30IntUserId, du30ExtUserId, du30UserName, du30AuthToken, du30Roles, du30Avatar, du30LogoutUrl,
      du30Welcome, du30DialNums, du30ConfigXml, du30ExternalData)

    val message: RegisterUserRequestMessage = new RegisterUserRequestMessage(header, userInfoBody)
    println(message.toJson)
    val rxMessage = RegisterUserRequestMessage.fromJson(message.toJson)
    val dialInNumbers = Helper.extractDialInNumbers(rxMessage.body)
    assert(dialInNumbers.toVector.length == 0)
    assert(dialInNumbers.isEmpty)
  }
}
