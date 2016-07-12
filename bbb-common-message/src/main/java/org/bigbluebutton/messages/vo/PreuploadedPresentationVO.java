package org.bigbluebutton.messages.vo;


import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PreuploadedPresentationVO {
    public final String presentationId;
    public final String presentationName;
    public final Boolean defaultPres;

    public PreuploadedPresentationVO(String presentationId, String presentationName,
                                     Boolean defaultPres) {
        this.presentationId = presentationId;
        this.presentationName = presentationName;
        this.defaultPres = defaultPres;
    }

    // TODO do I need a fromJson here?
    public static PreuploadedPresentationVO fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PreuploadedPresentationVO.class);
    }
}
