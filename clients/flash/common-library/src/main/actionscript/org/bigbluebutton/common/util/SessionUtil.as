package org.bigbluebutton.common.util
{
  import mx.core.FlexGlobals;
  
  import org.bigbluebutton.lib.common.utils.QueryStringParameters;

  public class SessionUtil implements ISessionUtil
  {
    
    public function getTopLevelUrl():String {
      var pageHost:String = FlexGlobals.topLevelApplication.url.split("/")[0];
      var pageURL:String = FlexGlobals.topLevelApplication.url.split("/")[2];
      
     return pageHost + "//" + pageURL;
    }
    
    public function getSessionToken():String {
      var p:QueryStringParameters = new QueryStringParameters();
      p.collectParameters();
      return p.getParameter("sessionToken");
    }
  }
}