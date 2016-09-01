package org.bigbluebutton.common.handlers
{
  import robotlegs.bender.framework.api.ILogger;

  public class ValidateAuthTokenSuccessMessageHandler implements IMessageHandler
  {  
    [Inject]
    public var logger:ILogger;
    
    public function handle(json:Object):void {
      logger.debug("ValidateAuthTokenSuccessMessageHandler " + JSON.stringify(json));
    }
  }
}