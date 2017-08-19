package org.bigbluebutton.endpoint.redis

import com.google.gson.Gson
import io.deepstream.DeepstreamClient

class BbbDeepstreamClientSender(server: String) {
  println("***** Logging into Deepstream!")
  val client = new DeepstreamClient(server)
  val loginResult = client.login()

  if (loginResult.loggedIn) {
    println("********** DeepstreamClient login")
  } else {
    throw new Exception("unable to initialise deepstream connection")
  }

  def send(topic: String, json: String): Unit = {
    println("**** Sending to DS " + json)
    client.event.emit("foo-bar", json)
  }
}
