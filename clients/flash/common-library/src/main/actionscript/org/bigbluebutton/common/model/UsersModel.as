package org.bigbluebutton.common.model
{
  import mx.collections.ArrayCollection;
  
  import org.osflash.signals.ISignal;
  import org.osflash.signals.Signal;

  public class UsersModel implements IUsersModel
  {
    private var _users:ArrayCollection = new ArrayCollection();
    
    /**
     * Dispatched when all participants are added
     */
    private var _allUsersAddedSignal:Signal = new Signal();
    
    public function get allUsersAddedSignal():ISignal {
      return _allUsersAddedSignal;
    }
    
    /**
     * Dispatched when a participant is added
     */
    private var _userAddedSignal:Signal = new Signal();
    
    public function get userAddedSignal():ISignal {
      return _userAddedSignal;
    }
    
    /**
     * Dispatched when a participant is removed
     */
    private var _userRemovedSignal:Signal = new Signal();
    
    public function get userRemovedSignal():ISignal {
      return _userRemovedSignal;
    }
    
    /**
     * Dispatched when a users' property have been changed
     */
    private var _userChangeSignal:Signal = new Signal();
    
    public function get userChangeSignal():ISignal {
      return _userChangeSignal;
    }
    
    public function UsersModel()
    {
    }
  }
}