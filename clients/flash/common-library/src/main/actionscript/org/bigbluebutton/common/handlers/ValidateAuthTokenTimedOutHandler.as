package org.bigbluebutton.common.handlers
{
  import robotlegs.bender.framework.api.ILogger;

  public class ValidateAuthTokenTimedOutHandler
  {
    [Inject]
    public var logger:ILogger;
    
    public function handle(message:Object):void
    {
    }
  }
}