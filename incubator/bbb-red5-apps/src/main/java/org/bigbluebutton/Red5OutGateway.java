package org.bigbluebutton;

import org.bigbluebutton.red5.client.messaging.ConnectionInvokerService;

public class Red5OutGateway {
    private final  ConnectionInvokerService service;

    public Red5OutGateway(ConnectionInvokerService service) {
        this.service = service;
    }
}
