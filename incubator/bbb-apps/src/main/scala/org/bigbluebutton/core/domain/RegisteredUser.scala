package org.bigbluebutton.core.domain

case class RegisteredUser(id: IntUserId,
  extId: ExtUserId,
  name: Name,
  roles: Set[Role],
  authToken: SessionToken,
  avatar: Avatar,
  logoutUrl: LogoutUrl,
  welcome: Welcome,
  dialNumbers: Set[DialNumber],
  pinNumber: PinNumber,
  config: String,
  extData: String)

