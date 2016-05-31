package org.bigbluebutton.common.domain
{

  public class User
  {
    private var _id:String;
    private var _externalId:String;
    private var _name:String;
    private var _emojiStatus:String;
    private var _roles:RoleCollection = new RoleCollection();
    private var _presence:PresenceCollection = new PresenceCollection();
    private var _abilities: UserAbilities = new UserAbilities();
    private var _roleData: RoleDataCollection = new RoleDataCollection();
    private var _externalData:ExternalDataCollection = new ExternalDataCollection();
    
    public function User()
    {
    }
  }
}