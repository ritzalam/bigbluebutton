package org.bigbluebutton.common.model
{
  import flash.events.IEventDispatcher;
  import flash.external.ExternalInterface; 
  import mx.core.FlexGlobals;
  import mx.events.ResourceEvent;
  import mx.resources.IResourceManager;
  import mx.resources.ResourceManager;
  import mx.utils.URLUtil;
  import org.bigbluebutton.common.signal.LoadedAppAndLocaleVersionsSignal;
  import org.bigbluebutton.common.signal.LocaleChangedSignal;

  public class LocaleModel implements ILocaleModel
  {
    private var MASTER_LOCALE:String = "en_US";
    private var resourceManager:IResourceManager;
    
    private var BBB_RESOURCE_BUNDLE:String = 'bbbResources';
    private var localeCodes:Array = new Array();
    private var localeNames:Array = new Array();
    private var localeIndex:Number;
    private var eventDispatcher:IEventDispatcher;
    private var preferredLocale:String
    
    [Inject]
    public var localeChangedSignal:LocaleChangedSignal;
    
    [Inject]
    public var loadedAppAndLocaleVersionsSignal:LoadedAppAndLocaleVersionsSignal;
    
    public function LocaleModel() {
      resourceManager = ResourceManager.getInstance();
    }
    
    public function setLocales(localeCodes:Array, localNames:Array):void {
      this.localeCodes = localeCodes;
      this.localeNames = localeNames;
      
      preferredLocale = getDefaultLocale();
      if (preferredLocale != MASTER_LOCALE) {
        loadMasterLocale(MASTER_LOCALE);
      }
      setPreferredLocale(preferredLocale);
    }
    
    private function getDefaultLocale():String {
      return ExternalInterface.call("getLanguage");
    }
    
    public function setPreferredLocale(locale:String):void {
      if (isPreferredLocaleAvailable(locale)) {
        preferredLocale = locale;
      }else{
        preferredLocale = MASTER_LOCALE;
      }
      localeIndex = getIndexForLocale(preferredLocale);
      changeLocale(preferredLocale);
    }
    
    private function isPreferredLocaleAvailable(prefLocale:String):Boolean {
      for (var i:Number = 0; i < localeCodes.length; i++){
        if (prefLocale == localeCodes[i]) 
          return true;
      }
      return false;
    }
    
    public function getString(resourceName:String, parameters:Array = null, locale:String = null):String {
      /**
       * Get the translated string from the current locale. If empty, get the string from the master
       * locale. Locale chaining isn't working because mygengo actually puts the key and empty value
       * for untranslated strings into the locale file. So, when Flash does a lookup, it will see that
       * the key is available in the locale and thus not bother falling back to the master locale.
       *    (ralam dec 15, 2011).
       */
      var localeTxt:String = resourceManager.getString(BBB_RESOURCE_BUNDLE, resourceName, parameters, null);
      if ((localeTxt == "") || (localeTxt == null)) {
        localeTxt = resourceManager.getString(BBB_RESOURCE_BUNDLE, resourceName, parameters, MASTER_LOCALE);
      }
      return localeTxt;
    }
    
    public function getCurrentLanguageCode():String {
      return preferredLocale;
    }
    
    public function getLocaleCodeForIndex(index:int):String {
      return localeCodes[index];
    }
    
    private function getIndexForLocale(prefLocale:String):int {
      for (var i:Number = 0; i < localeCodes.length; i++){
        if (prefLocale == localeCodes[i]) 
          return i;
      }
      return -1;
    }
    
    private function buildRequestURL():String {
      var swfURL:String = FlexGlobals.topLevelApplication.url;
      var protocol:String = URLUtil.getProtocol(swfURL);
      var serverName:String = URLUtil.getServerNameWithPort(swfURL);        
      return protocol + "://" + serverName + "/";
    }
    
    private function loadMasterLocale(locale:String):void {					
      /**
       *  http://help.adobe.com/en_US/FlashPlatform/reference/actionscript/3/mx/resources/IResourceManager.html#localeChain
       *  Always load the default language, so if the chosen language 
       *  doesn't provide a resource, the default language resource is used
       */
      loadResource(locale);
    }
    
    private function loadResource(language:String):IEventDispatcher {
      // Add a random string on the query so that we don't get a cached version.
      
      var date:Date = new Date();
      var localeURI:String = buildRequestURL() + 'client/locale/' + language + '_resources.swf?a=' + date.time;
      return resourceManager.loadResourceModule(localeURI, false);
    }		
    
    public function changeLocale(locale:String):void{        	
      eventDispatcher = loadResource(locale);
      eventDispatcher.addEventListener(ResourceEvent.COMPLETE, localeChangeComplete);
      eventDispatcher.addEventListener(ResourceEvent.ERROR, handleResourceNotLoaded);
    }
    
    private function localeChangeComplete(event:ResourceEvent):void {
      // Set the preferred locale and master as backup.
      if (preferredLocale != MASTER_LOCALE) {
        resourceManager.localeChain = [preferredLocale, MASTER_LOCALE];
        localeIndex = getIndexForLocale(preferredLocale);
      } else {
        if (preferredLocale != MASTER_LOCALE) {
          trace("Failed to load locale [{0}].", [preferredLocale]);
        }
        
        resourceManager.localeChain = [MASTER_LOCALE];
        preferredLocale = MASTER_LOCALE;
        localeIndex = getIndexForLocale(preferredLocale);
      }
      sendAppAndLocaleVersions();
      update();
    }
    
    private function sendAppAndLocaleVersions():void {
      loadedAppAndLocaleVersionsSignal.dispatch();
    }
    
    /**
     * Defaults to DEFAULT_LANGUAGE when an error is thrown by the ResourceManager 
     * @param event
     */        
    private function handleResourceNotLoaded(event:ResourceEvent):void{
      resourceManager.localeChain = [MASTER_LOCALE];
      preferredLocale = MASTER_LOCALE;
      localeIndex = getIndexForLocale(preferredLocale);
      update();
    }
    
    public function update():void{
      localeChangedSignal.dispatch();
    }
  }
}