package org.bigbluebutton.web

import akka.actor.{ ActorSystem, Props }

class BbbWebActorSystem {

  implicit val system = ActorSystem("bbb-web-system")

}
