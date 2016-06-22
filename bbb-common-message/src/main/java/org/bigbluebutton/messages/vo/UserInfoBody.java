package org.bigbluebutton.messages.vo;


import java.util.List;

public class UserInfoBody {
    public final String id;
    public final String externalId;
    public final String name;
    public final String authToken;
    public final List<String> roles;
    public final String avatarUrl;
    public final String logoutUrl;
    public final String welcomeMessage;
    public final List<String> dialInNumbers;
    public final String config;
    public final String externalData;

    public UserInfoBody(String id, String externalId, String name, String authToken, List<String> roles,
                        String avatarUrl, String logoutUrl, String welcomeMessage, List<String> dialInNumbers,
                        String config, String externalData) {
        this.id = id;
        this.externalId = externalId;
        this.name = name;
        this.authToken = authToken;
        this.roles = roles;
        this.avatarUrl = avatarUrl;
        this.logoutUrl = logoutUrl;
        this.welcomeMessage = welcomeMessage;
        this.dialInNumbers = dialInNumbers;
        this.config = config;
        this.externalData = externalData;
    }
}
