package org.bigbluebutton.common.model
{
  import org.bigbluebutton.common.domain.Config;

  public interface IConfigModel
  {
    function setConfig(config: Config):void;
    function getEnterApiUrl():String;
  }
}