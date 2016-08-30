package org.bigbluebutton.common.receivers
{
  
  import robotlegs.bender.framework.api.IConfig;
  import robotlegs.bender.framework.api.IInjector;
  
  public class CommonReceiversConfig implements IConfig
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
      injector.map(UsersMessageHandler).asSingleton();
      
    }
  }
}