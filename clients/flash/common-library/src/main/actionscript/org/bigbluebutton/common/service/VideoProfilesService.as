package org.bigbluebutton.common.service
{
  import flash.events.Event;
  import flash.events.HTTPStatusEvent;
  import flash.events.IOErrorEvent;
  import flash.net.URLLoader;
  import flash.net.URLRequest;
  import flash.net.URLRequestMethod;
  
  import org.bigbluebutton.common.model.IVideoProfileModel;

  public class VideoProfilesService implements IVideoProfileService
  {
    private static const LOG:String = "VideoProfilesService - ";
    
    [Inject]
    public var videoProfile:IVideoProfileModel;
    
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
      trace(LOG + "httpStatusHandler: {0}", [event]);
    }
    
    private function ioErrorHandler(event:IOErrorEvent):void {
      trace(LOG + "ioErrorHandler: {0}", [event]);
    }
    
    private function handleComplete(e:Event):void {
      trace(LOG + "handleComplete: {0}", [new XML(e.target.data)]);
      videoProfile.parseProfilesXml(new XML(e.target.data))
    }
  }
}