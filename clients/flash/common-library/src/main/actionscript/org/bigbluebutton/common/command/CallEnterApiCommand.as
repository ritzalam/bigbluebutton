package org.bigbluebutton.common.command
{
  import org.bigbluebutton.common.model.IConfigModel;
  import org.bigbluebutton.common.service.IEnterApiService;
  import org.bigbluebutton.common.util.ISessionUtil;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class CallEnterApiCommand extends Command
  {
    [Inject]
    public var enterApiService:IEnterApiService;
    
    [Inject]
    public var sessionUtil:ISessionUtil;
    
    [Inject]
    public var configModel:IConfigModel;
    
    public function CallEnterApiCommand()
    {
      super();
    }
    
    override public function execute():void {
      enterApiService.enter(configModel.getEnterApiUrl(), sessionUtil.getSessionToken());
    }
  }
}