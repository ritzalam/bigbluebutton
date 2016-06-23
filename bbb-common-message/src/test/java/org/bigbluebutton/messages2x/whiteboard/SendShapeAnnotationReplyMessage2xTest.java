package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.objects.AnnotationStatus;
import org.bigbluebutton.common.messages2x.objects.ShapeAnnotation;
import org.bigbluebutton.common.messages2x.objects.ShapeType;
import org.bigbluebutton.common.messages2x.whiteboard.SendShapeAnnotationReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class SendShapeAnnotationReplyMessage2xTest {

    @Test
    public void SendShapeAnnotationReplyMessage2x() {

        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "wbd123";

        ShapeType shapeType = ShapeType.TRIANGLE;
        Double[] points = new Double[] { 86.71893, 10.835914, 89.04181, 28.034056 };

        Integer color = 0;
        Boolean transparency = false;
        AnnotationStatus status = AnnotationStatus.DRAW_END;
        String shapeID = "bla_shape_132";
        Integer thickness = 1;

        ShapeAnnotation shape = new ShapeAnnotation(shapeType, points, color, transparency,
                status, shapeID, thickness);

        SendShapeAnnotationReplyMessage2x msg1 = new SendShapeAnnotationReplyMessage2x(meetingID,
                requesterID, whiteboardID, shape);

        String json1 = msg1.toJson();

        System.out.println(json1);

        SendShapeAnnotationReplyMessage2x msg2 = SendShapeAnnotationReplyMessage2x.fromJson(json1);

        Assert.assertEquals(SendShapeAnnotationReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(ShapeType.TRIANGLE, msg2.payload.shape.shapeType);
        Assert.assertArrayEquals(points, msg2.payload.shape.points);
        Assert.assertEquals(color, msg2.payload.shape.color);
        Assert.assertEquals(transparency, msg2.payload.shape.transparency);
        Assert.assertEquals(thickness, msg2.payload.shape.thickness);
        Assert.assertEquals(status, msg2.payload.shape.status);
        Assert.assertEquals(shapeID, msg2.payload.shape.shapeID);

        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
    }
}
