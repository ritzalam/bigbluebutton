package org.bigbluebutton.common.messages;

import com.google.gson.Gson;
import org.bigbluebutton.messages.Header;

public class PubSubPongMessage implements IBigBlueButtonMessage {

	public static final String NAME = "BbbPubSubPongMessage";
	
	public Header header;
	public PubSubPongMessagePayload payload;

	public PubSubPongMessage(PubSubPongMessagePayload payload) {
		this.header = new Header(NAME);
		this.payload = payload;
	}

	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson(this);
	}

	public String getChannel() {
		return ChannelConstants.TO_SYSTEM_CHANNEL;
	}

	public static class PubSubPongMessagePayload {
		public final String system;
		public final Long timestamp;

		public PubSubPongMessagePayload(String system, Long timestamp) {
			this.system = system;
			this.timestamp = timestamp;
		}
	}
}
