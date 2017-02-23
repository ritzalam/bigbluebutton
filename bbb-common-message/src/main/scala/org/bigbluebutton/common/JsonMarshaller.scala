package org.bigbluebutton.common

import org.bigbluebutton.common.message.{CreateMeetingRequestMessageMarshaller, PubSubPingMessageMarshaller}

/**
  * Created by ralam on 2/23/2017.
  */
object JsonMarshaller extends CreateMeetingRequestMessageMarshaller with PubSubPingMessageMarshaller {

}
