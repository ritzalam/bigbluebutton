package org.bigbluebutton;


import org.bigbluebutton.red5apps.messages.Red5InJsonMsg;

public interface IRed5InGW {
    void handle(Red5InJsonMsg msg);
}
