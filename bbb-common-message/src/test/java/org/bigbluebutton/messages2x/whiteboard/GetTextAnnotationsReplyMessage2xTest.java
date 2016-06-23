package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.objects.*;
import org.bigbluebutton.common.messages2x.whiteboard.GetTextAnnotationsReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetTextAnnotationsReplyMessage2xTest {

    @Test
    public void GetTextAnnotationsReplyMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "wbd123";

        // text 1
        String text = "KKKKKKKK\r";
        Double textBoxHeight = 2.747678;
        Double textBoxWidth = 11.747968;
        Integer fontColor = 0;
        Integer fontSize = 18;
        Double x = 84.52381;
        Double y = 41.795666;
        Double calcedFontSize = 2.7863777;
        String dataPoints = "84.52381,41.795666"; // why String? TODO
        AnnotationStatus status = AnnotationStatus.DRAW_END;
        String annotationIDText = "bla_shape_132";

        TextAnnotation textAnnotation = new TextAnnotation(text, textBoxHeight, textBoxWidth, fontColor,
                fontSize, x, y, calcedFontSize, dataPoints, status, annotationIDText);



        TextAnnotation[] arr = new TextAnnotation[] { textAnnotation, textAnnotation };

        GetTextAnnotationsReplyMessage2x msg1 = new GetTextAnnotationsReplyMessage2x(meetingID,
                requesterID, whiteboardID, arr);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetTextAnnotationsReplyMessage2x msg2 = GetTextAnnotationsReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetTextAnnotationsReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);

        Assert.assertEquals(text, msg2.payload.annotations[0].text);
        Assert.assertEquals(textBoxHeight, msg2.payload.annotations[0].textBoxHeight);
        Assert.assertEquals(textBoxWidth, msg2.payload.annotations[0].textBoxWidth);
        Assert.assertEquals(fontColor, msg2.payload.annotations[0].fontColor);
        Assert.assertEquals(fontSize, msg2.payload.annotations[0].fontSize);
        Assert.assertEquals(x, msg2.payload.annotations[0].x);
        Assert.assertEquals(y, msg2.payload.annotations[0].y);
        Assert.assertEquals(calcedFontSize, msg2.payload.annotations[0].calcedFontSize);
        Assert.assertEquals(dataPoints, msg2.payload.annotations[0].dataPoints);
        Assert.assertEquals(status, msg2.payload.annotations[0].status);
        Assert.assertEquals(annotationIDText, msg2.payload.annotations[0].annotationID);
        Assert.assertEquals(AnnotationType.TEXT, msg2.payload.annotations[0].annotationType);
    }
}
