package org.bigbluebutton.core.domain

trait Role2x
case object ModeratorRole extends Role2x
case object ViewerRole extends Role2x
case object PresenterRole extends Role2x

sealed trait Presence
case class FlashBrowserPresence(id: IntUserId, extId: ExtUserId, name: Name, sessionId: String) extends Presence
case class FlashSipPresence(id: IntUserId, extId: ExtUserId, name: Name, sessionId: String) extends Presence

case class RegisteredUser2x(id: IntUserId, extId: ExtUserId, name: Name, roles: Set[Role2x], authToken: AuthToken)

case class UserPermissions(permissions: Set[Permission2x], pinned: Boolean)

case class User2x(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId,
  roles: Set[Role2x],
  presence: Set[Presence],
  permissions: UserPermissions)

case class Voice2x(
  id: String,
  webId: String,
  callId: CallerId,
  phoningIn: Boolean,
  joined: Boolean,
  locked: Boolean,
  muted: Boolean,
  talking: Boolean)
