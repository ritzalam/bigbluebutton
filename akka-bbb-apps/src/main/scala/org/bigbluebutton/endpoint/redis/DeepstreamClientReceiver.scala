package org.bigbluebutton.endpoint.redis

import com.fasterxml.jackson.databind.JsonNode
import com.google.gson.Gson
import org.bigbluebutton.core.bus.{ IncomingJsonMessage, IncomingJsonMessageBus, ReceivedJsonMessage }
import io.deepstream.{ DeepstreamClient, EventListener }
import org.bigbluebutton.SystemConfiguration
import org.bigbluebutton.common2.msgs.{ BbbCommonEnvJsNodeMsg, BbbCoreEnvelope, Deserializer, Routing }
import org.bigbluebutton.common2.util.JsonUtil

import scala.util.{ Failure, Success }

class DeepstreamClientReceiver(jsonMsgBus: IncomingJsonMessageBus, server: String) {
  println("***** DeepstreamClientReceiver Logging into Deepstream!")
  val client = new DeepstreamClient(server)
  val loginResult = client.login()

  if (loginResult.loggedIn) {
    println("********** DeepstreamClientReceiver logged in")
  } else {
    throw new Exception("unable to initialise deepstream connection")
  }

  val listener = new MessageListener(jsonMsgBus)

  client.event.subscribe("from-client", listener)
}

class MessageListener(jsonMsgBus: IncomingJsonMessageBus) extends EventListener with SystemConfiguration {

  def handleMsgFromClientMsg(msg: String): Unit = {

    def convertToJsonNode(json: String): Option[JsonNode] = {
      JsonUtil.toJsonNode(json) match {
        case Success(jsonNode) => Some(jsonNode)
        case Failure(ex) =>
          println("Failed to process client message body " + ex)
          None
      }
    }

    object Deserializer extends Deserializer

    val (result, error) = Deserializer.toBbbCoreMessageFromClient(msg)
    result match {
      case Some(msgFromClient) =>
        val routing = Routing.addMsgFromClientRouting(msgFromClient.header.meetingId, msgFromClient.header.userId)
        val envelope = new BbbCoreEnvelope(msgFromClient.header.name, routing)

        if (msgFromClient.header.name == "ClientToServerLatencyTracerMsg") {
          println("-- trace -- " + msg)
        }

        for {
          jsonNode <- convertToJsonNode(msg)
        } yield {
          val akkaMsg = BbbCommonEnvJsNodeMsg(envelope, jsonNode)
          val json = JsonUtil.toJson(akkaMsg)
          val receivedJsonMessage = new ReceivedJsonMessage("none", json)
          // println(s"RECEIVED:\n [${receivedJsonMessage.channel}] \n ${receivedJsonMessage.data} \n")
          jsonMsgBus.publish(IncomingJsonMessage(toAkkaAppsJsonChannel, receivedJsonMessage))
        }

      case None =>
        println("Failed to convert message with error: " + error)
    }
  }

  override def onEvent(s: String, o: scala.Any): Unit = {
    // val gson = new Gson()
    // val json = gson.toJson(o.asInstanceOf[String])
    println("FOOO = " + o.toString)
    //val json = o.asInstanceOf[com.google.gson.JsonObject]

    handleMsgFromClientMsg(o.toString)

  }

}
