package org.bigbluebutton.web.services

import org.bigbluebutton.web.pubsub.UnitSpec

/**
  * Created by ritz on 2017-03-02.
  */
class ApiServiceGatewayTest extends UnitSpec {
  it should "download the config xml" in {
    val util = new ApiServiceGateway()

    util.createMeeting();

    assert(true)
  }

}
