package org.bigbluebutton.messages2x;

import org.bigbluebutton.common.messages2x.PubSubPingMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class PubSubPingMessage2xTest {

    @Test
    public void PubSubPingMessage2x() {
        String system = "abc-system";
        Long timestamp = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

        PubSubPingMessage2x msg1 = new PubSubPingMessage2x(system, timestamp);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        PubSubPingMessage2x msg2 = PubSubPingMessage2x.fromJson(json1);

        Assert.assertEquals(PubSubPingMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(system, msg2.payload.system);
        Assert.assertEquals(timestamp, msg2.payload.timestamp);
    }

}
