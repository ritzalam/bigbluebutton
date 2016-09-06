package org.bigbluebutton.common.command
{
  import org.bigbluebutton.common.model.MyUserModel;
  import org.bigbluebutton.common.service.BbbAppsConnection;
  import org.bigbluebutton.common.service.network.messages.ValidateAuthTokenRequestMessage;
  import org.bigbluebutton.common.util.ISessionUtil;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class ValidateAuthTokenCommand extends Command
  {
    [Inject]
    public var connection:BbbAppsConnection;
    
    [Inject]
    public var sessionUtil:ISessionUtil;
    
    public function ValidateAuthTokenCommand()
    {
      super();
    }
    
    override public function execute():void {
      var message:ValidateAuthTokenRequestMessage = 
        new ValidateAuthTokenRequestMessage(sessionUtil.getSessionToken());
 
      connection.sendJsonMessage(message);
    }
  }
}