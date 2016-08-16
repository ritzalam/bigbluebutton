package org.bigbluebutton.bbbred5apps

import com.typesafe.config.ConfigFactory

/**
  * Created by anton on 16/08/16.
  */
trait SystemConfiguration {

  val config = ConfigFactory.load()

}
