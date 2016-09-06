package org.bigbluebutton.endpoint.redis.json.handlers

import org.bigbluebutton.endpoint.redis.json.{ReceivedJsonMessage, UnhandledJsonMsgHdlr}
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg

trait ValidateAuthTokenClientHandler { // extends UnhandledJsonMsgHdlr {

  def handleReceivedJsonMsg(msg: Red5InJsonMsg): Unit = {
      // send to Connection Manager
  }
}
