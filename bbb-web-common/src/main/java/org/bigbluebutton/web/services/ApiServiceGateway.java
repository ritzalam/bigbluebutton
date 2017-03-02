package org.bigbluebutton.web.services;

import java.io.IOException;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bigbluebutton.messages.CreateMeetingRequest;

/**
 * Created by ralam on 3/1/2017.
 */
public class ApiServiceGateway {
  public void createMeeting() {

    /*
    val createMeetingPayload: CreateMeetingRequestMessageBody =
      new CreateMeetingRequestMessageBody("id", "externalId", "parentId", "name",
        record = true, voiceConfId = "85115", duration = 600,
        autoStartRecording = true, allowStartStopRecording = true,
        webcamsOnlyForModerator = true, moderatorPass = "MP",
        viewerPass = "AP", createTime = 10000L, createDate = "Now",
        isBreakout = false, sequence = 0)
*/

    CreateMeetingRequest.CreateMeetingRequestPayload payload =
      new CreateMeetingRequest.CreateMeetingRequestPayload(
        "id", "externalId", "parentId", "name",
        true, "85115", 600, true,
        true, true,
        "MP", "AP", 10000L, "Now", false,
    0);

    CreateMeetingRequest msg = new CreateMeetingRequest(payload);

    Gson gson = new Gson();
    String json = gson.toJson(msg);


    CloseableHttpClient httpclient = HttpClients.createDefault();
    try {
      HttpPost httpPost = new HttpPost("http://192.168.246.131:4000/createMeeting");

      StringEntity entity = new StringEntity(json);
      httpPost.setEntity(entity);
      httpPost.setHeader("Accept", "application/json");
      httpPost.setHeader("Content-type", "application/json");

      System.out.println("Executing request " + httpPost.getRequestLine());

      // Create a custom response handler
      ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

        @Override
        public String handleResponse(
          final HttpResponse response) throws ClientProtocolException, IOException {
          int status = response.getStatusLine().getStatusCode();
          if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? EntityUtils.toString(entity) : null;
          } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
          }
        }
      };

      String responseBody = httpclient.execute(httpPost, responseHandler);
      System.out.println("----------------------------------------");
      System.out.println(responseBody);

    } catch(IOException ex) {
      // IOException
    } finally {
      try {
        httpclient.close();
      } catch(IOException ex) {
        // do nothing
      }
    }
  }
}
