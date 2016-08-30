package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class Presentation {
    public Boolean current;
    public PresentationPage[] pages;
    public String name;
    public String id;

    public Presentation(Boolean current, PresentationPage[] pages, String name, String id) {
        this.current = current;
        this.pages = pages;
        this.name = name;
        this.id = id;
    }

    public static Presentation fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, Presentation.class);
    }
}
