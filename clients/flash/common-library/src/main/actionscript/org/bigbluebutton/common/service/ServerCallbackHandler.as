package org.bigbluebutton.common.service
{
  import mx.utils.ObjectUtil;
  
  import org.bigbluebutton.common.receivers.UsersMessageHandler;
  import org.bigbluebutton.lib.common.models.IMessageListener;
  
  import robotlegs.bender.framework.api.ILogger;

  public class ServerCallbackHandler
  {
    [Inject]
    public var logger:ILogger;
    
    [Inject]
    public var usersMessageHandler:UsersMessageHandler;
    
    private var _messageListeners:Array = new Array();
        
    public function onBWCheck(... rest):Number {
      return 0;
    }
    
    public function onBWDone(... rest):void {
      logger.debug("onBWDone() " + ObjectUtil.toString(rest));
      var p_bw:Number;
      if (rest.length > 0)
        p_bw = rest[0];
      // your application should do something here 
      // when the bandwidth check is complete 
      logger.debug("bandwidth = " + p_bw + " Kbps.");
    }
    
    public function onMessageFromServer(messageName:String, result:Object):void {
      logger.debug("RECEIVED MESSAGE: [" + messageName + "]");
      var msg:Object = JSON.parse(result.msg);
      logger.debug("MESSAGE: \n" + ObjectUtil.toString(msg) + "\n");
      //notifyListeners(messageName, result);
      usersMessageHandler.onMessage(messageName, result);
    }
    
    public function addMessageListener(listener:IMessageListener):void {
      _messageListeners.push(listener);
    }
    
    public function removeMessageListener(listener:IMessageListener):void {
      for (var ob:int = 0; ob < _messageListeners.length; ob++) {
        if (_messageListeners[ob] == listener) {
          _messageListeners.splice(ob, 1);
          break;
        }
      }
    }
    
    public function clearMessageListeners():void {
    }
    
    private function notifyListeners(messageName:String, message:Object):void {
      if (messageName != null && messageName != "") {
        for (var notify:String in _messageListeners) {
          _messageListeners[notify].onMessage(messageName, message);
        }
      } else {
        logger.debug("Message name is undefined");
      }
    }
  }
}