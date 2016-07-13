package org.bigbluebutton.messages.vo;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PageBody {
    public final String id;
    public final Integer num;
    public final String thumbUrl;
    public final String swfUrl;
    public final String textUrl;
    public final String svgUrl;
    public final Boolean current;

    public PageBody(String id, Integer num, String thumbUrl, String swfUrl, String textUrl,
                String svgUrl, Boolean current) {
        this.id = id;
        this.num = num;
        this.thumbUrl = thumbUrl;
        this.swfUrl = swfUrl;
        this.textUrl = textUrl;
        this.svgUrl = svgUrl;
        this.current = current;
    }

    // TODO do I need a fromJson here?
    public static PageBody fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PageBody.class);
    }
}



