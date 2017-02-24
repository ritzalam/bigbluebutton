package org.bigbluebutton.common

import org.bigbluebutton.common.message.{BbbMsg, CreateMeetingRequestMessageMarshaller, PubSubPingMessageMarshaller}

object JsonMarshaller extends CreateMeetingRequestMessageMarshaller with PubSubPingMessageMarshaller {

}
