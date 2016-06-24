package org.bigbluebutton.messages2x;

import org.bigbluebutton.common.messages2x.PubSubPongMessage2x;
import org.boon.core.Sys;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class PubSubPongMessage2xTest {

    @Test
    public void PubSubPongMessage2x() {
        String system = "abc-system";
        Long timestamp = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());

        PubSubPongMessage2x msg1 = new PubSubPongMessage2x(system, timestamp);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        PubSubPongMessage2x msg2 = PubSubPongMessage2x.fromJson(json1);

        Assert.assertEquals(PubSubPongMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(system, msg2.payload.system);
        Assert.assertEquals(timestamp, msg2.payload.timestamp);
    }

}
