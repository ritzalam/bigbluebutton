package org.bigbluebutton.common.domain
{
  public class Voice
  {
    private var _id:String;
    private var _joined:Boolean = false;
    private var _userAgent:String = "None";
    private var _callerIdName:String = "unknown";
    private var _callerIdNum: String = "unknown";
    private var _listenDirection:Boolean = false;
    private var _talkDirection:Boolean = false;
    private var _muted:Boolean = false;
    private var _talking:Boolean = false;
    
    public function Voice()
    {
    }
  }
}