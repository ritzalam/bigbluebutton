package org.bigbluebutton.red5apps

import org.bigbluebutton.bbbred5apps.SystemConfiguration
import org.bigbluebutton.{IRed5InGW, Red5OutGateway}
import akka.actor.ActorSystem
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg

class InGateway(val red5OutGW: Red5OutGateway) extends IRed5InGW with SystemConfiguration {

  println(" ****************** Hello!!!!!!!!!!!!!!!!!")

  def handle(msg: Red5InJsonMsg): Unit = {
    println("\n\n "  + msg.name + " \n\n")

  }

  implicit val system = ActorSystem("red5-bbb-apps-system")

  println("*************** meetingManagerChannel " + meetingManagerChannel + " *******************")
}
