package org.bigbluebutton.common.message

import spray.json.DefaultJsonProtocol

case class Header(name: String, channel: String, replyTo: Option[String]=None)

trait HeaderProtocol {
  this: DefaultJsonProtocol =>

  implicit val headerProtocolFormat = jsonFormat3(Header)
}
