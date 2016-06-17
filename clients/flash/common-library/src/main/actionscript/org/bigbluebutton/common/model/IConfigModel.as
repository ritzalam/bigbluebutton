package org.bigbluebutton.common.model
{
  import org.bigbluebutton.common.domain.Config;
  import org.bigbluebutton.common.signal.ConfigLoadedSignal;

  public interface IConfigModel
  {
    function getConfigLoadedSignal():ConfigLoadedSignal;
    function setConfig(config: Config):void;
    function getEnterApiUrl():String;
    function getBbbAppUrl():String;
  }
}