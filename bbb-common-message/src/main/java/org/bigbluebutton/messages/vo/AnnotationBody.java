package org.bigbluebutton.messages.vo;

import org.boon.json.JsonFactory;
import org.boon.json.ObjectMapper;

import java.util.Map;

public class AnnotationBody {
    public final String id;
    public final String status;
    public final String shapeType; // TODO make this enum
    public final Map<String, Object> shape;
    public final String wbId;

    public AnnotationBody(String id, String status, String shapeType, Map<String, Object> shape,
                          String wbId) {
        this.id = id;
        this.status = status;
        this.shapeType = shapeType;
        this.shape = shape;
        this.wbId = wbId;
    }

    public static AnnotationBody fromJson(String message) {
        ObjectMapper mapper = JsonFactory.create();

        // TODO will most likely need to write my own de/serializer

        return mapper.readValue(message, AnnotationBody.class);
    }

    public static String toJson (AnnotationBody obj) {
        // TODO will most likely need to write my own de/serializer
        return "";
    }

}
