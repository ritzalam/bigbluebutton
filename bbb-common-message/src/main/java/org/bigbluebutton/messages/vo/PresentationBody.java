package org.bigbluebutton.messages.vo;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class PresentationBody {
    public final String id;
    public final String name;
    public final Boolean current;
    public List<PageBody> pages = new ArrayList<PageBody>();
    public final Boolean defaultPres;

    public PresentationBody(String id, String name, Boolean current, List<PageBody> pages, Boolean
            defaultPres) {
        this.id = id;
        this.name = name;
        this.current = current;
        this.pages = pages;
        this.defaultPres = defaultPres;
    }

    // TODO do I need a fromJson here?
    public static PresentationBody fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationBody.class);
    }

}
