package org.bigbluebutton.red5.client;


import com.google.gson.*;
import org.bigbluebutton.red5.client.messaging.BroadcastClientMessage;
import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService;
import org.bigbluebutton.red5.client.messaging.DirectClientMessage;

import java.util.HashMap;
import java.util.Map;

public class ClientJsonMessageSender {
    private ConnectionInvokerService service;

    public ClientJsonMessageSender(ConnectionInvokerService service) {
        this.service = service;
    }

    public void handleJSONMessage(String message) {
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(message);

        if (obj.has("header") && obj.has("body")) {
            JsonObject header = (JsonObject) obj.get("header");
            JsonObject body = (JsonObject) obj.get("body");

            if (header.has("messageType")) {

                String messageType = header.get("messageType").getAsString();
                switch (messageType) {
                    case "DIRECT": handleDirectMessage(header, body);
                        break;
                    case "BROADCAST": handleBroadcastMessage(header, body);
                        break;
                    case "SYSTEM": handleSystemMessage(header, body);
                }
            }
        }
    }

    private void handleDirectMessage(JsonObject header, JsonObject body) {
        if (header.has("name") && header.has("meetingId") && header.has("receiverId")) {
            String messageName = header.get("name").getAsString();
            String meetingId = header.get("meetingId").getAsString();
            String receiverId = header.get("receiverId").getAsString();

            JsonObject headerObj = buildMessageHeader(messageName);
            JsonObject jsonObjectMessage = headerAndPayload(headerObj, body);

            Map<String, Object> message = new HashMap<String, Object>();
            Gson gson = new Gson();
            message.put("msg", gson.toJson(jsonObjectMessage));

            DirectClientMessage m = new DirectClientMessage(meetingId, receiverId, messageName, message);
            service.sendMessage(m);
        }
    }

    private void handleBroadcastMessage(JsonObject header, JsonObject body) {
        if (header.has("name") && header.has("meetingId")) {
            String messageName = header.get("name").getAsString();
            String meetingId = header.get("meetingId").getAsString();

            JsonObject headerObj = buildMessageHeader(messageName);
            JsonObject jsonObjectMessage = headerAndPayload(headerObj, body);

            Map<String, Object> message = new HashMap<String, Object>();
            Gson gson = new Gson();
            message.put("msg", gson.toJson(jsonObjectMessage));

            BroadcastClientMessage m = new BroadcastClientMessage(meetingId,
                    messageName, message);
            service.sendMessage(m);
        }
    }

    private void handleSystemMessage(JsonObject header, JsonObject body) {
        if (header.has("name") && header.has("meetingId") && header.has("receiverId")) {
            String messageName = header.get("name").getAsString();
            String meetingId = header.get("meetingId").getAsString();
            String receiverId = header.get("receiverId").getAsString();

            JsonObject headerObj = buildMessageHeader(messageName);
            JsonObject jsonObjectMessage = headerAndPayload(headerObj, body);

            Map<String, Object> message = new HashMap<String, Object>();
            Gson gson = new Gson();
            message.put("msg", gson.toJson(jsonObjectMessage));

           // TODO: Handle system message

        }
    }

    private JsonObject buildMessageHeader(String name) {
        JsonObject header = new JsonObject();
        header.add("name", new JsonPrimitive(name));
        return header;
    }

    private JsonObject headerAndPayload(JsonElement header, JsonElement payload) {
        JsonObject message = new JsonObject();
        message.add("header", header);
        message.add("body", payload);
        return message;
    }
}
