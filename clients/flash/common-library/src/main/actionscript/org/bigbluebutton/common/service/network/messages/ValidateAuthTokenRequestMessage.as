package org.bigbluebutton.common.service.network.messages
{
  public class ValidateAuthTokenRequestMessage implements IJsonMessage
  {
    public const NAME:String = "ValidateAuthTokenRequestMessage";
    
    private var header:Object;
    private var body:Object;
    
    public function ValidateAuthTokenRequestMessage(authToken: String)
    {
      header = new Object;
      header.name = NAME;
      
      var message:Object = new Object();
      message["authToken"] = authToken;
      
      this.body = message;
    }
    
    public function toJson():String {
      var json:Object = new Object();
      json.header = header;
      json.body = body;
      
      return JSON.stringify(json);
    }
  }
}