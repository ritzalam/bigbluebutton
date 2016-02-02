package org.bigbluebutton

package object live {
  case class WebcamStream(id: String, props: Map[String, String])
  case class User(id: String, name: String, externalId: String, role: Set[String],
    webcamStreams: Set[WebcamStream], authz: Set[String])

}