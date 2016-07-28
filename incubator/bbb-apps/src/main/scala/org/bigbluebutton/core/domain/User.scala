package org.bigbluebutton.core.domain

import com.softwaremill.quicklens._

object User {
  def create(id: IntUserId, externalId: ExtUserId, name: Name, roles: Set[Role]): User = {

    new User(id, externalId, name, EmojiStatus("none"), roles,
      Set.empty, new UserAbilities(Set.empty, Set.empty, false),
      Set.empty, Set.empty, Set.empty)
  }

  def update(old: Client, user: User, updated: Client): User = {
    modify(user)(_.client).setTo((user.client - old) + updated)
  }

  def findClientWithSessionToken(clients: Set[Client], sessionToken: SessionToken): Option[Client] = {
    clients.find(c => c.sessionToken == sessionToken)
  }

  def findWithClientId(clients: Set[Client], clientId: ClientId): Option[Client] = {
    clients.find(p => p.id == clientId)
  }

  def add(user: User, client: Client): User = {
    modify(user)(_.client).setTo(user.client + client)
  }

  def add(user: User, role: Role): User = {
    modify(user)(_.roles).setTo(user.roles + role)
  }

  def remove(user: User, role: Role): User = {
    modify(user)(_.roles).setTo(user.roles - role)
  }

  def update(user: User, emoji: EmojiStatus): User = {
    modify(user)(_.emojiStatus).setTo(emoji)
  }

  def create(id: ClientId, userId: IntUserId, sessionToken: SessionToken, userAgent: ClientUserAgent): Client = {
    new Client(id, userId, sessionToken, UserAgent("Flash"), new AppsComponent(Set.empty), new VoiceComponent(Set.empty),
      new WebCamComponent(Set.empty), new ScreenShareComponent(Set.empty))
  }

}

case class User(id: IntUserId, externalId: ExtUserId, name: Name, emojiStatus: EmojiStatus, roles: Set[Role],
    client: Set[Client], permissions: UserAbilities, roleData: Set[RoleData],
    config: Set[String], externalData: Set[String]) {

  def isModerator: Boolean = roles.contains(ModeratorRole)
}

case class UserAbilities(removed: Set[Abilities], added: Set[Abilities], applyMeetingAbilities: Boolean)