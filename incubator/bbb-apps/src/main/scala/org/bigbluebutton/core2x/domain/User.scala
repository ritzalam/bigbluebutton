package org.bigbluebutton.core2x.domain

import com.softwaremill.quicklens._
import org.bigbluebutton.core2x.api.SessionId

object User {
  def create(id: IntUserId, externalId: ExtUserId, name: Name, roles: Set[Role2x]): User = {

    new User(id, externalId, name, EmojiStatus("none"), roles,
      Set.empty, new UserAbilities(Set.empty, Set.empty, false),
      Set.empty, Set.empty, Set.empty)
  }

  def update(old: Client2x, user: User, updated: Client2x): User = {
    modify(user)(_.client).setTo((user.client - old) + updated)
  }

  def findWithClientId(clients: Set[Client2x], clientId: ClientId): Option[Client2x] = {
    clients.find(p => p.id == clientId)
  }

  def add(user: User, client: Client2x): User = {
    modify(user)(_.client).setTo(user.client + client)
  }

  def add(user: User, role: Role2x): User = {
    modify(user)(_.roles).setTo(user.roles + role)
  }

  def remove(user: User, role: Role2x): User = {
    modify(user)(_.roles).setTo(user.roles - role)
  }

  def update(user: User, emoji: EmojiStatus): User = {
    modify(user)(_.emojiStatus).setTo(emoji)
  }

  def create(id: ClientId, userAgent: ClientUserAgent): Client2x = {
    userAgent match {
      case FlashWebUserAgent => Client2x(
        id, UserAgent("Flash"), Set.empty, DataApp2x(SessionId("none")), Voice4x(VoiceUserId("foo")),
        WebCamStreams(Set.empty), ScreenShareStreams(Set.empty))
      case Html5WebUserAgent => Client2x(id, UserAgent("Html5"), Set.empty, DataApp2x(SessionId("none")),
        Voice4x(VoiceUserId("foo")), WebCamStreams(Set.empty), ScreenShareStreams(Set.empty))
    }
  }
}

case class User(id: IntUserId, externalId: ExtUserId, name: Name, emojiStatus: EmojiStatus, roles: Set[Role2x],
    client: Set[Client2x], permissions: UserAbilities, roleData: Set[RoleData],
    config: Set[String], externalData: Set[String]) {

  def isModerator: Boolean = roles.contains(ModeratorRole)
}

case class UserAbilities(removed: Set[Abilities2x], added: Set[Abilities2x], applyMeetingAbilities: Boolean)