package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.objects.AnnotationStatus;
import org.bigbluebutton.common.messages2x.objects.AnnotationType;
import org.bigbluebutton.common.messages2x.objects.TextAnnotation;
import org.bigbluebutton.common.messages2x.whiteboard.SendTextAnnotationReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendTextAnnotationReplyMessage2xTest {

    @Test
    public void SendTextAnnotationReplyMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "wbd123";

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
        String annotationID = "bla_shape_132";

        TextAnnotation shape = new TextAnnotation(text, textBoxHeight, textBoxWidth, fontColor,
                fontSize, x, y, calcedFontSize, dataPoints, status, annotationID);

        SendTextAnnotationReplyMessage2x msg1 = new SendTextAnnotationReplyMessage2x(meetingID,
                requesterID, whiteboardID, shape);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        SendTextAnnotationReplyMessage2x msg2 = SendTextAnnotationReplyMessage2x.fromJson(json1);

        Assert.assertEquals(SendTextAnnotationReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(text, msg2.payload.shape.text);
        Assert.assertEquals(textBoxHeight, msg2.payload.shape.textBoxHeight);
        Assert.assertEquals(textBoxWidth, msg2.payload.shape.textBoxWidth);
        Assert.assertEquals(fontColor, msg2.payload.shape.fontColor);
        Assert.assertEquals(fontSize, msg2.payload.shape.fontSize);
        Assert.assertEquals(x, msg2.payload.shape.x);
        Assert.assertEquals(y, msg2.payload.shape.y);
        Assert.assertEquals(calcedFontSize, msg2.payload.shape.calcedFontSize);
        Assert.assertEquals(dataPoints, msg2.payload.shape.dataPoints);
        Assert.assertEquals(status, msg2.payload.shape.status);
        Assert.assertEquals(annotationID, msg2.payload.shape.annotationID);
        Assert.assertEquals(AnnotationType.TEXT, msg2.payload.shape.annotationType);

        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }
}
