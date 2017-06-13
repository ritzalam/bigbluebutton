package org.bigbluebutton.core.model.users
{
  import mx.collections.ArrayCollection;
  
  import org.bigbluebutton.core.model.Me;
  import org.bigbluebutton.core.vo.UserVO;
  import org.bigbluebutton.core.vo.VoiceUserVO;

  public class Users
  {
   
    private var _users:ArrayCollection = new ArrayCollection();
           
    private function add(user: User2x):void {
      _users.addItem(user);
    }
    
    private function remove(intId: String):User2x {
      var index:int = getIndex(intId);
      if (index >= 0) {
        return _users.removeItemAt(index) as User2x;
      }
      
      return null;
    }
    
    private function getUserAndIndex(intId: String):Object {
      var user:User2x;
      for (var i:int = 0; i < _users.length; i++) {
        user = _users.getItemAt(i) as User2x;
        
        if (user.id == intId) {
          return {index:i, user:user};;
        }
      }
      
      return null;      
    }
    
    private function getUser(intId:String):User2x {
      var user:User2x;
      
      for (var i:int = 0; i < _users.length; i++) {
        user = _users.getItemAt(i) as User2x;
        
        if (user.id == intId) {
          return user;
        }
      }				
      
      return null;
    }
    
    private function getIndex(intId: String):int {
      var user:User2x;
      for (var i:int = 0; i < _users.length; i++) {
        user = _users.getItemAt(i) as User2x;
        
        if (user.id == intId) {
          return i;
        }
      }
      
      return -1;
    }
    
    
    public function userJoined(vu: User):void {
      add(vu);
    }

    public function userLeft(intId: String):User2x {
      var user: User2x = remove(intId);
      if (user != null) {
        return user;
      }    
      
      return null;
    }
  
  }
}

