package org.bigbluebutton.common.model
{
  import org.bigbluebutton.lib.video.models.VideoProfile;

  public interface IVideoProfileModel
  {
    function parseProfilesXml(profileXML:XML):void;
    function parseConfigXml(configXML:XML):void;
    function get profiles():Array;
    function getVideoProfileById(id:String):VideoProfile;
    function getVideoProfileByStreamName(streamName:String):VideoProfile;
    function get defaultVideoProfile():VideoProfile;
    function get fallbackVideoProfile():VideoProfile;
    function getProfileWithLowerResolution():VideoProfile;
  }
}