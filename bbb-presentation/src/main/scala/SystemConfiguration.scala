
package org.bigbluebutton

import com.typesafe.config.ConfigFactory
import scala.util.Try

trait SystemConfiguration {

  val config = ConfigFactory.load()

  lazy val imageMagickDir = Try(config.getString("imageMagickDir")).getOrElse("DID NOT FIND") //TODO
  lazy val maxConversionTime = Try(config.getInt("maxConversionTime")).getOrElse(99999) //TODO
  lazy val BLANK_SLIDE = Try(config.getString("BLANK_SLIDE")).getOrElse("DID NOT FIND") //TODO
  lazy val svgImagesRequired = Try(config.getBoolean("svgImagesRequired")).getOrElse(false) //TODO


  lazy val redisHost = Try(config.getString("redis.host")).getOrElse("127.0.0.1")
  lazy val redisPort = Try(config.getInt("redis.port")).getOrElse(6379)
  lazy val redisPassword = Try(config.getString("redis.password")).getOrElse("")
  lazy val keysExpiresInSec = Try(config.getInt("redis.keyExpiry")).getOrElse(14 * 86400) // 14 days
}
