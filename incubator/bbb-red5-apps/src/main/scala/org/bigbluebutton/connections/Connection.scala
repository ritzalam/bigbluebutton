package org.bigbluebutton.connections

import akka.actor.{Actor, ActorLogging, Props}
import com.google.gson.{Gson, JsonObject, JsonParser}
import org.bigbluebutton.bus.{FromClientMsg, Red5AppsMsgBus}
import org.bigbluebutton.common.messages.MessagingConstants
import org.bigbluebutton.endpoint.redis.RedisPublisher

object Connection {
  def props(bus: Red5AppsMsgBus, redisPublisher: RedisPublisher, sessionToken: String,
            connectionId: String): Props =
    Props(classOf[Connection], bus, redisPublisher, sessionToken, connectionId)
}


class Connection(bus: Red5AppsMsgBus, redisPublisher: RedisPublisher , sessionToken: String,
                 connectionId: String) extends Actor with ActorLogging {
  log.warning(s"Creating a new Connection: sessionToken=$sessionToken connectionId=$connectionId")


  override def preStart(): Unit = {
    bus.subscribe(self, sessionToken)
    super.preStart()
  }

  override def postStop(): Unit = {
    bus.unsubscribe(self, sessionToken)
    super.postStop()
  }

  def receive = {
    case msg: FromClientMsg => {
      msg.name match {
        case "ValidateAuthTokenRequestMessage" => handleValidateAuthTokenRequest(msg)
        case "ClientConnected"                 => handleClientConnected(msg)
        case "ClientDisconnected"              => handleClientDisconnected(msg)
        case _                                 => handleTransitMessage(msg)
      }
    }

    case msg: Any => log.warning(s"ConnectionActor[$sessionToken] Unknown message " + msg)
  }


  private def handleValidateAuthTokenRequest(msg: FromClientMsg): Unit = {

    val json = addReplyChannelToJsonMessage(msg.json)
    log.info(s"ValidateAuthToken [$json]")

   // send to pubsub with replychannel
    redisPublisher.publish(MessagingConstants.TO_MEETING_CHANNEL, json)
  }

  private def handleClientConnected(msg: FromClientMsg): Unit = {
    log.info(s"_____handleClientConnected___${msg.sessionToken}  ${msg.connectionId}")
  }

  private def handleClientDisconnected(msg: FromClientMsg): Unit = {
    log.info(s"_____handleClientDisconnected___${msg.sessionToken}  ${msg.connectionId}")
  }

  private def handleTransitMessage(msg: FromClientMsg): Unit = {
    redisPublisher.publish(MessagingConstants.FROM_BBB_APPS_PATTERN, msg.json)
  }

  private def addReplyChannelToJsonMessage(message: String): String = {
    val parser: JsonParser = new JsonParser
    val obj: JsonObject = parser.parse(message).asInstanceOf[JsonObject]
    var json = ""

    if (obj.has("header") && obj.has("body")) {
      val header: JsonObject = obj.get("header").getAsJsonObject
      val body: JsonObject = obj.get("body").getAsJsonObject
      header.addProperty("returnChannel", sessionToken)

      val res: JsonObject = new JsonObject
      res.add("body", body)
      res.add("header", header)
      val gson = new Gson()
      json = gson.toJson(res)
    }
    json
  }

}
