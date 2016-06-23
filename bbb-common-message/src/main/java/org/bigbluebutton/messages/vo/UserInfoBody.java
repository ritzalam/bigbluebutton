package org.bigbluebutton.messages.vo;


import org.boon.json.annotations.JsonInclude;

import java.util.ArrayList;
import java.util.List;

public class UserInfoBody {
    public final String id;
    public final String externalId;
    public final String name;
    public final String authToken;
    public final String avatarUrl;
    public final String logoutUrl;
    public final String welcomeMessage;
    public final String config;
    public final String externalData;

    public final List<String> roles;
    @JsonInclude
    public List<String> dialInNumbers = new ArrayList<String>();

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
