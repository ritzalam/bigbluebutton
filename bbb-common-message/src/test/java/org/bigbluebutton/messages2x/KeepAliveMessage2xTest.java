package org.bigbluebutton.messages2x;

import org.bigbluebutton.common.messages2x.KeepAliveMessage2x;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class KeepAliveMessage2xTest {

    @Test
    public void KeepAliveMessage2x() {
        String keepAliveID = "aKeep-id123";

        KeepAliveMessage2x msg1 = new KeepAliveMessage2x(keepAliveID);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        KeepAliveMessage2x msg2 = KeepAliveMessage2x.fromJson(json1);

        Assert.assertEquals(KeepAliveMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(keepAliveID, msg2.payload.keepAliveID);
    }

}
