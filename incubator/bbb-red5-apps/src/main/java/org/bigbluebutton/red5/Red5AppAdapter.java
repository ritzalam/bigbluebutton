package org.bigbluebutton.red5;

import com.google.gson.Gson;
import org.bigbluebutton.bbbred5apps.IBigBlueButtonRed5App;
import org.bigbluebutton.bbbred5apps.messages.LockSettingsVO;
import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService;
import org.bigbluebutton.red5.pubsub.MessagePublisher;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Red5AppAdapter extends MultiThreadedApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(Red5AppAdapter.class, "bigbluebutton");

    private IBigBlueButtonRed5App app;
    private ConnectionInvokerService connInvokerService;

    private final UserConnectionMapper userConnections = new UserConnectionMapper();

    private final String CONN = "RED5-";

    @Override
    public boolean appConnect(IConnection conn, Object[] params) {
        return super.appConnect(conn, params);
    }

    @Override
    public void appDisconnect(IConnection conn) {
        super.appDisconnect(conn);
    }

    @Override
    public boolean appJoin(IClient client, IScope scope) {
        return super.appJoin(client, scope);
    }

    @Override
    public void appLeave(IClient client, IScope scope) {
        super.appLeave(client, scope);
    }

    @Override
    public boolean roomJoin(IClient client, IScope scope) {
        return super.roomJoin(client, scope);
    }

    @Override
    public void roomLeave(IClient client, IScope scope) {
        super.roomLeave(client, scope);
    }

    @Override
    public boolean appStart(IScope app) {
        super.appStart(app);
        connInvokerService.setAppScope(app);
        log.warn("\n\n\nappStart bbb-red5-apps Red5AppAdaper log\n\n\n"); //TODO change to info
        return true;
    }

    @Override
    public void appStop(IScope app) {
        super.appStop(app);
    }

    @Override
    public boolean roomStart(IScope room) {
        return super.roomStart(room);
    }

    @Override
    public void roomStop(IScope room) {
        super.roomStop(room);
    }

    @Override
    public boolean roomConnect(IConnection connection, Object[] params) {
        String username = ((String) params[0]).toString();
        String role = ((String) params[1]).toString();
        String room = ((String) params[2]).toString();

        String voiceBridge = ((String) params[3]).toString();

        boolean record = (Boolean) params[4];

        String externalUserID = ((String) params[5]).toString();
        String internalUserID = ((String) params[6]).toString();

        Boolean muted = false;
        if (params.length >= 7 && ((Boolean) params[7])) {
            muted = true;
        }

        Map<String, Boolean> lsMap = null;
        if (params.length >= 8) {
            try {
                lsMap = (Map<String, Boolean>) params[8];
            } catch (Exception e) {
                lsMap = new HashMap<String, Boolean>();
            }
        }

        String userId = internalUserID;
        String sessionId = CONN + userId;
        BigBlueButtonSession bbbSession = new BigBlueButtonSession(room, internalUserID, username, role,
                voiceBridge, record, externalUserID, muted, sessionId);
        connection.setAttribute(Constants.SESSION, bbbSession);
        connection.setAttribute("INTERNAL_USER_ID", internalUserID);
        connection.setAttribute("USER_SESSION_ID", sessionId);
        connection.setAttribute("TIMESTAMP", System.currentTimeMillis());


        String meetingId = bbbSession.getRoom();

        String connType = getConnectionType(Red5.getConnectionLocal().getType());
        String userFullname = bbbSession.getUsername();
        String connId = Red5.getConnectionLocal().getSessionId();

        app.userConnected(room, internalUserID, muted, lsMap, connId);

        String remoteHost = Red5.getConnectionLocal().getRemoteAddress();
        int remotePort = Red5.getConnectionLocal().getRemotePort();

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("meetingId", meetingId);
        logData.put("connType", connType);
        logData.put("connId", connId);
        logData.put("conn", remoteHost + ":" + remotePort);
        logData.put("userId", userId);
        logData.put("externalUserId", externalUserID);
        logData.put("sessionId", sessionId);
        logData.put("username", userFullname);
        logData.put("event", "user_joining_bbb_apps");
        logData.put("description", "User joining BBB Apps.");

        Gson gson = new Gson();
        String logStr = gson.toJson(logData);

        log.info("User joining bbb-apps: data={}", logStr);

        userConnections.addUserConnection(userId, connId);

        return super.roomConnect(connection, params);

    }

    private String getConnectionType(String connType) {
        if ("persistent".equals(connType.toLowerCase())) {
            return "RTMP";
        } else if ("polling".equals(connType.toLowerCase())) {
            return "RTMPT";
        } else {
            return connType.toUpperCase();
        }
    }

    @Override
    public void roomDisconnect(IConnection conn) {

        String remoteHost = Red5.getConnectionLocal().getRemoteAddress();
        int remotePort = Red5.getConnectionLocal().getRemotePort();

        BigBlueButtonSession bbbSession = (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);

        String meetingId = bbbSession.getRoom();
        String userId = bbbSession.getInternalUserID();
        String connType = getConnectionType(Red5.getConnectionLocal().getType());
        String userFullname = bbbSession.getUsername();
        String connId = Red5.getConnectionLocal().getSessionId();

        String sessionId = CONN + userId;

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("meetingId", meetingId);
        logData.put("connType", connType);
        logData.put("connId", connId);
        logData.put("conn", remoteHost + ":" + remotePort);
        logData.put("sessionId", sessionId);
        logData.put("userId", userId);
        logData.put("username", userFullname);
        logData.put("event", "user_leaving_bbb_apps");
        logData.put("description", "User leaving BBB Apps.");

        Gson gson = new Gson();
        String logStr = gson.toJson(logData);

        boolean removeUser = userConnections.userDisconnected(userId, connId);

        if (removeUser) {
            log.info("User leaving bbb-apps: data={}", logStr);
            app.userDisconnected(bbbSession.getRoom(), getBbbSession().getInternalUserID(), sessionId);
        } else {
            log.info("User not leaving bbb-apps but just disconnected: data={}", logStr);
        }

        super.roomDisconnect(conn);
    }

    public void messageFromClientRemoteCall(String json) {
        log.debug("messageFromClientRemoteCall: \n" + json + "\n");
        app.handleJsonMessage(json);
    }

    public void validateToken(Map<String, String> msg) {
        String token = (String) msg.get("authToken");

        BigBlueButtonSession bbbSession = (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
        assert bbbSession != null;
        String userId = bbbSession.getInternalUserID();
        String meetingId = Red5.getConnectionLocal().getScope().getName();
        String connId = Red5.getConnectionLocal().getSessionId();
        String sessionId = CONN + connId + "-" + userId;

        Map<String, Object> logData = new HashMap<String, Object>();
        logData.put("meetingId", meetingId);
        logData.put("connId", connId);
        logData.put("sessionId", sessionId);
        logData.put("userId", userId);
        logData.put("token", token);
        logData.put("event", "user_validate_token_bbb_apps");
        logData.put("description", "User validate token BBB Apps.");

        Gson gson = new Gson();
        String logStr = gson.toJson(logData);

        log.info("User validate token bbb-apps: data={}", logStr);
        app.validateAuthToken(meetingId, userId, token, meetingId + "/" + userId, sessionId);
    }


    public void setApplicationListeners(Set<IApplication> listeners) {
        Iterator<IApplication> iter = listeners.iterator();
        while (iter.hasNext()) {
            super.addListener((IApplication) iter.next());
        }
    }

    private BigBlueButtonSession getBbbSession() {
        return (BigBlueButtonSession) Red5.getConnectionLocal().getAttribute(Constants.SESSION);
    }

    public void setConnInvokerService(ConnectionInvokerService connInvokerService) {
        this.connInvokerService = connInvokerService;
    }

    public void setMainApplication(IBigBlueButtonRed5App app) {
        this.app = app;
        log.warn("\n\nYAYAYYAYAYAYA\n\n");
    }

}
