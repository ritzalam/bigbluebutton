
package org.bigbluebutton

import com.typesafe.config.ConfigFactory
import scala.util.Try

trait SystemConfiguration {

  val config = ConfigFactory.load()

  lazy val imageMagicDir = Try(config.getString("imageMagic.dir")).getOrElse("DID NOT FIND")
}
