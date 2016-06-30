package org.bigbluebutton.common.command
{
  import org.bigbluebutton.common.service.ILocaleXmlLoaderService;
  
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class LoadLocaleXmlCommand extends Command
  {
    [Inject]
    public var localeXmlLoader:ILocaleXmlLoaderService;
    
    public function LoadLocaleXmlCommand()
    {
      super();
    }
    
    override public function execute():void {
      localeXmlLoader.loadLocalXml();
    }
  }
}