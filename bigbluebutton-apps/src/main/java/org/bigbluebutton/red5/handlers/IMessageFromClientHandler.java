package org.bigbluebutton.red5.handlers;


public interface IMessageFromClientHandler {

    void handle(String meetingId, String senderId, String json);
}
