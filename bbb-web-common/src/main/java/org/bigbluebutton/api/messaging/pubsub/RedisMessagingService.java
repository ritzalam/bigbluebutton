/**
 * BigBlueButton open source conferencing system - http://www.bigbluebutton.org/
 * <p>
 * Copyright (c) 2012 BigBlueButton Inc. and by respective authors (see below).
 * <p>
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License as published by the Free Software
 * Foundation; either version 3.0 of the License, or (at your option) any later
 * version.
 * <p>
 * BigBlueButton is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along
 * with BigBlueButton; if not, see <http://www.gnu.org/licenses/>.
 */

package org.bigbluebutton.api.messaging.pubsub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bigbluebutton.api.messaging.MessagingService;
import org.bigbluebutton.common.messages.*;
import org.bigbluebutton.common.messages.PubSubPingMessage;
import org.bigbluebutton.messages.RegisterUserMessage;
import org.bigbluebutton.messages.CreateMeetingRequest;
import org.bigbluebutton.messages.CreateMeetingRequest.CreateMeetingRequestPayload;
import org.bigbluebutton.pubsub.redis.MessagePublisher;
import org.bigbluebutton.web.services.turn.StunServer;
import org.bigbluebutton.web.services.turn.TurnEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.gson.Gson;

public class RedisMessagingService implements MessagingService {
  private static Logger log = LoggerFactory.getLogger(RedisMessagingService.class);

  private MessagePublisher sender;

  public void createMeeting(String meetingID, String externalMeetingID,
                            String parentMeetingID, String meetingName, Boolean recorded,
                            String voiceBridge, Integer duration, Boolean autoStartRecording,
                            Boolean allowStartStopRecording, Boolean webcamsOnlyForModerator,
                            String moderatorPass, String viewerPass, Long createTime,
                            String createDate, Boolean isBreakout, Integer sequence) {
    CreateMeetingRequestPayload payload = new CreateMeetingRequestPayload(
      meetingID, externalMeetingID, parentMeetingID, meetingName,
      recorded, voiceBridge, duration, autoStartRecording,
      allowStartStopRecording, webcamsOnlyForModerator,
      moderatorPass, viewerPass, createTime, createDate, isBreakout,
      sequence);
    CreateMeetingRequest msg = new CreateMeetingRequest(payload);

    Gson gson = new Gson();
    String json = gson.toJson(msg);
    log.info("Sending create meeting message to bbb-apps:[{}]", json);
    sender.send(msg.getChannel(), json);
  }

  public void destroyMeeting(String meetingID) {
    DestroyMeetingMessage.DestroyMeetingMessagePayload payload =
            new DestroyMeetingMessage.DestroyMeetingMessagePayload(meetingID);

    DestroyMeetingMessage msg = new DestroyMeetingMessage(payload);
    sender.send(msg);
  }

  public void registerUser(String meetingID, String internalUserId, String fullname, String role,
                           String externUserID, String authToken, String avatarURL, Boolean guest,
                           Boolean authed, Boolean needsModApproval) {
    RegisterUserMessage.RegisterUserMessagePayload payload =
            new RegisterUserMessage.RegisterUserMessagePayload(meetingID, internalUserId, fullname, role,
                    externUserID, authToken, avatarURL, guest, authed, needsModApproval);

    RegisterUserMessage msg = new RegisterUserMessage(payload);
    String json = msg.toJson();
    log.info("Sending register user message to bbb-apps:[{}]", json);
    sender.send(msg);
  }

  public void endMeeting(String meetingId) {
    EndMeetingMessage.EndMeetingMessagePayload payload = new EndMeetingMessage.EndMeetingMessagePayload(meetingId);
    EndMeetingMessage msg = new EndMeetingMessage(payload);
    String json = msg.toJson();
    log.info("Sending end meeting message to bbb-apps:[{}]", json);
    sender.send(msg);
  }


  public void send(IBigBlueButtonMessage message) {
    sender.send(message.getChannel(), message.toJson());
  }

  public void send(String channel, String message) {
    sender.send(channel, message);
  }

  public void sendPolls(String meetingId, String title, String question, String questionType, List<String> answers) {
    Gson gson = new Gson();

    HashMap<String, Object> map = new HashMap<String, Object>();
    map.put("messageId", ChannelConstants.SEND_POLLS_EVENT);
    map.put("meetingId", meetingId);
    map.put("title", title);
    map.put("question", question);
    map.put("questionType", questionType);
    map.put("answers", answers);

    sender.send(ChannelConstants.TO_POLLING_CHANNEL, gson.toJson(map));
  }

  public void setMessageSender(MessagePublisher sender) {
    this.sender = sender;
  }


  public void sendStunTurnInfo(String meetingId, String internalUserId, Set<StunServer> stuns, Set<TurnEntry> turns) {
    ArrayList<String> stunsArrayList = new ArrayList<String>();
    Iterator stunsIter = stuns.iterator();

    while (stunsIter.hasNext()) {
      StunServer aStun = (StunServer) stunsIter.next();
      if (aStun != null) {
        stunsArrayList.add(aStun.url);
      }
    }

    ArrayList<Map<String, Object>> turnsArrayList = new ArrayList<Map<String, Object>>();
    Iterator turnsIter = turns.iterator();
    while (turnsIter.hasNext()) {
      TurnEntry te = (TurnEntry) turnsIter.next();
      if (null != te) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(MessageBodyConstants.USERNAME, te.username);
        map.put(MessageBodyConstants.URL, te.url);
        map.put(MessageBodyConstants.TTL, te.ttl);
        map.put(MessageBodyConstants.PASSWORD, te.password);

        turnsArrayList.add(map);
      }
    }

    SendStunTurnInfoReplyMessage msg = new SendStunTurnInfoReplyMessage(meetingId, internalUserId,
            stunsArrayList, turnsArrayList);

    sender.send(msg);
  }
}
