package org.bigbluebutton.bbbred5apps

import com.typesafe.config.ConfigFactory

trait SystemConfiguration {

  val config = ConfigFactory.load()

}
