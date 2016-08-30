package org.bigbluebutton.web.main.views {
  
  import flash.net.URLRequest;
  
  import mx.core.FlexGlobals;
  
  import org.bigbluebutton.common.signal.ConfigLoadedSignal;
  import org.bigbluebutton.common.signal.EnterApiCallSuccessSignal;
  import org.bigbluebutton.common.signal.LoadConfigSignal;
  import org.bigbluebutton.common.signal.LocaleChangedSignal;
  import org.bigbluebutton.common.signal.LocaleXmlLoadedSignal;
  import org.bigbluebutton.common.signal.VideoProfileLoadedSignal;
  import org.bigbluebutton.lib.main.commands.JoinMeetingSignal;
  import org.bigbluebutton.web.main.models.IUISession;
  
  import robotlegs.bender.bundles.mvcs.Mediator;
  import robotlegs.bender.framework.api.ILogger;
  
  public class LoadingScreenMediator extends Mediator {
    [Inject]
    public var logger:ILogger;
    
    [Inject]
    public var view:LoadingScreen;
    
    [Inject]
    public var joinMeetingSignal:JoinMeetingSignal;
    
    [Inject]
    public var uiSession:IUISession;
    
    [Inject]
    public var loadConfigSignal:LoadConfigSignal;
    
    [Inject]
    public var configLoadedSignal:ConfigLoadedSignal;
    
    [Inject]
    public var videoProfileLoadedSignal:VideoProfileLoadedSignal;
    
    [Inject]
    public var localeXmlLoadedSignal:LocaleXmlLoadedSignal;
    
    [Inject]
    public var enterApiCallSuccessSignal:EnterApiCallSuccessSignal;
    
    [Inject]
    public var localeChangedSignal:LocaleChangedSignal;
    
    override public function initialize():void {
      localeChangedSignal.add(onLocaleChanged);
      configLoadedSignal.add(onConfigLoadedSignal);
      videoProfileLoadedSignal.add(onVideoProfileLoadedSignal);
      localeXmlLoadedSignal.add(onLocaleXmlLoadedSignal);
      enterApiCallSuccessSignal.add(onEnterApiCallSuccessSignal);
      
      uiSession.loadingChangeSignal.add(onLoadingChange);
      onLoadingChange(uiSession.loading, uiSession.loadingMessage);
      var pageHost:String = FlexGlobals.topLevelApplication.url.split("/")[0];
      var pageURL:String = FlexGlobals.topLevelApplication.url.split("/")[2];
      
      var joinHost: String = pageHost + "//" + pageURL;
      
      // Kick-off bootstrap sequence.
      loadConfigSignal.dispatch();  
      
    }
    
    private function onLocaleChanged():void {
      logger.debug("onLocaleChanged");
      view.stateLabel.text = "onLocaleChanged";
    }
    
    private function onConfigLoadedSignal():void {
      logger.debug("onConfigLoadedSignal");
      view.stateLabel.text = "onConfigLoadedSignal";
    }
    
    private function onVideoProfileLoadedSignal():void {
      logger.debug("onVideoProfileLoadedSignal");
      view.stateLabel.text = "onVideoProfileLoadedSignal";
    }
    private function onLocaleXmlLoadedSignal():void {
      logger.debug("onLocaleXmlLoadedSignal");
      view.stateLabel.text = "onLocaleXmlLoadedSignal";   
    }
    private function onEnterApiCallSuccessSignal():void {
      logger.debug("onEnterApiCallSuccessSignal");
      view.stateLabel.text = "onEnterApiCallSuccessSignal";
    }
    
    private function onLoadingChange(loading:Boolean, message:String):void {
      view.stateLabel.text = message;
      view.visible = loading;
    }
    
    private function joinSuccess(urlRequest:URLRequest, responseURL:String):void {
      joinMeetingSignal.dispatch(responseURL);
    }
    
    private function joinFailure(reason:String):void {
      uiSession.setLoading(true, reason);
    }
    
    override public function destroy():void {
      super.destroy();
      uiSession.loadingChangeSignal.remove(onLoadingChange);
      localeChangedSignal.remove(onLocaleChanged);
      configLoadedSignal.remove(onConfigLoadedSignal);
      videoProfileLoadedSignal.remove(onVideoProfileLoadedSignal);
      localeXmlLoadedSignal.remove(onLocaleXmlLoadedSignal);
      enterApiCallSuccessSignal.remove(onEnterApiCallSuccessSignal);
      //view.dispose();
      view = null;
    }
  }
}
