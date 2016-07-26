package org.bigbluebutton.core2x.domain

case class RegisteredUser2x(id: IntUserId,
  extId: ExtUserId,
  name: Name,
  roles: Set[Role2x],
  authToken: SessionToken,
  avatar: Avatar,
  logoutUrl: LogoutUrl,
  welcome: Welcome,
  dialNumbers: Set[DialNumber],
  pinNumber: PinNumber,
  config: String,
  extData: String)

