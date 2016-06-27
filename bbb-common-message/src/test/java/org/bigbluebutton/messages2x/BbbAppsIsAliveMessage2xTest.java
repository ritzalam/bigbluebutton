package org.bigbluebutton.messages2x;

import org.bigbluebutton.common.messages2x.BbbAppsIsAliveMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class BbbAppsIsAliveMessage2xTest {

    @Test
    public void BbbAppsIsAliveMessage2x() {
        Long timestamp = TimeUnit.NANOSECONDS.toMillis(System.nanoTime());
        Long startedOn = timestamp - 200000; // example

        BbbAppsIsAliveMessage2x msg1 = new BbbAppsIsAliveMessage2x(startedOn, timestamp);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        BbbAppsIsAliveMessage2x msg2 = BbbAppsIsAliveMessage2x.fromJson(json1);

        Assert.assertEquals(BbbAppsIsAliveMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(startedOn, msg2.payload.startedOn);
        Assert.assertEquals(timestamp, msg2.payload.timestamp);
    }

}
