package org.bigbluebutton.core2x.bus.handlers

import org.bigbluebutton.core.UnitSpec
import org.bigbluebutton.messages.ValidateAuthTokenRequestMessage
import org.bigbluebutton.messages.body.MessageHeader


class ValidateAuthTokenRequestMessageJsonHandlerTest extends UnitSpec {
  it should "parse msg" in {
    object DefPerm extends ValidateAuthTokenRequestMessageJsonHandler
    val json = "{\"header\":{\"name\":\"BbbPubSubPongMessage\"}"

  }

}
