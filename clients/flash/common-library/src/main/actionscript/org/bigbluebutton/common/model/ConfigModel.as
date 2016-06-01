package org.bigbluebutton.common.model
{
  import org.bigbluebutton.common.domain.Config;
  import org.bigbluebutton.common.signal.ConfigLoadedSignal;

  public class ConfigModel implements IConfigModel
  {
    [Inject]
    public var configLoadedSignal:ConfigLoadedSignal;
    
    private var _config:Config;
   
    
    public function setConfig(config: Config):void {
      _config = config;
      configLoadedSignal.dispatch();
    }
    
    public function getEnterApiUrl():String {
      return _config.application.host;
    }
  }
}