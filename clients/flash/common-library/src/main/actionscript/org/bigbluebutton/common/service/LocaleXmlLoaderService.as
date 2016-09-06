package org.bigbluebutton.common.service
{

  import flash.events.Event;
  import flash.net.URLLoader;
  import flash.net.URLRequest;
  
  import mx.core.FlexGlobals;
  import mx.utils.URLUtil;
  
  import org.bigbluebutton.common.model.LocaleModel;
  import org.bigbluebutton.common.signal.LocaleXmlLoadedSignal;

  public class LocaleXmlLoaderService implements ILocaleXmlLoaderService
  {
    public static const LOCALES_FILE:String = "client/locales.xml";
    public static const VERSION:String = "0.9.0";
    
    [Inject]
    public var localeModel:LocaleModel;
   
    [Inject]
    public var localeXmlLoadedSignal:LocaleXmlLoadedSignal;
    
    public function loadLocalXml():void {
      
      // Add a random string on the query so that we always get an up-to-date config.xml
      var date:Date = new Date();
      
      var _urlLoader:URLLoader = new URLLoader();     
      _urlLoader.addEventListener(Event.COMPLETE, handleComplete);
      
      var localeReqURL:String = buildRequestURL() + LOCALES_FILE + "?a=" + date.time;
      _urlLoader.load(new URLRequest(localeReqURL));
    }
    
    private function buildRequestURL():String {
      var swfURL:String = FlexGlobals.topLevelApplication.url;
      var protocol:String = URLUtil.getProtocol(swfURL);
      var serverName:String = URLUtil.getServerNameWithPort(swfURL);
      return protocol + "://" + serverName + "/";
    }
    
    private function handleComplete(e:Event):void{
      parse(new XML(e.target.data));
    }
    
    private function parse(xml:XML):void{
      var list:XMLList = xml.locale;
      var locale:XML;
      var localeCodes:Array = new Array();
      var localeNames:Array = new Array();
      
      for each(locale in list){
        localeCodes.push(locale.@code);
        localeNames.push(locale.@name);
      }
      localeModel.setLocales(localeCodes, localeNames);
      
      localeXmlLoadedSignal.dispatch();
    }
  }
}