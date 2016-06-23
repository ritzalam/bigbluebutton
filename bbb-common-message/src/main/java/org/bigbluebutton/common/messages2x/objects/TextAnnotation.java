package org.bigbluebutton.common.messages2x.objects;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

public class TextAnnotation {

    public String text;
    public Double textBoxHeight;
    public Double textBoxWidth;
    public Integer fontColor;
    public Integer fontSize;
    public Double x;
    public Double y;
    public Double calcedFontSize;
    public String dataPoints; // why String? TODO
    public AnnotationStatus status;
    public String shapeID;
    public final ShapeType shapeType = ShapeType.TEXT;

    public TextAnnotation(String text, Double textBoxHeight, Double textBoxWidth, Integer
            fontColor, Integer fontSize, Double x, Double y, Double calcedFontSize, String
            dataPoints, AnnotationStatus status, String shapeID) {
        this.text = text;
        this.textBoxHeight = textBoxHeight;
        this.textBoxWidth = textBoxWidth;
        this.fontColor = fontColor;
        this.fontSize = fontSize;
        this.x = x;
        this.y = y;
        this.calcedFontSize = calcedFontSize;
        this.dataPoints = dataPoints;
        this.status = status;
        this.shapeID = shapeID;
    }

    public static TextAnnotation fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();
        return mapper.readValue(message, TextAnnotation.class);
    }
}
