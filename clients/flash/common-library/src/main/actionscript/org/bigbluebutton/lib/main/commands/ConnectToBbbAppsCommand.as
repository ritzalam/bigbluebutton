package org.bigbluebutton.lib.main.commands
{
  import org.bigbluebutton.common.service.BbbAppsConnection;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class ConnectToBbbAppsCommand extends Command
  {
    [Inject]
    public var bbbAppsConnection:BbbAppsConnection;
    
    public function ConnectToBbbAppsCommand()
    {
      super();
    }
    
    override public function execute():void {
      bbbAppsConnection.connect();
    }
  }
}