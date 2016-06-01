package org.bigbluebutton.common.service
{
  public interface IConfigService
  {
    function loadConfig(serverUrl:String, sessionToken:String):void;
  }
}