package org.bigbluebutton.api.messaging;


import java.util.List;
import java.util.Map;

public interface IStorageService {
  String storeSubscription(String meetingId, String externalMeetingID, String callbackURL);

  boolean removeSubscription(String meetingId, String subscriptionId);

  List<Map<String, String>> listSubscriptions(String meetingId);

  void recordMeetingInfo(String meetingId, Map<String, String> info, Map<String, String> breakoutInfo);

}
