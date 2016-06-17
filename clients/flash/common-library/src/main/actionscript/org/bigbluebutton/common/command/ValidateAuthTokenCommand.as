package org.bigbluebutton.common.command
{
  import org.bigbluebutton.common.model.MyUserModel;
  import org.bigbluebutton.common.service.BbbAppsConnection;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class ValidateAuthTokenCommand extends Command
  {
    [Inject]
    public var connection:BbbAppsConnection;
    
    [Inject]
    public var myUserModel:MyUserModel;
    
    public function ValidateAuthTokenCommand()
    {
      super();
    }
    
    override public function execute():void {
      var message:Object = new Object();
      message["userId"] = myUserModel.internalUserId;
      message["authToken"] = myUserModel.authToken;
      connection.sendMessage("validateToken", message);
    }
  }
}