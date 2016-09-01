package org.bigbluebutton.api.messaging.messages;

import java.util.List;

public class RegisterUser implements IMessage {

	public final String meetingID;
	public final String internalUserId;
	public final String fullname;
	public final List<String> roles;
	public final String externUserID;
	public final String authToken;
	public final String avatarURL;
	public final String logoutUrl;
	public final String welcomeMessage;
	public final List<String> dialInNumbers;
	public final String configXml;
	public final String externalData;
	public final String sessionToken;
	
	public RegisterUser(String meetingID, String internalUserId, String fullname, List<String> roles,
						String externUserID, String authToken, String avatarURL,
						String logourUrl, String welcomeMessage, List<String> dialInNumbers,
						String configXml, String externalData, String sessionToken) {
		this.meetingID = meetingID;
		this.internalUserId = internalUserId;
		this.fullname = fullname;
		this.roles = roles;
		this.externUserID = externUserID;
		this.authToken = authToken;		
		this.avatarURL = avatarURL;
		this.logoutUrl = logourUrl;
		this.welcomeMessage = welcomeMessage;
		this.dialInNumbers = dialInNumbers;
		this.configXml = configXml;
		this.externalData = externalData;
		this.sessionToken = sessionToken;

	}
}
