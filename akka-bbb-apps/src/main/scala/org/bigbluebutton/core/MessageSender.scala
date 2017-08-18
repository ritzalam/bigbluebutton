package org.bigbluebutton.core

import org.bigbluebutton.endpoint.redis.{ BbbDeepstreamClientSender, RedisPublisher }

class MessageSender(publisher: RedisPublisher, dsclient: BbbDeepstreamClientSender) {

  def send(channel: String, data: String) {
    publisher.publish(channel, data)

  }

  def sendToDS(topic: String, data: String): Unit = {
    dsclient.send(topic, data)
  }

}