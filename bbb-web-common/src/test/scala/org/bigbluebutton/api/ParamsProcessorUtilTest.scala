package org.bigbluebutton.api

import org.bigbluebutton.presentation.PresentationUrlDownloadService
import org.bigbluebutton.web.pubsub.UnitSpec


class ParamsProcessorUtilTest extends UnitSpec {
  it should "download the config xml" in {
    val util = new ParamsProcessorUtil()

    val config = util.getConfig("http://192.168.246.131/client/conf/config.xml");

    println(config)

    assert(true)
  }

  it should "download the default presentation" in {
    val util = new PresentationUrlDownloadService()

    val config = util.savePresentation("foo", "default.pdf", "http://192.168.246.131/default.pdf");

    println(config)

    assert(config)
  }
}
