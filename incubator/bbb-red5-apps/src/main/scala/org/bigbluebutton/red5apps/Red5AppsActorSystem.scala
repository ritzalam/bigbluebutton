package org.bigbluebutton.red5apps

import org.bigbluebutton.bbbred5apps.SystemConfiguration
import org.bigbluebutton.{IRed5InGW, IRed5InMsg, Red5OutGateway}
import akka.actor.ActorSystem

class Red5AppsActorSystem(val red5OutGW: Red5OutGateway) extends IRed5InGW with SystemConfiguration {

  println(" ****************** Hello!!!!!!!!!!!!!!!!!")

  def handle(msg: IRed5InMsg): Unit = {

  }

  implicit val system = ActorSystem("red5-bbb-apps-system")

  println("*************** meetingManagerChannel " + meetingManagerChannel + " *******************")
}
