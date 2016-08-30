package org.bigbluebutton.common.command
{
  import robotlegs.bender.bundles.mvcs.Command;
  
  public class LoadConfigCommand2 extends Command
  {
    public function LoadConfigCommand2()
    {
      super();
    }
    
    override public function execute():void {
      trace("************* !!!!!! LOAD CONFIG COMMAND 2");
    }
  }
}