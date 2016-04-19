package org.bigbluebutton.core.domain

case class RegisteredUser2x(id: IntUserId, extId: ExtUserId, name: Name, roles: Set[String], authToken: AuthToken)

case class User2x(
  id: IntUserId,
  extId: ExtUserId,
  name: Name,
  sessionId: SessionId,
  roles: Set[String],
  presence: Set[Presence],
  permissions: Set[Permission2x])

case class Voice2x(
  id: String,
  webId: String,
  callId: CallerId,
  phoningIn: Boolean,
  joined: Boolean,
  locked: Boolean,
  muted: Boolean,
  talking: Boolean)
