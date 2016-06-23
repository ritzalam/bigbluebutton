package org.bigbluebutton.messages2x.whiteboard;

import org.bigbluebutton.common.messages2x.objects.AnnotationStatus;
import org.bigbluebutton.common.messages2x.objects.AnnotationType;
import org.bigbluebutton.common.messages2x.objects.ShapeAnnotation;
import org.bigbluebutton.common.messages2x.whiteboard.GetShapeAnnotationsReplyMessage2x;
import org.bigbluebutton.common.messages2x.whiteboard.SendShapeAnnotationReplyMessage2x;
import org.junit.Assert;
import org.junit.Test;

public class GetShapeAnnotationsReplyMessage2xTest {

    @Test
    public void GetShapeAnnotationsReplyMessage2x() {
        String meetingID = "abc123";
        String requesterID = "req123";
        String whiteboardID = "wbd123";

        AnnotationType annotationType = AnnotationType.TRIANGLE;
        Double[] points = new Double[] { 86.71893, 10.835914, 89.04181, 28.034056 };

        Integer color = 0;
        Boolean transparency = false;
        AnnotationStatus status = AnnotationStatus.DRAW_END;
        String shapeID = "bla_shape_132";
        Integer thickness = 1;

        ShapeAnnotation shapeAnnotation = new ShapeAnnotation(annotationType, points, color, transparency,
                status, shapeID, thickness);

        ShapeAnnotation[] arr = new ShapeAnnotation[] { shapeAnnotation, shapeAnnotation };

        GetShapeAnnotationsReplyMessage2x msg1 = new GetShapeAnnotationsReplyMessage2x(meetingID,
                requesterID, whiteboardID, arr);

        String json1 = msg1.toJson();

        // System.out.println(json1);

        GetShapeAnnotationsReplyMessage2x msg2 = GetShapeAnnotationsReplyMessage2x.fromJson(json1);

        Assert.assertEquals(GetShapeAnnotationsReplyMessage2x.NAME, msg2.header.name);
        Assert.assertEquals(meetingID, msg2.payload.meetingID);
        Assert.assertEquals(requesterID, msg2.payload.requesterID);
        Assert.assertEquals(whiteboardID, msg2.payload.whiteboardID);

        Assert.assertEquals(AnnotationType.TRIANGLE, msg2.payload.annotations[0].annotationType);
        Assert.assertArrayEquals(points, msg2.payload.annotations[0].points);
        Assert.assertEquals(color, msg2.payload.annotations[0].color);
        Assert.assertEquals(transparency, msg2.payload.annotations[0].transparency);
        Assert.assertEquals(thickness, msg2.payload.annotations[0].thickness);
        Assert.assertEquals(status, msg2.payload.annotations[0].status);
        Assert.assertEquals(shapeID, msg2.payload.annotations[0].shapeID);
    }
}
