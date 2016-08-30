package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class Presenter {
    public String userID;
    public String userName;
    public String assignedBy;

    public Presenter(String userID, String userName, String assignedBy) {
        this.userID = userID;
        this.userName = userName;
        this.assignedBy = assignedBy;
    }

    public static Presenter fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, Presenter.class);
    }
}
