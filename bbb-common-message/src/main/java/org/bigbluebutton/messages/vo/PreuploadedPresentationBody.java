package org.bigbluebutton.messages.vo;


import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PreuploadedPresentationBody {
    public final String presentationId;
    public final String presentationName;
    public final Boolean defaultPres;

    public PreuploadedPresentationBody(String presentationId, String presentationName,
                                     Boolean defaultPres) {
        this.presentationId = presentationId;
        this.presentationName = presentationName;
        this.defaultPres = defaultPres;
    }

    // TODO do I need a fromJson here?
    public static PreuploadedPresentationBody fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PreuploadedPresentationBody.class);
    }
}
