/**
 * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
 *
 * Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
 *
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 *
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along
 * with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
 *
 */
package org.bigbluebutton.red5.service;

import org.bigbluebutton.bbbred5apps.IBigBlueButtonRed5App;
import org.bigbluebutton.common.messages.MessagingConstants;
import org.bigbluebutton.messages.body.MessageHeader;
import org.bigbluebutton.messages.chat.GetChatHistoryRequestMessage;
import org.bigbluebutton.messages.chat.SendPrivateChatMessage;
import org.bigbluebutton.messages.chat.SendPublicChatMessage;
import org.bigbluebutton.red5.BigBlueButtonSession;
import org.bigbluebutton.red5.Constants;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.api.Red5;
import org.slf4j.Logger;

import org.bigbluebutton.messages.vo.ChatMessage;

public class ChatService {
    private static Logger log = Red5LoggerFactory.getLogger( ChatService.class, "bigbluebutton" );

    private int maxMessageLength;
    private IBigBlueButtonRed5App app;

    public void sendPublicChatHistory() {
        String meetingID = Red5.getConnectionLocal().getScope().getName();
        String requesterID = getBbbSession().getInternalUserID();
        // Just hardcode as we don't really need it for flash client. (ralam may 7, 2014)
        String replyTo = meetingID + "/" + requesterID;

        app.getChatHistory(meetingID, requesterID, replyTo);

        GetChatHistoryRequestMessage fullMessage = new GetChatHistoryRequestMessage(new MessageHeader
                    (GetChatHistoryRequestMessage.NAME, meetingID, requesterID, replyTo), new
                GetChatHistoryRequestMessage.Body(replyTo, requesterID));
            app.sendJsonMessage(MessagingConstants.TO_CHAT_CHANNEL, fullMessage.toJson());
    }

    private BigBlueButtonSession getBbbSession() {
        return (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
    }

    public void sendPublicMessage(String jsonBody) {
        ChatMessage publicChatObject = ChatMessage.fromJson(jsonBody);

        String meetingID = Red5.getConnectionLocal().getScope().getName();
        String requesterID = getBbbSession().getInternalUserID();
        // Just hardcode as we don't really need it for flash client. (ralam may 7, 2014)
        String replyTo = meetingID + "/" + requesterID;

        // The message is being ignored in the red5 application to avoid copying it to any
        // another application which that may cause a memory issue
        String chatText = publicChatObject.message;
        if (chatText.length() <= maxMessageLength) {
            SendPublicChatMessage fullMessage = new SendPublicChatMessage(new MessageHeader
                    (SendPublicChatMessage.NAME, meetingID, requesterID, replyTo), new
                    SendPublicChatMessage.Body(requesterID, publicChatObject));
            app.sendJsonMessage(MessagingConstants.TO_CHAT_CHANNEL, fullMessage.toJson());
        }
        else {
            log.warn("sendPublicMessage maximum allowed message length exceeded (length: [" +
                    chatText.length() + "], message: [" + chatText + "])");
        }
    }

    public void setMaxMessageLength(int maxLength) {
        maxMessageLength = maxLength;
    }


    public void sendPrivateMessage(String jsonBody){
        ChatMessage privateChatObject = ChatMessage.fromJson(jsonBody);

        String meetingID = Red5.getConnectionLocal().getScope().getName();
        String requesterID = getBbbSession().getInternalUserID();
        // Just hardcode as we don't really need it for flash client. (ralam may 7, 2014)
        String replyTo = meetingID + "/" + requesterID;

        // The message is being ignored in the red5 application to avoid copying it to any
        // another application which that may cause a memory issue
        String chatText = privateChatObject.message;
        if (chatText.length() <= maxMessageLength) {
            SendPrivateChatMessage fullMessage = new SendPrivateChatMessage(new MessageHeader
                    (SendPrivateChatMessage.NAME, meetingID, requesterID, replyTo), new
                    SendPrivateChatMessage.Body(requesterID, privateChatObject));
            app.sendJsonMessage(MessagingConstants.TO_CHAT_CHANNEL, fullMessage.toJson());
        }
        else {
            log.warn("sendPrivateMessage maximum allowed message length exceeded (length: [" +
                    chatText.length() + "], message: [" + chatText + "])");
        }
    }

    public void setMainApplication(IBigBlueButtonRed5App app) {
        this.app = app;
    }
}
