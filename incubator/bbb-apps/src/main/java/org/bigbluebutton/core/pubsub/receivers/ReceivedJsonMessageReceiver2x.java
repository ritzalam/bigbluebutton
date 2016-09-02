package org.bigbluebutton.core.pubsub.receivers;


import org.bigbluebutton.core.api.IBigBlueButtonInGW;
import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;
import java.util.Map;

public class ReceivedJsonMessageReceiver2x implements MessageHandler {
    private IBigBlueButtonInGW bbbGW;

    public ReceivedJsonMessageReceiver2x(IBigBlueButtonInGW bbbGW) {
        this.bbbGW = bbbGW;
    }

    @Override
    public void handleMessage(String pattern, String channel, String json) {
        ObjectMapper mapper = JsonFactory.create();
        Map msg = mapper.readValue(json, Map.class);
        Map header = (Map) msg.get("header");
        if (header != null && header.containsKey("name")) {
            String msgName = (String) header.get("name");
            if (msgName != null && !msgName.isEmpty()) {
                bbbGW.handleReceivedJsonMessage(msgName, json);
                return;
            }
        }

        System.out.println("Cannot handle message \n" + json + "\n");

    }
}
