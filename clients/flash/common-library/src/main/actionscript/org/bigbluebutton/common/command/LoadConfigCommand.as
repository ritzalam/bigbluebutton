package org.bigbluebutton.common.command
{
  import org.bigbluebutton.common.service.IConfigService;
  import org.bigbluebutton.common.util.ISessionUtil;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class LoadConfigCommand extends Command
  {
    [Inject]
    public var sessionUtil:ISessionUtil;
    
    [Inject]
    public var configService:IConfigService;
    
    public function LoadConfigCommand()
    {
      super();
    }
    
    override public function execute():void {
      configService.loadConfig(sessionUtil.getTopLevelUrl(), sessionUtil.getSessionToken());
    }
  }
}