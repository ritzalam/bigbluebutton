package org.bigbluebutton.common.service
{
  import flash.events.Event;
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.net.URLLoader;
  import flash.net.URLRequest;
  import flash.net.URLRequestMethod;
  
  import org.bigbluebutton.common.model.VideoProfileModel;
  
  import robotlegs.bender.framework.api.ILogger;

  public class VideoProfilesService implements IVideoProfileService
  {
    [Inject]
    public var logger:ILogger;
    
    [Inject]
    public var videoProfile:VideoProfileModel;
    
    private var urlLoader:URLLoader;
    
    public function getProfiles(serverUrl:String):void {
      urlLoader = new URLLoader();
      
      var profileUrl:String = serverUrl + "/client/conf/profiles.xml?a=" + new Date().time;
      var request:URLRequest = new URLRequest(profileUrl);
      request.method = URLRequestMethod.GET;
      
      urlLoader.addEventListener(Event.COMPLETE, handleComplete);
      urlLoader.addEventListener(HTTPStatusEvent.HTTP_STATUS, httpStatusHandler);
      urlLoader.addEventListener(IOErrorEvent.IO_ERROR, ioErrorHandler);
      urlLoader.load(request);
    }
    
    private function httpStatusHandler(event:HTTPStatusEvent):void {
      logger.debug("httpStatusHandler: " + event.toString());
    }
    
    private function ioErrorHandler(event:IOErrorEvent):void {
      logger.debug("ioErrorHandler: " + event.toString());
    }
    
    private function handleComplete(e:Event):void {
      //logger.debug("handleComplete: {0}", [new XML(e.target.data)]);
      videoProfile.parseProfilesXml(new XML(e.target.data))
    }
  }
}