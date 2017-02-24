package org.bigbluebutton.common.messages;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class UserVoiceMutedMessage implements IBigBlueButtonMessage {
	public static final String USER_VOICE_MUTED  = "user_voice_muted_message";
	public static final String VERSION = "0.0.1";
	
	public final String meetingId;
	public final Map<String, Object> user;
	public final String voiceConf;
	
	public UserVoiceMutedMessage(String meetingId, Map<String, Object> user, String voiceConf) {
		this.meetingId = meetingId;
		this.user = user;
		this.voiceConf = voiceConf;
	}
	
	public String toJson() {
		HashMap<String, Object> payload = new HashMap<String, Object>();
		payload.put(MessageBodyConstants.MEETING_ID, meetingId);
		payload.put(MessageBodyConstants.USER, user);
		payload.put(MessageBodyConstants.VOICE_CONF, voiceConf);
		
		java.util.HashMap<String, Object> header = MessageBuilder.buildHeader(USER_VOICE_MUTED, VERSION, null);

		return MessageBuilder.buildJson(header, payload);				
	}

	public String getChannel() {
		// TODO
		return "FIX MEE!!!";
	}

	public static UserVoiceMutedMessage fromJson(String message) {
		JsonParser parser = new JsonParser();
		JsonObject obj = (JsonObject) parser.parse(message);
		
		if (obj.has("header") && obj.has("payload")) {
			JsonObject header = (JsonObject) obj.get("header");
			JsonObject payload = (JsonObject) obj.get("payload");
			
			if (header.has("name")) {
				String messageName = header.get("name").getAsString();
				if (USER_VOICE_MUTED.equals(messageName)) {
					if (payload.has(MessageBodyConstants.MEETING_ID)
							&& payload.has(MessageBodyConstants.USER)) {
						String id = payload.get(MessageBodyConstants.MEETING_ID).getAsString();
						String voiceConf = payload.get(MessageBodyConstants.VOICE_CONF).getAsString();
						
						JsonObject user = (JsonObject) payload.get(MessageBodyConstants.USER);
						
						Util util = new Util();
						Map<String, Object> userMap = util.extractUser(user);
						
						if (userMap != null) {
							return new UserVoiceMutedMessage(id, userMap, voiceConf);						
						}				
					}
				} 
			}
		}
		return null;

	}
}
