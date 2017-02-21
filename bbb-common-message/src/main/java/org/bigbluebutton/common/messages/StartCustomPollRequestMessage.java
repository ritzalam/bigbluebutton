package org.bigbluebutton.common.messages;

import org.bigbluebutton.common.messages.payload.StartCustomPollRequestMessagePayload;

public class StartCustomPollRequestMessage implements IBigBlueButtonMessage {

	public static final String START_CUSTOM_POLL_REQUEST = "start_custom_poll_request_message";
	
	public MessageHeader header;		
	public StartCustomPollRequestMessagePayload payload;

	public String toJson() {
		// TODO
		return "FIX MEE!!!";
	}

	public String getChannel() {
		// TODO
		return "FIX MEE!!!";
	}
}
