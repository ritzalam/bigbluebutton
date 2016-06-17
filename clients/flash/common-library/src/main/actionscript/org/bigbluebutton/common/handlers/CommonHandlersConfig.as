package org.bigbluebutton.common.handlers
{
  import robotlegs.bender.framework.api.IConfig;
  import robotlegs.bender.framework.api.IInjector;
  
  public class CommonHandlersConfig implements IConfig
  {
    [Inject]
    public var injector:IInjector;
    
    public function configure():void
    {
      dependencies();
    }
    
    /**
     * Specifies all the dependencies for the feature
     * that will be injected onto objects used by the
     * application.
     */
    private function dependencies():void {
      injector.map(ValidateAuthTokenReplyHandler).asSingleton();
      injector.map(ValidateAuthTokenTimedOutHandler).asSingleton();
    }
  }
}