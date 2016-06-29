package org.bigbluebutton.core2x.pubsub.senders

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.core2x.JsonConversionTestFixtures
import org.bigbluebutton.core2x.api.OutGoingMsg.ValidateAuthTokenSuccessReplyOutMsg
import org.bigbluebutton.core2x.domain._
import org.bigbluebutton.messages.{ MessageType, ValidateAuthTokenSuccessMessage }
import org.bigbluebutton.messages.body.MessageHeaderToClient
import org.bigbluebutton.messages.vo.UserInfoBody

class ValidateAuthTokenSuccessReplyOutMessageJsonSenderHelperTests extends UnitSpec with JsonConversionTestFixtures {
  it should "convert roles to string" in {
    object Helper extends ValidateAuthTokenSuccessReplyOutMessageJsonSenderHelper

    val roles: Set[Role2x] = Set(ModeratorRole, ViewerRole)
    val rolesList = Helper.convertRoles(roles)
    assert(rolesList.size() == 2)
    assert(rolesList.contains("moderator"))
    assert(rolesList.contains("viewer"))
  }

  it should "convert dialin numbers" in {
    object Helper extends ValidateAuthTokenSuccessReplyOutMessageJsonSenderHelper

    val num1 = "6135551234"
    val num2 = "18005551234"

    val dialInNumbers: Set[DialNumber] = Set(DialNumber(num1), DialNumber(num2))
    val dialList = Helper.convertDialInNumbers(dialInNumbers)
    assert(dialList.size() == 2)
    assert(dialList.contains(num1))
    assert(dialList.contains(num2))
  }

  it should "convert message" in {
    object Helper extends ValidateAuthTokenSuccessReplyOutMessageJsonSenderHelper

    val outMsg = ValidateAuthTokenSuccessReplyOutMsg(
      IntMeetingId("demomeeting"), IntUserId("userid1"), Name("Juan Tamad"),
      Set(ModeratorRole), ExtUserId("extuserid1"), AuthToken("LetMeIn!"),
      Avatar("http://www.myavatar.com/12345"),
      LogoutUrl("http://www.amoutofhere.com"),
      Welcome("Welcome to my meeting!"),
      Set(DialNumber("6135551234")),
      "PlaceHolder", "PlaceHolder")

    val message: ValidateAuthTokenSuccessMessage = Helper.convert(outMsg)

    //  println(message.toJson)

    val rxMessage = ValidateAuthTokenSuccessMessage.fromJson(message.toJson)
    assert(rxMessage.body.roles.size() == 1)
    assert(rxMessage.body.roles.contains("moderator"))
    assert(rxMessage.body.dialInNumbers != null)
    assert(rxMessage.body.dialInNumbers.contains("6135551234"))
  }
}
