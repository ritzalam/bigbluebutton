
package org.bigbluebutton

import com.typesafe.config.ConfigFactory
import scala.util.Try

trait SystemConfiguration {

  val config = ConfigFactory.load()

  lazy val imageMagickDir = Try(config.getString("imageMagickDir")).getOrElse("/usr/bin")
  lazy val maxConversionTime = Try(config.getInt("maxConversionTime")).getOrElse(5)
  lazy val BLANK_SLIDE = Try(config.getString("BLANK_SLIDE")).getOrElse("/var/bigbluebutton/blank/blank-slide.swf")
  lazy val svgImagesRequired = Try(config.getBoolean("svgImagesRequired")).getOrElse(false)

  lazy val numConversionThreads = Try(config.getInt("numConversionThreads")).getOrElse(2)
  lazy val maxNumPages = Try(config.getInt("maxNumPages")).getOrElse(200)
  lazy val swfToolsDir = Try(config.getString("swfToolsDir")).getOrElse("/usr/bin")

  lazy val ghostScriptExec = Try(config.getString("ghostScriptExec")).getOrElse("/usr/bin/gs")
  lazy val noPdfMarkWorkaround = Try(config.getString("noPdfMarkWorkaround")).getOrElse("/etc/bigbluebutton/nopdfmark.ps")
  lazy val fontsDir = Try(config.getString("fontsDir")).getOrElse("/usr/share/fonts")
  lazy val placementsThreshold = Try(config.getInt("placementsThreshold")).getOrElse(8000)
  lazy val defineTextThreshold = Try(config.getInt("defineTextThreshold")).getOrElse(2500)
  lazy val imageTagThreshold = Try(config.getInt("imageTagThreshold")).getOrElse(8000)
  lazy val BLANK_THUMBNAIL = Try(config.getString("BLANK_THUMBNAIL")).getOrElse("/var/bigbluebutton/blank/blank-thumb.png")
  lazy val MAX_SWF_FILE_SIZE = Try(config.getInt("MAX_SWF_FILE_SIZE")).getOrElse(500000)

  lazy val redisHost = Try(config.getString("redis.host")).getOrElse("127.0.0.1")
  lazy val redisPort = Try(config.getInt("redis.port")).getOrElse(6379)
  lazy val redisPassword = Try(config.getString("redis.password")).getOrElse("")
  lazy val keysExpiresInSec = Try(config.getInt("redis.keyExpiry")).getOrElse(14 * 86400) // 14 days
}
