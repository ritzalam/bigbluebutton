package org.bigbluebutton;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bigbluebutton.red5apps.messages.Red5InJsonMsg;
import org.red5.logging.Red5LoggerFactory;
import org.red5.server.adapter.IApplication;
import org.red5.server.adapter.MultiThreadedApplicationAdapter;
import org.red5.server.api.IClient;
import org.red5.server.api.IConnection;
import org.red5.server.api.Red5;
import org.red5.server.api.scope.IScope;
import org.slf4j.Logger;

public class Red5AppsAdapter extends MultiThreadedApplicationAdapter {

    private static Logger log = Red5LoggerFactory.getLogger(Red5AppsAdapter.class, "bbbapps");

    private IRed5InGW red5InGW;

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
        System.out.println("***** Red5 Apps START!!!!!!");

        super.appStart(app);
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


        super.roomDisconnect(conn);
    }

    private String extractName(String json) {
        JsonParser parser = new JsonParser();
        JsonObject obj = (JsonObject) parser.parse(json);
        String answer = "";

        if (obj.has("header")) {
            JsonObject header = (JsonObject) obj.get("header");

            if (header.has("name")) {
                String messageName = header.get("name").getAsString();
                answer = messageName;
            }
        }
        return answer;
    }

    public void messageFromClientRemoteCall(String json) {
        log.warn("messageFromClientRemoteCall: \n" + json + "\n");

        String connectionId = Red5.getConnectionLocal().getSessionId();
        Red5InJsonMsg inMsg = new Red5InJsonMsg(extractName(json), json, connectionId);
        red5InGW.handle(inMsg);
    }

    public void setIRed5InGW(IRed5InGW red5InGW) {
        this.red5InGW = red5InGW;
    }
}