package org.bigbluebutton.messages;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.whiteboard.SendWbAnnotationReq;
import org.bigbluebutton.messages.whiteboard.SendWbAnnotationReq.SendWbAnnotationReqBody;
//import org.bigbluebutton.messages.whiteboard.TextAnnotation;
import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;

public class SendWbAnnotationReqTest {
    @Test
    public void testSendWbAnnotationReqTest() {

        String meetingID = "abc123";
        String senderId = "req123";
        String replyTo = "req123";
        String whiteboardID = "wbd123";

        String text = "KKKKKKKK\r";
        Double textBoxHeight = 2.747678;
//        Double textBoxWidth = 11.747968;
//        Integer fontColor = 0;
//        Integer fontSize = 18;
//        Double x = 84.52381;
//        Double y = 41.795666;
//        Double calcedFontSize = 2.7863777;
//        String dataPoints = "84.52381,41.795666"; // why String? TODO
//        String status = "DRAW_END";
//        String annotationID = "bla_shape_132";


        Map<String, Object> textAnnot = new HashMap<String, Object>();
        textAnnot.put("text", text);
        textAnnot.put("textBoxHeight", textBoxHeight);
        SendWbAnnotationReqBody body = new SendWbAnnotationReqBody(whiteboardID, "text", textAnnot);


        MessageHeader mh = new MessageHeader(SendWbAnnotationReq.NAME, meetingID, senderId,
                replyTo);
        SendWbAnnotationReq msg = new SendWbAnnotationReq(mh, body);
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        System.out.println("\n\n\n\n\n" + json);



        SendWbAnnotationReq rxMsg = gson.fromJson(json, SendWbAnnotationReq.class);

        System.out.println("\n\n" + rxMsg.toJson());



        /*
        String meetingId = "abc123";
        String externalId = "extabc123";
        Boolean record = false;
        Integer durationInMinutes = 20;
        String name = "Breakout room 1";
        String voiceConfId = "851153";
        Boolean autoStartRecording = false;
        Boolean allowStartStopRecording = false;
        Boolean isBreakout = true;
        String viewerPassword = "vp";
        String moderatorPassword = "mp";
        long createTime = System.currentTimeMillis();
        String createDate = new Date(createTime).toString();

        SendWbAnnotationReqBody payload =
                new SendWbAnnotationReqBody(meetingId, externalId, name, record, voiceConfId,
                        durationInMinutes, autoStartRecording,
                        allowStartStopRecording, moderatorPassword,
                        viewerPassword, createTime, createDate, isBreakout);
        SendWbAnnotationReqTest msg = new SendWbAnnotationReqTest(payload);
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        System.out.println(json);

        SendWbAnnotationReqTest rxMsg = gson.fromJson(json, SendWbAnnotationReqTest.class);

        Assert.assertEquals(rxMsg.header.name, SendWbAnnotationReqTest.NAME);
        Assert.assertEquals(rxMsg.payload.id, meetingId);
        Assert.assertEquals(rxMsg.payload.name, name);
        Assert.assertEquals(rxMsg.payload.voiceConfId, voiceConfId);
        Assert.assertEquals(rxMsg.payload.viewerPassword, viewerPassword);
        Assert.assertEquals(rxMsg.payload.moderatorPassword, moderatorPassword);
        Assert.assertEquals(rxMsg.payload.durationInMinutes, durationInMinutes);
        Assert.assertEquals(rxMsg.payload.isBreakout, isBreakout);
        */
    }
}
