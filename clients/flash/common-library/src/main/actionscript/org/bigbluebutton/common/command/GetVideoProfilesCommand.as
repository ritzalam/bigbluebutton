package org.bigbluebutton.common.command
{
  import org.bigbluebutton.common.service.IVideoProfileService;
  import org.bigbluebutton.common.util.ISessionUtil;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class GetVideoProfilesCommand extends Command
  {
    [Inject]
    public var service:IVideoProfileService;
    
    [Inject]
    public var sessionUtil:ISessionUtil;
    
    public function GetVideoProfilesCommand()
    {
      super();
    }
    
    override public function execute():void {
      service.getProfiles(sessionUtil.getTopLevelUrl());
    }
  }
}