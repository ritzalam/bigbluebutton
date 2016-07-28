package org.bigbluebutton.core.api.json.senders

import java.util

import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.core.{ MessageSender, MsgSenderActor2X }
import org.bigbluebutton.core.api.OutGoingMsg.ValidateAuthTokenSuccessReplyOutMsg
import org.bigbluebutton.core.domain.{ ConvertRoleHelper, DialNumber, Role2x }
import org.bigbluebutton.core.MessageSender
import org.bigbluebutton.messages.body.MessageHeaderToClient
import org.bigbluebutton.messages.vo.UserInfoBody
import org.bigbluebutton.messages.{ MessageType, ValidateAuthTokenSuccessMessage }

trait ValidateAuthTokenSuccessReplyOutMsgJsonSender
    extends ValidateAuthTokenSuccessReplyOutMsgJsonSenderHelper {
  this: MsgSenderActor2X =>

  val service: MessageSender

  def handleValidateAuthTokenSuccessReplyOutMessage(msg: ValidateAuthTokenSuccessReplyOutMsg) {
    log.debug("**** handleValidateAuthTokenReply *****")

    val message: ValidateAuthTokenSuccessMessage = convert(msg)
    val json = message.toJson
    log.debug(json)
    service.send(MessagingConstants.FROM_USERS_CHANNEL, json)
  }
}

trait ValidateAuthTokenSuccessReplyOutMsgJsonSenderHelper {

  def convert(msg: ValidateAuthTokenSuccessReplyOutMsg): ValidateAuthTokenSuccessMessage = {
    val rolesList = convertRoles(msg.roles)
    val dialInNumbers = convertDialInNumbers(msg.dialNumbers)
    val userInfoBody = new UserInfoBody(msg.userId.value,
      msg.extUserId.value, msg.name.value, msg.authToken.value,
      rolesList, msg.avatar.value, msg.logoutUrl.value,
      msg.welcome.value, dialInNumbers,
      msg.config, msg.extData)

    val header = new MessageHeaderToClient(ValidateAuthTokenSuccessMessage.NAME,
      msg.meetingId.value, msg.userId.value, MessageType.DIRECT)

    val message: ValidateAuthTokenSuccessMessage = new ValidateAuthTokenSuccessMessage(header, userInfoBody)

    message
  }

  def convertRoles(roles: Set[Role2x]): util.List[String] = {
    import scala.collection.convert.wrapAsJava._

    // Convert roles to strings
    val rolesOptions = roles.map(b => ConvertRoleHelper.convert(b))
    // Unwrap the Options and None
    // (see http://stackoverflow.com/questions/10104558/how-to-filter-nones-out-of-listoption)
    val rolesSet = rolesOptions.flatten
    // Convert to java List
    seqAsJavaList(rolesSet.toSeq)
  }

  def convertDialInNumbers(dialInNumbers: Set[DialNumber]): util.List[String] = {
    import scala.collection.convert.wrapAsJava._

    // Convert to strings
    val dialIns = dialInNumbers.map(n => n.value)
    // Convert to java List
    seqAsJavaList(dialIns.toSeq)
  }
}