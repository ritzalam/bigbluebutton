package org.bigbluebutton.common.service
{
  public class JsonMessage
  {
    private var header:Object;
    private var body:Object;
    
    public function JsonMessage(name: String, body: Object)
    {
      header = new Object;
      header.name = name;
      this.body = body;
    }
    
    public function toJson():String {
      var json:Object = new Object();
      json.header = header;
      json.body = body;
      
      return JSON.stringify(json);
    }
  }
}