package org.bigbluebutton.common.command
{
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class DoNothingCommand extends Command
  {
    public function DoNothingCommand()
    {
      // Does nothing. Used to map signals to instantiate and inject into
      // mediators.
      super();
    }
    
    override public function execute():void {
      // Does nothing. Used to map signals to instantiate and inject into
      // mediators.      
    }
  }
}