package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class PresentationPage {
    public Integer widthRatio;
    public Integer heightRatio;
    public String txtURI;
    public Integer num;
    public Integer yOffset;
    public String swfURI;
    public String thumbURI;
    public Integer xOffset;
    public Boolean current;
    public String svgURI;
    public String id;

    public PresentationPage (Integer widthRatio, Integer heightRatio, String txtURI, Integer num,
                             Integer yOffset, String swfURI, String thumbURI, Integer xOffset,
                             Boolean current, String svgURI, String id) {
        this.widthRatio = widthRatio;
        this.heightRatio = heightRatio;
        this.txtURI = txtURI;
        this.num = num;
        this.yOffset = yOffset;
        this.swfURI = swfURI;
        this.thumbURI = thumbURI;
        this.xOffset = xOffset;
        this.current = current;
        this.svgURI = svgURI;
        this.id = id;
    }

    public static PresentationPage fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, PresentationPage.class);
    }
}
