package org.bigbluebutton.common.domain
{
  public class Presence
  {
    private var _id:String;
    private var _userAgent:String;
    private var _data:DataApp;
    private var _voice: Voice;
    private var _webCams:WebCamStreams;
    private var _screenShare: ScreenShareStreams;
    
    public function Presence()
    {
    }
  }
}