package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;

public class User {
    public String userID;
    public String username;
    public Boolean hasStream;
    public Boolean listenOnly;
    public EmojiStatus emojiStatus;
    public Boolean phoneUser;
    public Boolean presenter;
    public Boolean locked;
    public String extUserID;
    public Role role;
    public String avatarURL;
    public VoiceUser voiceUser;
    public ArrayList<String> webcamStreams;

    public User(String userID, String username, Boolean hasStream, Boolean listenOnly,
                EmojiStatus emojiStatus, Boolean phoneUser, Boolean presenter, Boolean locked,
                String extUserID, Role role, String avatarURL, VoiceUser voiceUser,
                ArrayList<String> webcamStreams) {
        this.userID = userID;
        this.username = username;
        this.hasStream = hasStream;
        this.listenOnly = listenOnly;
        this.emojiStatus = emojiStatus;
        this.phoneUser = phoneUser;
        this.presenter = presenter;
        this.locked = locked;
        this.extUserID = extUserID;
        this.role = role;
        this.avatarURL = avatarURL;
        this.voiceUser = voiceUser;
        this.webcamStreams = webcamStreams;
    }

    public static User fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, User.class);
    }
}
