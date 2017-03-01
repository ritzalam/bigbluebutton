package org.bigbluebutton.common

import org.bigbluebutton.common.message.{CreateMeetingRequestMessageMarshaller, PubSubPingMessageMarshaller, PubSubPongMessageMarshaller}

object JsonMarshaller
  extends CreateMeetingRequestMessageMarshaller
    with PubSubPingMessageMarshaller {

}
